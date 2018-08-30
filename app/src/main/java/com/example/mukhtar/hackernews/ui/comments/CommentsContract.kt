package com.example.mukhtar.hackernews.ui.comments

import com.example.mukhtar.hackernews.models.Comment
import com.example.mukhtar.hackernews.models.Item
import com.example.mukhtar.hackernews.ui.base.MvpPresenter
import com.example.mukhtar.hackernews.ui.base.MvpView

interface CommentsMvpView: MvpView {
    fun onCommentUpdated(newComment: Comment, position: Int)
    fun onItemUpdated(comments: List<Comment>)
    fun showNoNetworkMessage()
}

interface CommentsListener{
    fun onCommentViewBound(item: Comment, position: Int)
}
interface CommentsMvpPresenter<V : CommentsMvpView> : MvpPresenter<V> {
    fun onViewInitialized(itemId: Int)
    fun onRefresh()
    fun onCommentBound(comment: Comment, position: Int)
}