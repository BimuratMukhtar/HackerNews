package com.example.mukhtar.hackernews.ui.news

import com.example.mukhtar.hackernews.models.Item
import com.example.mukhtar.hackernews.ui.base.MvpPresenter
import com.example.mukhtar.hackernews.ui.base.MvpView

interface NewsListMvpView: MvpView {
    fun updateAdapter(subjects: List<Item>)
    fun onItemResultCome(item: Item, position: Int)
    fun showNoNetworkMessage()
}

interface NewsListMvpPresenter<V : NewsListMvpView> : MvpPresenter<V> {
    fun onViewInitialized(type: Int)
    fun onRefresh()
    fun onItemBound(item: Item, position: Int)
}

interface NewsListListener{
    fun onItemClick(item: Item)
    fun onOpenCommentsClick(item: Item)
    fun onItemViewBound(item: Item, position: Int)
}