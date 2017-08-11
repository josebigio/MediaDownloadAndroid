package com.josebigio.mediadownloader.managers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.text.method.NumberKeyListener
import android.widget.Toast
import com.josebigio.mediadownloader.services.DownloadService
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import timber.log.Timber


/**
 * Created by josebigio on 7/25/17.
 */
class DownloadManager (val context: Context) {

    var emitter: ObservableEmitter<Download>? = null


    fun startAudioDownload(videoID: String): Observable<Download> {
        Timber.d("startAudioDownload. Context: $context, videoId: $videoID")
        val intent = Intent(context, DownloadService::class.java)
        intent.putExtra(DownloadService.VIDEO_ID_PARAM, videoID)
        intent.putExtra("receiver", DownloadReceiver(Handler()))
        context.startService(intent)
        return Observable.create<Download> { emitter ->
            this.emitter = emitter
        }

    }


    private inner class DownloadReceiver(handler: Handler) : ResultReceiver(handler) {

        override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
            super.onReceiveResult(resultCode, resultData)
            if (resultCode == DownloadService.UPDATE_PROGRESS) {
                val progress = resultData?.getInt(DownloadService.UPDATE_PROGRESS_KEY)?:return
                //Timber.d("Progress: $progress")
                emitter?.onNext(Download(progress,null))
            }
            if (resultCode == DownloadService.DOWNLOAD_DONE) {
                val filePath = resultData?.getString(DownloadService.DOWNLOAD_DONE_KEY)
                Toast.makeText(context,"Download done!!",Toast.LENGTH_SHORT).show()
                emitter?.onNext(Download(100,filePath))
                emitter?.onComplete()
            }
        }
    }

}

data class Download(val progress:Int, val filePath:String?)