package com.josebigio.mediadownloader.models.comments

/**
 * Created by josebigio on 8/5/17.
 */
data class CommentsModel(val comments: List<TopLevelComment>)
data class TopLevelComment(val author: String?, val authorThumbnail: String?, val text:String?, val likeCount: Int?, val replies: List<Comment>)
data class Comment(val author: String?, val authorThumbnail: String?, val text:String?, val likeCount: Int?)
