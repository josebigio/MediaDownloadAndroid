package com.josebigio.mediadownloader.models.comments

import java.util.*

/**
 * Created by josebigio on 8/5/17.
 */
class CommentsModel(val comments: List<Comment>)

abstract class Comment(val id: UUID, val author: String?, val authorThumbnail: String?, val text: String?, val likeCount: Int?){
}

class TopLevelComment(id: UUID, author: String?, authorThumbnail: String?, text: String?
                      , likeCount: Int?
                      , val replies: List<Comment>
                      , var isExpanded: Boolean = false) :
                        Comment(id, author, authorThumbnail, text, likeCount)

class ReplyComment(id: UUID, author: String?, authorThumbnail: String?, text: String?
                      , likeCount: Int?, val parentId: UUID) :
        Comment(id, author, authorThumbnail, text, likeCount)