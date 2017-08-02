package com.josebigio.mediadownloader.views

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.josebigio.mediadownloader.MediaApplication
import com.josebigio.mediadownloader.R
import com.josebigio.mediadownloader.presenters.SearchPresenter
import com.josebigio.mediadownloader.views.adapters.SearchAdapter
import com.josebigio.mediadownloader.views.adapters.SearchItem
import com.josebigio.mediadownloader.views.interfaces.SearchView
import kotlinx.android.synthetic.main.search_view.*
import kotlinx.android.synthetic.main.search_view.view.*
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by josebigio on 7/30/17.
 */
class SearchFragment : Fragment(), SearchView {


    @Singleton
    @Inject
    lateinit var presenter: SearchPresenter

    val searchAdapter = SearchAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MediaApplication.mainComponent.inject(this)
        presenter.view = this
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.search_view, container, false)
        view.recyclerView.layoutManager = LinearLayoutManager(context)
        view.recyclerView.adapter = searchAdapter
        return view
    }


    override fun onResume() {
        super.onResume()
        presenter.onViewActive()
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                presenter.onSearch(editable.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

    }


    override fun onPause() {
        super.onPause()
        presenter.onViewInactive()
    }


    override fun render(dataSet: ArrayList<SearchItem>) {
        searchAdapter.dataSet = dataSet
        searchAdapter.notifyDataSetChanged()
    }


}