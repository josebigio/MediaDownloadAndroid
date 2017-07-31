package com.josebigio.mediadownloader.views

import android.app.Fragment
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.josebigio.mediadownloader.MediaApplication
import com.josebigio.mediadownloader.R
import com.josebigio.mediadownloader.api.ApiManager
import com.josebigio.mediadownloader.models.SearchResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.search_view.*
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MediaApplication.networkComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.search_view,container,false)
    }

    override fun onResume() {
        super.onResume()
        searchEditText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                api.searchVideo(editable.toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe{
                            searchResponse ->
                                for (result: SearchResult in searchResponse.results) {
                                    Timber.d("Search result: ${result.title}")
                                }
                        }
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