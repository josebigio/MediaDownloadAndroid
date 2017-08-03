package com.josebigio.mediadownloader.presenters

import android.text.TextUtils
import com.josebigio.mediadownloader.api.ApiManager
import com.josebigio.mediadownloader.models.SearchResult
import com.josebigio.mediadownloader.navigation.Navigator
import com.josebigio.mediadownloader.views.adapters.SearchItem
import com.josebigio.mediadownloader.views.interfaces.SearchView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.ArrayList
import javax.inject.Singleton

/**
 * Created by josebigio on 8/1/17.
 */
@Singleton
class SearchPresenter(val api: ApiManager, val navigator: Navigator) {

    var view:SearchView? = null

    fun onViewActive() {

    }

    fun onViewInactive() {

    }

    fun onSearchItemClicked(searchItem: SearchItem) {
        Timber.d("onSearchItemClicked: $searchItem")
    }

    fun onSearch(query:String) {
        if(TextUtils.isEmpty(query)) {
            return
        }
        view?.showLoading(true)
        api.searchVideo(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    searchResponse ->
                    val dataSet = ArrayList<SearchItem>()
                    for (result: SearchResult in searchResponse.results) {
                        Timber.d("Search result: ${result.title}")
                        val searchItem = SearchItem(result.title, result.thumbnails.default.url)
                        dataSet.add(searchItem)
                    }
                    view?.showLoading(true)
                    view?.render(dataSet)
                })

    }

}