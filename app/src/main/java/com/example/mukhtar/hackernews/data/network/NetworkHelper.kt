package com.example.mukhtar.hackernews.data.network

import com.example.mukhtar.hackernews.models.Item
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.Response


interface NetworkHelper {
    fun getNews(url: String): Single<Response<List<Int>>>
    fun getItemById(id: Int): Single<Response<Item>>
}
