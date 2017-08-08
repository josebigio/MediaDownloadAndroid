package com.josebigio.mediadownloader.mappers

import com.josebigio.mediadownloader.api.models.comments.CommentsItem
import com.josebigio.mediadownloader.api.models.comments.CommentsResponse
import com.josebigio.mediadownloader.api.models.comments.ResultsItem
import com.josebigio.mediadownloader.models.comments.Comment
import com.josebigio.mediadownloader.models.comments.CommentsModel
import com.josebigio.mediadownloader.models.comments.TopLevelComment
import javax.inject.Inject

/**
 * Created by josebigio on 8/5/17.
 */
class CommentMapper @Inject constructor(){

    fun transform(commentsResponse: CommentsResponse): CommentsModel? {
        if (commentsResponse.results == null) {
            return null
        }
        var topLevelComments = ArrayList<TopLevelComment>()
        for (resultItem: ResultsItem? in commentsResponse.results) {
            val topComment = resultItem?.snippet?.topLevelComment ?:break
            val snippet = topComment.snippet ?: break
            val comments = resultItem.replies?.comments ?: ArrayList()
            val repliesList = ArrayList<Comment>()
            for (reply: CommentsItem? in comments) {
                val replySnippet = reply?.snippet ?: break
                val replyComment = Comment(replySnippet.authorDisplayName, replySnippet.authorProfileImageUrl, replySnippet.textDisplay, replySnippet.likeCount)
                repliesList.add(replyComment)
            }
            val topLevelComment = TopLevelComment(snippet.authorDisplayName, snippet.authorProfileImageUrl, snippet.textDisplay, snippet.likeCount, repliesList)
            topLevelComments.add(topLevelComment)
        }
        return CommentsModel(topLevelComments)
    }
}