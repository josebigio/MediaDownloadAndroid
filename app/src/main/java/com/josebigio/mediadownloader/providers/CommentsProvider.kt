package com.josebigio.mediadownloader.providers

import com.josebigio.mediadownloader.api.ApiManager
import com.josebigio.mediadownloader.mappers.CommentMapper
import com.josebigio.mediadownloader.models.comments.Comment
import com.josebigio.mediadownloader.models.comments.CommentsModel
import com.josebigio.mediadownloader.models.comments.ReplyComment
import com.josebigio.mediadownloader.models.comments.TopLevelComment
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import java.util.*

/**
 * Created by josebigio on 8/17/17.
 */
class CommentsProvider(val api: ApiManager, val commentMapper: CommentMapper) {

    private var commentsBehaviourSubject = BehaviorSubject.create<CommentsModel>()


    fun getComments(videoId: String): Observable<CommentsModel> {
        commentsBehaviourSubject = BehaviorSubject.create<CommentsModel>()
        api.getComments(videoId)
                .subscribeOn(Schedulers.io())
                .subscribe {
                    response ->
                    commentsBehaviourSubject.onNext(commentMapper.transform(response))
                }

        return commentsBehaviourSubject
    }

    fun handleCommentAction(commentId: UUID) {
        val comment = commentsBehaviourSubject.value.comments.filter { comment -> comment.id == commentId }.first()
        if(comment is TopLevelComment) {
            if(comment.isExpanded) {
                collapseComment(commentId)
            }
            else {
                expandComment(commentId)
            }
        }
    }

    fun expandComment(commentId: UUID) {
        val currentModel = commentsBehaviourSubject.value
        val result = ArrayList<Comment>()
        for (comment in currentModel.comments) {
            result.add(comment)
            if(comment.id == commentId) {
                if(comment !is TopLevelComment) {
                    throw RuntimeException("Trying to expand a reply comment!!!!")
                }
                if(comment.isExpanded) {
                    throw RuntimeException("Trying to expand an expanded comment!!!!")
                }
                comment.isExpanded = true
                Timber.d("comment to expand found")
                result.addAll(comment.replies)
            }
        }
        commentsBehaviourSubject.onNext(CommentsModel(result))
    }

    fun collapseComment(commentId: UUID) {
        val currentModel = commentsBehaviourSubject.value
        val result = ArrayList<Comment>()
        for (comment in currentModel.comments) {
            if(comment is ReplyComment && comment.parentId!=commentId || comment is TopLevelComment) {
                result.add(comment)
            }
            if(comment.id == commentId) {
                if(comment !is TopLevelComment) {
                    throw RuntimeException("Trying to collapse a reply comment!!!!")
                }
                if(!comment.isExpanded) {
                    throw RuntimeException("Trying to collapse a collapsed comment!!!!")
                }
                comment.isExpanded = false
                Timber.d("comment to expand found")
            }

        }
        commentsBehaviourSubject.onNext(CommentsModel(result))
    }



}