package com.josebigio.mediadownloader.views.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import com.josebigio.mediadownloader.MediaApplication
import com.josebigio.mediadownloader.R
import com.josebigio.mediadownloader.di.components.ActivityComponent
import com.josebigio.mediadownloader.di.components.DaggerActivityComponent
import com.josebigio.mediadownloader.di.modules.NavigationModule
import com.josebigio.mediadownloader.presenters.SearchPresenter
import com.josebigio.mediadownloader.views.adapters.SearchAdapter
import com.josebigio.mediadownloader.views.adapters.SearchAdapterDelegate
import com.josebigio.mediadownloader.views.adapters.SearchItem
import com.josebigio.mediadownloader.views.interfaces.SearchView
import kotlinx.android.synthetic.main.search_view.*
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


class SearchActivity : BaseActivity(), SearchView, SearchAdapterDelegate {


    @Singleton
    @Inject
    lateinit var presenter: SearchPresenter

    lateinit var activityComponent: ActivityComponent

    val searchAdapter = SearchAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_view)
        val mediaApp = MediaApplication.mainComponent
        activityComponent = DaggerActivityComponent.builder()
                .mainComponent(mediaApp)
                .activityModule(getActivityModule())
                .build()
        activityComponent.inject(this)
        presenter.view = this
        initViews()
    }

    override fun onResume() {
        super.onResume()
        presenter.onViewActive()
    }


    override fun onPause() {
        super.onPause()
        presenter.onViewInactive()
    }


    override fun render(dataSet: ArrayList<SearchItem>) {
        searchAdapter.dataSet = dataSet
        searchAdapter.notifyDataSetChanged()
    }

    override fun showLoading(show: Boolean) {
    }

    override fun onItemClick(searchItem: SearchItem) {
        presenter.onSearchItemClicked(searchItem)
    }

    private fun initViews() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = searchAdapter
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                presenter.onSearch(editable.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }


//    private fun startDownload() {
//
//        if(TextUtils.isEmpty(editText.text)) {
//            return
//        }
//        dialog.isIndeterminate = false
//        dialog.max = 100
//        dialog.visibility = View.VISIBLE
//        val intent = Intent(this, DownloadService::class.java)
//        intent.putExtra("videoId", editText.text.toString())
//        intent.putExtra("receiver", DownloadReceiver(Handler()))
//        startService(intent)
//    }


//    private inner class DownloadReceiver(handler: Handler) : ResultReceiver(handler) {
//
//        override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
//            super.onReceiveResult(resultCode, resultData)
//            if (resultCode == DownloadService.UPDATE_PROGRESS) {
//                resultData?:return
//                val progress = resultData.getInt("progress")
//                dialog.progress = progress
//                if (progress == 100) {
//                    dialog.visibility = View.GONE
//                    Toast.makeText(this@SearchActivity, "DOWNLOAD DONE", Toast.LENGTH_LONG).show()
//                }
//            }
//            else if(resultCode == DownloadService.DOWNLOAD_STARTED) {
//                //dialog.isIndeterminate = false
//                dialog.progress = 0
//                Toast.makeText(this@SearchActivity, "DOWNLOAD STARTED", Toast.LENGTH_LONG).show()
//            }
//        }
//    }


}
