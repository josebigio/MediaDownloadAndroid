package com.josebigio.mediadownloader.presenters

import android.widget.Toast
import com.josebigio.mediadownloader.api.ApiManager
import com.josebigio.mediadownloader.managers.DownloadManager
import com.josebigio.mediadownloader.mappers.CommentMapper
import com.josebigio.mediadownloader.mappers.ItemInfoMapper
import com.josebigio.mediadownloader.views.interfaces.DetailsView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by josebigio on 8/3/17.
 */
class DetailsPresenter(val api: ApiManager, val commentMapper: CommentMapper, val itemInfoMapper: ItemInfoMapper, val downloadManager: DownloadManager) {

    var view: DetailsView? = null
    var id: String? = null

    fun onViewActive() {
        Timber.d("onViewActive")
    }

    fun onViewInactive() {
        Timber.d("onViewInactive")
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
        view?.showLoading(true)
        downloadManager.startAudioDownload(_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    (progress) ->
                    Timber.d("download progress: $progress")
                        view?.showProgress(progress)
                }, {
                    error ->
                        Timber.d("Error downloading: $error")
                        view?.showLoading(false)

                },{
                    view?.showLoading(false)
                })
        view?.enableDownload(false)
    }

    private fun loadData(id: String) {
        api.getInfo(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    infoResponse ->
                    view?.renderItemInfo(itemInfoMapper.transform(infoResponse))
                })
        view?.showLoading(true)
        api.getComments(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    commentsResponse ->
                    val commentsModel = commentMapper.transform(commentsResponse) ?: return@subscribe
                    Timber.d("comments model: $commentsModel")
                    view?.renderComments(commentsModel)
                    view?.showLoading(false)
                },
                        {
                            onError ->
                            Timber.e("ERROR GETTING COMMENTS $onError")
                            view?.showLoading(false)
                        })
    }


}