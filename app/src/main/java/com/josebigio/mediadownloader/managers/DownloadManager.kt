package com.josebigio.mediadownloader.managers

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import io.reactivex.Observable
import android.os.SystemClock
import android.util.Log
import com.josebigio.mediadownloader.MainActivity
import com.josebigio.mediadownloader.services.DownloadService


/**
 * Created by josebigio on 7/25/17.
 */
class DownloadManager {

    val TEST_URL = "http://traffic.libsyn.com/joeroganexp/p987.mp3"
    val TAG = DownloadService::class.java.canonicalName
    val downloadMap = HashMap<String,Int>()


//    fun startAudioDownload(videoID: String, activity: Activity) {
//        val url = ""
//        Observable.create<Boolean> { emitter ->
//            val intent = Intent(activity, DownloadService::class.java)
//            intent.putExtra("url", TEST_URL)
//            intent.putExtra("receiver", DownloadReceiver(Handler()))
//            activity.startService(intent)
//            emitter.onNext()
//        }
//    }
//
//
//    private inner class DownloadReceiver(handler: Handler) : ResultReceiver(handler) {
//
//        override fun onReceiveResult(resultCode: Int, resultData: Bundle) {
//            super.onReceiveResult(resultCode, resultData)
//            if (resultCode == DownloadService.UPDATE_PROGRESS) {
//                val progress = resultData.getInt("progress")
//                Log.d(TAG,"progress: $progress")
//                dialog.progress = progress
//                if (progress == 100) {
//                    dialog.dismiss()
//                }
//            }
//        }
//    }


}