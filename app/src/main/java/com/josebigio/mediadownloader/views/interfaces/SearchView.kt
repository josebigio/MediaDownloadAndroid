package com.josebigio.mediadownloader.views.interfaces

import com.josebigio.mediadownloader.views.adapters.SearchItem
import java.util.*

/**
 * Created by josebigio on 8/1/17.
 */
interface SearchView {

    fun render(dataSet: ArrayList<SearchItem>)
    fun showLoading(show:Boolean)

}