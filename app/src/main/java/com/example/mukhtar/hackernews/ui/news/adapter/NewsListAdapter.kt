package com.example.mukhtar.hackernews.ui.news.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.mukhtar.hackernews.R
import com.example.mukhtar.hackernews.models.Item
import com.example.mukhtar.hackernews.ui.news.NewsListListener
import kotlinx.android.synthetic.main.item_news_loaded.view.*
import java.util.*

class NewsListAdapter(private val listener: NewsListListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = ArrayList<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == TYPE_LOADED){
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_news_loaded, parent, false)
            return LoadedViewHolder(view)
        }else{
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_news_not_loaded, parent, false)
            return NotLoadedViewHolder(view)
        }

    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = items[position]
        if(getItemViewType(position) == TYPE_LOADED){
            holder as LoadedViewHolder
            holder.bind(currentItem)
        }else{
            holder as NotLoadedViewHolder
            listener.onItemViewBound(currentItem, position)
        }
    }

    override fun getItemViewType(position: Int): Int =
            if(items.get(position).title == null) TYPE_NOT_LOADED
            else TYPE_LOADED

    public fun changeItemAtPosition(position: Int, item: Item){
        items[position] = item
        notifyItemChanged(position)
    }

    public fun clearAndAddAll(item: List<Item>){
        items.clear()
        items.addAll(item)
        notifyDataSetChanged()
    }

    inner class LoadedViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        fun bind(item: Item){
            itemView.titleTextView.text = item.title
            itemView.authorTextView.text = item.by
            itemView.viewCommentsButton.text = String.format("view %d comments", item.descendants)
            itemView.dateTextView.text = item.getTimeInString()


            itemView.setOnClickListener {
                listener.onItemClick(item)
            }
            itemView.viewCommentsButton.setOnClickListener {
                listener.onOpenCommentsClick(item)
            }
        }
    }

    inner class NotLoadedViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

    }

    companion object {
        val TYPE_NOT_LOADED = 0
        val TYPE_LOADED = 1
    }
}