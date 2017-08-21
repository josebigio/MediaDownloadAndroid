package com.josebigio.mediadownloader.views.interfaces

import com.josebigio.mediadownloader.models.MediaFile

/**
 * Created by josebigio on 8/18/17.
 */
interface LibraryView {

    fun renderItems(files: List<MediaFile>)
    fun navigateToDetails(id:String)
}