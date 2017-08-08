package com.josebigio.mediadownloader.views.activities

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import com.josebigio.mediadownloader.MediaApplication
import com.josebigio.mediadownloader.R
import com.josebigio.mediadownloader.constants.DETAIL_VIEW_ID_KEY
import com.josebigio.mediadownloader.constants.SEARCH_VIEW_QUERY_KEY
import com.josebigio.mediadownloader.di.components.ActivityComponent
import com.josebigio.mediadownloader.di.components.DaggerActivityComponent
import com.josebigio.mediadownloader.di.modules.ActivityModule
import com.josebigio.mediadownloader.di.modules.PresenterModule
import com.josebigio.mediadownloader.navigation.Navigator
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
    @Singleton
    @Inject
    lateinit var navigator: Navigator


    val searchAdapter = SearchAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_view)
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

    override fun navigateToDetails(id:String) {
        navigator.navigateToDetails(id,this)
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





}
