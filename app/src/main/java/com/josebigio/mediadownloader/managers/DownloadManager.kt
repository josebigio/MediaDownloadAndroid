package com.josebigio.mediadownloader.managers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.widget.Toast
import com.josebigio.mediadownloader.services.DownloadService
import timber.log.Timber


/**
 * Created by josebigio on 7/25/17.
 */
class DownloadManager (val context: Context) {

    fun startAudioDownload(videoID: String) {
//        Observable.create<Boolean> { emitter ->
//            val intent = Intent(context, DownloadService::class.java)
//            intent.putExtra("videoId", videoID)
//            intent.putExtra("receiver", DownloadReceiver(Handler()))
//            context.startService(intent)
//            emitter.onNext(true)
//        }
        Timber.d("startAudioDownload. Context: $context, videoId: $videoID")
        val intent = Intent(context, DownloadService::class.java)
        intent.putExtra("videoId", videoID)
        intent.putExtra("receiver", DownloadReceiver(Handler()))
        context.startService(intent)
    }


    private inner class DownloadReceiver(handler: Handler) : ResultReceiver(handler) {

        override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
            super.onReceiveResult(resultCode, resultData)
            if (resultCode == DownloadService.UPDATE_PROGRESS) {
                val progress = resultData?.getInt("progress")
                Timber.d("Progress: $progress")
            }
            if (resultCode == DownloadService.DOWNLOAD_DONE) {
                Toast.makeText(context,"Download done!!",Toast.LENGTH_SHORT).show()
            }
        }
    }


}