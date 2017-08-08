package com.josebigio.mediadownloader.views.adapters

import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.josebigio.mediadownloader.R
import com.josebigio.mediadownloader.models.comments.CommentsModel
import com.josebigio.mediadownloader.models.comments.TopLevelComment
import kotlinx.android.synthetic.main.search_cell.view.*
import kotlinx.android.synthetic.main.top_comment_cell.view.*
import timber.log.Timber


/**
 * Created by josebigio on 8/5/17.
 */

class CommentsAdapter : RecyclerView.Adapter<CommentsViewHolder>() {

    var commentsModel: CommentsModel? = null

    override fun onBindViewHolder(holder: CommentsViewHolder?, position: Int) {
        holder!!.bind((commentsModel!!.comments[position]), View.OnClickListener { })
    }

    override fun getItemCount(): Int {
        return commentsModel?.comments?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CommentsViewHolder {
        return CommentsViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.top_comment_cell,parent,false))
    }

}
class CommentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(comment: TopLevelComment, onClickListener: View.OnClickListener) {
        itemView.tCommentAuthorTV.text = comment.author
        itemView.tCommentTextTV.text =  Html.fromHtml(comment.text)
        itemView.tCommentCellDraweeView.setImageURI(comment.authorThumbnail)
    }
}
