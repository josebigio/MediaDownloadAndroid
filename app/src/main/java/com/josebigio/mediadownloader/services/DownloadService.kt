package com.josebigio.mediadownloader.services

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.os.Parcelable
import android.os.ResultReceiver
import android.util.Log
import timber.log.Timber
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL


/**
 * Created by josebigio on 7/24/17.
 */
class DownloadService : IntentService("DownloadService") {

    val AUDIO_FILE_EXTENSION = "mp3"
    val BASE_URL = "http://ec2-52-11-161-241.us-west-2.compute.amazonaws.com/audio?videoId="

    companion object {

        val UPDATE_PROGRESS = 8344
        val DOWNLOAD_STARTED = 1337
        val DOWNLOAD_DONE = 9000

        val VIDEO_ID_PARAM = "videoId"

        val UPDATE_PROGRESS_KEY = "progress"
        val DOWNLOAD_DONE_KEY = "filePath"
    }

    override fun onHandleIntent(intent: Intent?) {
        val videoId = intent!!.getStringExtra(VIDEO_ID_PARAM)
        Timber.d("Downloading videoId $videoId")
        val urlToDownload = BASE_URL + videoId
        val receiver = intent.getParcelableExtra<Parcelable>("receiver") as ResultReceiver
        try {
            val url = URL(urlToDownload)
            val connection = url.openConnection()
            connection.connect()
            // this will be useful so that you can show a typical 0-100% progress bar
            val fileLength = connection.getContentLength()

            // download the file
            val input = BufferedInputStream(connection.getInputStream())
            val outputStream = FileOutputStream(File(getExternalFilesDir(null), "$videoId.$AUDIO_FILE_EXTENSION"))
            val data = ByteArray(1024)
            var total: Long = 0
            var count: Int = input.read(data)
            receiver.send(DOWNLOAD_STARTED,null)
            while (count != -1) {
                total += count.toLong()
                // publishing the progress....
                val resultData = Bundle()
                val progress = ((total * 100.00) / fileLength).toInt()
                //Log.d(TAG,"download progress: $progress")
                resultData.putInt(UPDATE_PROGRESS_KEY, progress)
                receiver.send(UPDATE_PROGRESS, resultData)
                outputStream.write(data, 0, count)
                count = input.read(data)
            }

            outputStream.flush()
            outputStream.close()
            input.close()
            Timber.d("DOWNLOAD FOR $videoId DONE")
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val resultData = Bundle()
        resultData.putString(DOWNLOAD_DONE_KEY, getFilePath(videoId))
        receiver.send(DOWNLOAD_DONE, resultData)
    }

    private fun getFilePath(videoId:String): String {
        return "${getExternalFilesDir(null)}/$videoId.$AUDIO_FILE_EXTENSION"
    }
}