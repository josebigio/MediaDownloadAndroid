package com.josebigio.mediadownloader.views.adapters

import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.josebigio.mediadownloader.R
import com.josebigio.mediadownloader.models.ItemInfo
import com.josebigio.mediadownloader.models.comments.CommentsModel
import com.josebigio.mediadownloader.models.comments.TopLevelComment
import kotlinx.android.synthetic.main.item_info_cell.view.*
import kotlinx.android.synthetic.main.top_comment_cell.view.*


/**
 * Created by josebigio on 8/5/17.
 */

class CommentsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var commentsModel: CommentsModel? = null
    var itemInfo: ItemInfo? = null

    companion object {
        private val VIDEO_INFO = 0
        private val TOP_COMMENT = 1
        private val REPLY_COMMENT = 2
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is VideoInfoViewHolder) {
            holder.bind(itemInfo!!)
        }
        else if(holder is TopCommentViewHolder) {
            holder.bind(commentsModel!!.comments[position])
        }
    }

    override fun getItemCount(): Int {
        val headerCount = if ( itemInfo == null ) 0 else 1
        return (commentsModel?.comments?.size ?: 0) + headerCount
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VIDEO_INFO ->
                    return VideoInfoViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.item_info_cell,parent,false))
            TOP_COMMENT ->
                return TopCommentViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.top_comment_cell,parent,false))
            else ->
                return TopCommentViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.top_comment_cell,parent,false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return VIDEO_INFO
        }
        return TOP_COMMENT
    }
}

class TopCommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(comment: TopLevelComment) {
        itemView.tCommentAuthorTV.text = comment.author
        itemView.tCommentTextTV.text =  Html.fromHtml(comment.text)
        itemView.tCommentCellDraweeView.setImageURI(comment.authorThumbnail)
    }
}

class VideoInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(itemInfo: ItemInfo) {
        itemView.itemInfoCellTitleTV.text = itemInfo.title
        itemView.itemInfoCellDescriptionTV.text = itemInfo.description
        itemView.itemInfoCellMDTV.text = "Length: ${itemInfo.duration}. Published on: ${itemInfo.publication}"
    }
}
