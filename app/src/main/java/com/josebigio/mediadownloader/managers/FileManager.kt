package com.josebigio.mediadownloader.managers

import android.content.Context
import com.josebigio.mediadownloader.models.MediaFile
import io.realm.Realm
import timber.log.Timber

/**
 * Created by josebigio on 8/9/17.
 */
class FileManager(val context: Context) {

    fun saveFile(fileName: String, fileId: String, filePath: String) {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val file = realm.createObject(MediaFile::class.java)
        file.fileId = fileId
        file.fileName = fileName
        file.filePath = filePath
        realm.commitTransaction()
        Timber.d("$file saved")

    }

    fun getFileByName(fileName: String): MediaFile {
        val realm = Realm.getDefaultInstance()
        val query = realm.where(MediaFile::class.java)
        val file = query.equalTo("fileId",fileName).findFirst()
        return file
    }

    fun getFileById(fileName:String): MediaFile? {
        return null
    }


}