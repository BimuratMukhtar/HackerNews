package com.example.mukhtar.hackernews.ui.news

import com.example.mukhtar.hackernews.models.Item
import com.example.mukhtar.hackernews.ui.base.MvpPresenter
import com.example.mukhtar.hackernews.ui.base.MvpView

interface NewsListMvpView: MvpView {
    fun updateAdapter(subjects: List<Item>)
    fun onItemClicked(item: Item)
}

interface NewsListMvpPresenter<V : NewsListMvpView> : MvpPresenter<V> {
    fun onViewInitialized(url: String)
    fun onRefresh()
}