package com.josebigio.mediadownloader.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by josebigio on 8/9/17.
 */
open class MediaFile : RealmObject() {

    @PrimaryKey
    var fileId: String? = null
    var filePath: String? = null
    var lastPlayedMilli: Long = 0
}