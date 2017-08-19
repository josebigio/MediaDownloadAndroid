package com.josebigio.mediadownloader.views.adapters

import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.josebigio.mediadownloader.R
import com.josebigio.mediadownloader.models.ItemInfo
import com.josebigio.mediadownloader.models.comments.CommentsModel
import com.josebigio.mediadownloader.models.comments.ReplyComment
import com.josebigio.mediadownloader.models.comments.TopLevelComment
import kotlinx.android.synthetic.main.item_info_cell.view.*
import kotlinx.android.synthetic.main.top_comment_cell.view.*
import java.util.*
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.view.animation.AlphaAnimation






/**
 * Created by josebigio on 8/5/17.
 */

class CommentsAdapter(val delegate: Delegate) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var commentsModel: CommentsModel? = null
    var itemInfo: ItemInfo? = null

        private var lastPosition = -1


    companion object {
        private val VIDEO_INFO = 0
        private val TOP_COMMENT = 1
        private val REPLY_COMMENT = 2

        private val ANIMATION_DURATION = 1000L
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is VideoInfoViewHolder && itemInfo != null) {
            holder.bind(itemInfo!!)
        }
        else if(holder is TopCommentViewHolder) {
            holder.bind(commentsModel!!.comments[position-1] as TopLevelComment)
        }
        else if(holder is ReplyCommentViewHolder) {
            holder.bind(commentsModel!!.comments[position-1] as ReplyComment)
            //setAnimation(holder.itemView,position)
        }
    }

    override fun getItemCount(): Int {
        return (commentsModel?.comments?.size?: 0) + 1
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VIDEO_INFO ->
                    return VideoInfoViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.item_info_cell,parent,false))
            TOP_COMMENT ->
                return TopCommentViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.top_comment_cell,parent,false))
            else ->
                return ReplyCommentViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.top_comment_cell,parent,false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return VIDEO_INFO
        }
        val comment = commentsModel!!.comments[position-1]
        if(comment is TopLevelComment) {
            return TOP_COMMENT
        }
        return REPLY_COMMENT
    }

    private fun setScaleAnimation(view: View) {
        val anim = ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        anim.duration = ANIMATION_DURATION
        view.startAnimation(anim)
    }

    private fun setAnimation(view: View, pos: Int) {
        if(pos > lastPosition) {
            val anim = AlphaAnimation(0.0f, 1.0f)
            anim.duration = ANIMATION_DURATION
            view.startAnimation(anim)
            lastPosition = pos
        }
    }


    inner class TopCommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(comment: TopLevelComment) {
            itemView.tCommentAuthorTV.text = comment.author
            itemView.tCommentTextTV.text =  Html.fromHtml(comment.text)
            itemView.tCommentCellDraweeView.setImageURI(comment.authorThumbnail)
            itemView.setOnClickListener {
                delegate.onCommentSelected(comment.id)
            }
        }
    }

    inner class ReplyCommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(comment: ReplyComment) {
            itemView.tCommentAuthorTV.text = comment.author + "(reply)"
            itemView.tCommentTextTV.text =  Html.fromHtml(comment.text)
            itemView.tCommentCellDraweeView.setImageURI(comment.authorThumbnail)
            itemView.setOnClickListener {
                delegate.onCommentSelected(comment.parentId)
            }
        }
    }

    inner class VideoInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(itemInfo: ItemInfo) {
            itemView.itemInfoCellTitleTV.text = itemInfo.title
            itemView.itemInfoCellDescriptionTV.text = itemInfo.description
            itemView.itemInfoCellMDTV.text = "Length: ${itemInfo.duration}. Published on: ${itemInfo.publication}"
        }
    }


}

interface Delegate {
    fun onCommentSelected(id: UUID)
}

