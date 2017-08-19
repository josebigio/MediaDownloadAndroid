package com.josebigio.mediadownloader.presenters

import com.josebigio.mediadownloader.api.ApiManager
import com.josebigio.mediadownloader.api.models.InfoResponse
import com.josebigio.mediadownloader.api.models.comments.CommentsResponse
import com.josebigio.mediadownloader.managers.FileManager
import com.josebigio.mediadownloader.mappers.CommentMapper
import com.josebigio.mediadownloader.mappers.ItemInfoMapper
import com.josebigio.mediadownloader.models.MediaFile
import com.josebigio.mediadownloader.models.comments.Comment
import com.josebigio.mediadownloader.models.comments.CommentsModel
import com.josebigio.mediadownloader.providers.CommentsProvider
import com.josebigio.mediadownloader.views.interfaces.DetailsView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*

/**
 * Created by josebigio on 8/3/17.
 */
class DetailsPresenter(val api: ApiManager, val itemInfoMapper: ItemInfoMapper,
                       val fileManager: FileManager, val commentsProvider: CommentsProvider) {

    var view: DetailsView? = null
    var id: String? = null
    val compositeDisposable = CompositeDisposable()
    var loadingSpinnerCount = 0

    fun onViewActive() {
        Timber.d("onViewActive")
    }

    fun onViewInactive() {
        Timber.d("onViewInactive")
        //compositeDisposable.dispose()
    }

    fun initialize(id: String, view: DetailsView) {
        this.id = id
        this.view = view
        view.enableDownload(true)
        loadData(id)
    }

    fun onDownloadClicked() {
        Timber.d("onDownloadClicked")
        val _id = id ?: return
        increaseSpinnerCount()
        fileManager.fetchAndSaveFile(_id)
    }

    fun onPlayClicked() {
        Timber.d("onPlayClicked")
        val _id = id ?: return
        fileManager.getOrCreateFile(_id).firstOrError().subscribe({
            mediaFile ->
            if (mediaFile.filePath != null) {
                view?.startPlayer(mediaFile.filePath!!)
            }
        })

    }

    fun onCommentSelected(id: UUID) {
        commentsProvider.handleCommentAction(id)
    }

    private fun loadData(id: String) {
        val commentsObserver = CommentObserver()
        val itemInfoObserver = ItemInfoObserver()
        val mediaFileObserver = MediaFileObserver()
        val fileSaveProgressObservable = FileSaveProgressObservable()
        compositeDisposable.addAll(commentsObserver, itemInfoObserver, mediaFileObserver)
        api.getInfo(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { increaseSpinnerCount() }
                .subscribe(itemInfoObserver)
        commentsProvider.getComments(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { increaseSpinnerCount() }
                .subscribe(commentsObserver)
        fileManager.getOrCreateFile(id).subscribe(mediaFileObserver)
        fileManager.getDownloadProgressForFile(id).subscribe(fileSaveProgressObservable)

    }

    private fun decreaseSpinnerCount() {
        loadingSpinnerCount = Math.max(0, loadingSpinnerCount - 1)
        view?.showLoading(loadingSpinnerCount > 0)
    }

    private fun increaseSpinnerCount() {
        loadingSpinnerCount++
        view?.showLoading(loadingSpinnerCount > 0)
    }


    //OBSERVERS
    private inner class CommentObserver : DisposableObserver<CommentsModel>() {

        override fun onError(onError: Throwable?) {
            Timber.e("ERROR GETTING COMMENTS $onError")
            view?.alert("ERROR GETTING COMMENTS $onError")
            decreaseSpinnerCount()
        }

        override fun onComplete() {}

        override fun onNext(commentsModel: CommentsModel) {
            Timber.d("comments model: $commentsModel")
            view?.renderComments(commentsModel)
            decreaseSpinnerCount()
        }

    }

    private inner class ItemInfoObserver : DisposableObserver<InfoResponse>() {

        override fun onError(onError: Throwable?) {
            Timber.e("ERROR GETTING iteminfo $onError")
            view?.alert("ERROR GETTING iteminfo $onError")
            decreaseSpinnerCount()
        }

        override fun onComplete() {}

        override fun onNext(infoResponse: InfoResponse) {
            view?.renderItemInfo(itemInfoMapper.transform(infoResponse))
            decreaseSpinnerCount()
        }

    }

    private inner class MediaFileObserver : DisposableObserver<MediaFile>() {

        override fun onError(onError: Throwable?) {
            Timber.e("ERROR GETTING mediaFile $onError")
            view?.alert("ERROR GETTING mediaFile $onError")
        }

        override fun onComplete() {}

        override fun onNext(mediaFile: MediaFile) {
            if (mediaFile.filePath == null) {
                view?.enableDownload(true)
                view?.enablePlay(false)
            } else {
                view?.enableDownload(false)
                view?.enablePlay(true)
            }
        }

    }

    private inner class FileSaveProgressObservable : DisposableObserver<FileManager.DownloadProgress>() {

        var recievedResponse = false

        override fun onError(onError: Throwable?) {
            Timber.e("ERROR GETTING mediaFile $onError")
            view?.hideProgress()
        }

        override fun onComplete() {}

        override fun onNext(downloadProgress: FileManager.DownloadProgress) {
            if (downloadProgress.downloadInProgress) {
                if (!downloadProgress.waitingForResponse && !recievedResponse) {
                    decreaseSpinnerCount()
                    recievedResponse = true
                } else {
                    view?.showProgress(downloadProgress.progress)
                }
            } else {
                view?.hideProgress()
            }
        }

    }

}