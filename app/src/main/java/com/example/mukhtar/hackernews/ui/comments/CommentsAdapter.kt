package com.example.mukhtar.hackernews.ui.comments

import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mukhtar.hackernews.R
import com.example.mukhtar.hackernews.models.Comment
import kotlinx.android.synthetic.main.item_comment_loaded.view.*
import java.util.*

class CommentsAdapter(private val listener: CommentsListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val comments = ArrayList<Comment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == TYPE_LOADED){
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_comment_loaded, parent, false)
            return LoadedViewHolder(view)
        }else{
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_comment_not_loaded, parent, false)
            return NotLoadedViewHolder(view)
        }

    }

    override fun getItemCount(): Int = comments.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = comments[position]
        if(getItemViewType(position) == TYPE_LOADED){
            holder as LoadedViewHolder
            holder.bind(currentItem)
        }else{
            holder as NotLoadedViewHolder
            listener.onCommentViewBound(currentItem, position)
        }
    }

    override fun getItemViewType(position: Int): Int =
            if(comments.get(position).text == null) TYPE_NOT_LOADED
            else TYPE_LOADED

    public fun changeCommentAtPosition(position: Int, item: Comment){
        comments[position] = item
        notifyItemChanged(position)
    }

    public fun clearAndAddAll(item: List<Comment>){
        comments.clear()
        comments.addAll(item)
        notifyDataSetChanged()

    }

    inner class LoadedViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        fun bind(comment: Comment){
            itemView.authorTextView.text = comment.by
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                itemView.commentContentTextView.text = Html.fromHtml(comment.by, Html.FROM_HTML_MODE_LEGACY)
            } else {
                itemView.commentContentTextView.text = Html.fromHtml(comment.by)
            }
            itemView.dateOfCommentTextView.text = comment.getTimeInString()
            itemView.numberOfCommentTextView.text = String.format("%d comments", comment.kidsRealm.size)
        }
    }

    inner class NotLoadedViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

    }

    companion object {
        val TYPE_NOT_LOADED = 0
        val TYPE_LOADED = 1
    }
}