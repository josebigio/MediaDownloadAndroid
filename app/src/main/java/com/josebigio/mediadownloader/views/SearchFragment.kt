package com.josebigio.mediadownloader.views

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.josebigio.mediadownloader.MediaApplication
import com.josebigio.mediadownloader.R
import com.josebigio.mediadownloader.api.ApiManager
import com.josebigio.mediadownloader.models.SearchResponse
import com.josebigio.mediadownloader.models.SearchResult
import com.josebigio.mediadownloader.views.adapters.SearchAdapter
import com.josebigio.mediadownloader.views.adapters.SearchItem
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.search_view.*
import kotlinx.android.synthetic.main.search_view.view.*
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by josebigio on 7/30/17.
 */
class SearchFragment: Fragment() {

    @Singleton
    @Inject
    lateinit var api: ApiManager

    val searchAdapter = SearchAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MediaApplication.networkComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater!!.inflate(R.layout.search_view,container,false)
        view.recyclerView.layoutManager = LinearLayoutManager(context)
        view.recyclerView.adapter = searchAdapter
        return view
    }



    override fun onResume() {
        super.onResume()
        searchEditText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                if(TextUtils.isEmpty(editable)) {
                    return
                }
                api.searchVideo(editable.toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(object : Observer<SearchResponse> {
//                    override fun onError(e: Throwable?) {
//
//                    }
//
//                    override fun onSubscribe(d: Disposable?) {
//                    }
//
//                    override fun onNext(value: SearchResponse?) {
//
//                    }
//
//                    override fun onComplete() {
//                    }
//
//                } )
//                        .subscribe{
//                            searchResponse ->
//                                val dataSet = ArrayList<SearchItem>()
//                                for (result: SearchResult in searchResponse.results) {
//                                    Timber.d("Search result: ${result.title}")
//                                    val searchItem = SearchItem(result.title, result.thumbnails.default.url)
//                                    dataSet.add(searchItem)
//                                }
//                                searchAdapter.dataSet = dataSet
//                                searchAdapter.notifyDataSetChanged()
//
//                        }

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

    }

    override fun onPause() {
        super.onPause()
    }

}