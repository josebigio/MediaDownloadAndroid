package com.josebigio.mediadownloader.views.interfaces

import com.josebigio.mediadownloader.models.ItemInfo

/**
 * Created by josebigio on 8/3/17.
 */
interface DetailsView {

    fun render(itemInfo: ItemInfo)
    fun showLoading(show:Boolean)

}