package com.example.mukhtar.hackernews.ui.comments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.mukhtar.hackernews.R
import com.example.mukhtar.hackernews.models.Comment
import com.example.mukhtar.hackernews.models.Item
import com.example.mukhtar.hackernews.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_comments.*
import javax.inject.Inject

class CommentsActivity : BaseActivity(), CommentsMvpView, CommentsListener {

    @Inject
    lateinit var mPresenter: CommentsPresenter<CommentsMvpView>

    private var mCommentsAdapter: CommentsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)
        activityComponent.inject(this)
        setUp()
        mPresenter.onAttach(this)
        val itemId = intent.getIntExtra(ARG_ITEM, 0)
        if(itemId != 0){
            mPresenter.onViewInitialized(itemId)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }

    override fun setUp() {
        mCommentsAdapter = CommentsAdapter(this)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            if(adapter == null){
                adapter = mCommentsAdapter
            }
        }

        swipeRefreshLayout.setOnRefreshListener {
            mPresenter.onRefresh()
        }
    }

    override fun onCommentUpdated(newComment: Comment, position: Int) {
        mCommentsAdapter?.changeCommentAtPosition(position, newComment)
    }

    override fun onItemUpdated(comments: List<Comment>) {
        mCommentsAdapter?.clearAndAddAll(comments)
    }

    override fun onCommentViewBound(item: Comment, position: Int) {
        mPresenter.onCommentBound(item, position)
    }

    override fun showNoNetworkMessage() {
        showMessage("You are offline")
    }

    override fun showLoading() {
        if(!swipeRefreshLayout.isRefreshing){
            swipeRefreshLayout.isRefreshing = true
        }
    }

    override fun hideLoading() {
        if(swipeRefreshLayout.isRefreshing){
            swipeRefreshLayout.isRefreshing = false
        }
    }

    companion object {
        fun open(context: Context, item: Item){
            val intent = Intent(context, CommentsActivity::class.java)
            intent.putExtra(ARG_ITEM, item.id)
            context.startActivity(intent)
        }
        const val ARG_ITEM = "item"
    }
}
