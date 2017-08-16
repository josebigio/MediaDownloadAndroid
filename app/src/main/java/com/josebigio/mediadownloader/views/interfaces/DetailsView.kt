package com.josebigio.mediadownloader.views.interfaces

import com.josebigio.mediadownloader.models.ItemInfo
import com.josebigio.mediadownloader.models.comments.CommentsModel

/**
 * Created by josebigio on 8/3/17.
 */
interface DetailsView {

    fun renderItemInfo(itemInfo: ItemInfo)
    fun showLoading(show:Boolean)
    fun showProgress(progress:Int)
    fun hideProgress()
    fun enableDownload(enable:Boolean)
    fun enablePlay(enable:Boolean)
    fun renderComments(commentsModel: CommentsModel)
    fun startPlayer(filePath: String)
    fun alert(alert:String)
}