package com.josebigio.mediadownloader.presenters

import com.josebigio.mediadownloader.managers.FileManager
import com.josebigio.mediadownloader.models.MediaFile
import com.josebigio.mediadownloader.views.interfaces.DetailsView
import com.josebigio.mediadownloader.views.interfaces.LibraryView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by josebigio on 8/18/17.
 */
class LibraryPresenter(val fileManager: FileManager) {

    var view: LibraryView? = null

    fun onViewActive() {
        Timber.d("onViewActive")
    }

    fun onViewInactive() {
        Timber.d("onViewInactive")
    }

    fun initialize(view: LibraryView) {
        this.view = view
        loadData()
    }

    fun loadData() {
        fileManager.getAllFiles()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    files->
                    view?.renderItems(files)
                })
    }

    fun onItemClicked(file: MediaFile) {
        view?.navigateToDetails(file.fileId!!)
    }

}