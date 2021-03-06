package com.josebigio.mediadownloader.views.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import com.josebigio.mediadownloader.R
import com.josebigio.mediadownloader.managers.FileManager
import com.josebigio.mediadownloader.navigation.Navigator
import com.josebigio.mediadownloader.presenters.SearchPresenter
import com.josebigio.mediadownloader.views.adapters.SearchAdapter
import com.josebigio.mediadownloader.views.adapters.SearchAdapterDelegate
import com.josebigio.mediadownloader.views.adapters.SearchItem
import com.josebigio.mediadownloader.views.interfaces.SearchView
import kotlinx.android.synthetic.main.drawer_container_view.*
import kotlinx.android.synthetic.main.search_view.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


class SearchActivity : BaseActivity(), SearchView, SearchAdapterDelegate {


    @Singleton
    @Inject
    lateinit var presenter: SearchPresenter
    @Singleton
    @Inject
    lateinit var fileManager: FileManager

    companion object {
        fun getCallingIntent(context: Context): Intent {
            val intent = Intent(context, SearchActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            return intent
        }
    }


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

    override fun selectDrawerPos() {
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
