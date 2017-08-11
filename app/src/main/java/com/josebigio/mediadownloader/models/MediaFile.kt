package com.josebigio.mediadownloader.models

import io.realm.RealmObject

/**
 * Created by josebigio on 8/9/17.
 */
open class MediaFile : RealmObject() {

    var fileName: String? = null
    var filePath: String? = null
    var fileId: String? = null
    var lastPlayedMilli: Long = 0
}