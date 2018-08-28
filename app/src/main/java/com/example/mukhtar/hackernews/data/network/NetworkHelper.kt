package com.example.mukhtar.hackernews.data.network

import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.Response


interface NetworkHelper {
    fun getNews(url: String): Single<Response<List<Int>>>
}
