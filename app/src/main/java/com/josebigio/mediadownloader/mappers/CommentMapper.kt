package com.josebigio.mediadownloader.mappers

import com.josebigio.mediadownloader.api.models.comments.CommentsItem
import com.josebigio.mediadownloader.api.models.comments.CommentsResponse
import com.josebigio.mediadownloader.api.models.comments.ResultsItem
import com.josebigio.mediadownloader.models.comments.CommentsModel
import com.josebigio.mediadownloader.models.comments.ReplyComment
import com.josebigio.mediadownloader.models.comments.TopLevelComment
import java.util.*
import javax.inject.Inject

/**
 * Created by josebigio on 8/5/17.
 */
class CommentMapper @Inject constructor(){

    fun transform(commentsResponse: CommentsResponse): CommentsModel? {
        if (commentsResponse.results == null) {
            return null
        }
        val topLevelComments = ArrayList<TopLevelComment>()
        for (resultItem: ResultsItem? in commentsResponse.results) {
            val topComment = resultItem?.snippet?.topLevelComment ?:break
            val snippet = topComment.snippet ?: break
            val comments = resultItem.replies?.comments ?: ArrayList()
            val repliesList = ArrayList<ReplyComment>()
            val parentId = UUID.randomUUID()!!
            for (reply: CommentsItem? in comments) {
                val replySnippet = reply?.snippet ?: break
                val replyComment = ReplyComment(UUID.randomUUID()!!, replySnippet.authorDisplayName, replySnippet.authorProfileImageUrl, replySnippet.textDisplay, replySnippet.likeCount, parentId)
                repliesList.add(replyComment)
            }
            val topLevelComment = TopLevelComment(parentId,snippet.authorDisplayName, snippet.authorProfileImageUrl, snippet.textDisplay, snippet.likeCount, repliesList,false)
            topLevelComments.add(topLevelComment)
        }
        return CommentsModel(topLevelComments)
    }
}