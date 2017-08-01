package com.josebigio.mediadownloader

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Toast
import com.josebigio.mediadownloader.services.DownloadService
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import android.view.View
import com.josebigio.mediadownloader.api.ApiManager
import com.josebigio.mediadownloader.views.SearchFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject lateinit var api: ApiManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MediaApplication.networkComponent.inject(this)


        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer,SearchFragment()).commit()
        downloadButton.setOnClickListener { startDownload() }
        api.searchVideo("Funny dogs")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    Timber.d(response.toString())
                }

    }

    private fun startDownload() {

        if(TextUtils.isEmpty(editText.text)) {
            return
        }
        dialog.isIndeterminate = false
        dialog.max = 100
        dialog.visibility = View.VISIBLE
        val intent = Intent(this, DownloadService::class.java)
        intent.putExtra("videoId", editText.text.toString())
        intent.putExtra("receiver", DownloadReceiver(Handler()))
        startService(intent)
    }


    private inner class DownloadReceiver(handler: Handler) : ResultReceiver(handler) {

        override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
            super.onReceiveResult(resultCode, resultData)
            if (resultCode == DownloadService.UPDATE_PROGRESS) {
                resultData?:return
                val progress = resultData.getInt("progress")
                dialog.progress = progress
                if (progress == 100) {
                    dialog.visibility = View.GONE
                    Toast.makeText(this@MainActivity, "DOWNLOAD DONE", Toast.LENGTH_LONG).show()
                }
            }
            else if(resultCode == DownloadService.DOWNLOAD_STARTED) {
                //dialog.isIndeterminate = false
                dialog.progress = 0
                Toast.makeText(this@MainActivity, "DOWNLOAD STARTED", Toast.LENGTH_LONG).show()
            }
        }
    }


}
