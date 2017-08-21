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
    var fileName: String? = null
    var thumnailUrl: String? = null

    fun copy(): MediaFile {
        val copy = MediaFile()
        copy.fileId = fileId
        copy.filePath = filePath
        copy.lastPlayedMilli = lastPlayedMilli
        copy.fileName = fileName
        copy.thumnailUrl = thumnailUrl
        return copy
    }

    override fun toString(): String {
        return "fileId: $fileId, filePath: $filePath, lastPlayedMilli: $lastPlayedMilli, fileName: $fileName"
    }
}