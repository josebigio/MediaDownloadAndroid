package com.josebigio.mediadownloader.managers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.widget.Toast
import com.josebigio.mediadownloader.services.DownloadService
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import timber.log.Timber


/**
 * Created by josebigio on 7/25/17.
 */
class DownloadManager (val context: Context) {


    fun startAudioDownload(videoID: String): Observable<Download> {
        return Observable.create<Download> {
            emitter ->
                Timber.d("startAudioDownload. Context: $context, videoId: $videoID")
                val intent = Intent(context, DownloadService::class.java)
                intent.putExtra(DownloadService.VIDEO_ID_PARAM, videoID)
                intent.putExtra("receiver", DownloadReceiver(Handler(context.mainLooper),emitter))
                context.startService(intent)
        }
    }




    private inner class DownloadReceiver(handler: Handler, val emitter: ObservableEmitter<Download>?) : ResultReceiver(handler) {

        override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
            super.onReceiveResult(resultCode, resultData)
            when(resultCode) {
                DownloadService.UPDATE_PROGRESS -> {
                    val progress = resultData?.getInt(DownloadService.UPDATE_PROGRESS_KEY)?:return
                    emitter?.onNext(Download(progress = progress, isWaitingResponse = false, downloadRequested = true))
                }
                DownloadService.DOWNLOAD_DONE -> {
                    val filePath = resultData?.getString(DownloadService.DOWNLOAD_DONE_KEY)
                    Toast.makeText(context,"Download done!!",Toast.LENGTH_SHORT).show()
                    emitter?.onNext(Download(progress = 100, isWaitingResponse = false, downloadRequested = true, isComplete = true, filePath = filePath))
                    emitter?.onComplete()
                }
                DownloadService.DOWNLOAD_ERROR -> {
                    emitter?.onError(RuntimeException("Error downloading video"))
                }
                DownloadService.DOWNLOAD_STARTED -> {
                    emitter?.onNext(Download(progress = 0, isWaitingResponse = false, downloadRequested = true))
                }
            }
        }
    }

}

data class Download(val progress:Int = 0, val filePath:String? = null, val isComplete: Boolean = false, val isWaitingResponse: Boolean = false, val downloadRequested: Boolean = true)