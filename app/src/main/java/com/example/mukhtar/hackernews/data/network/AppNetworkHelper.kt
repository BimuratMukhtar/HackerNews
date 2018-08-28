package com.example.mukhtar.hackernews.data.network

import android.content.Context
import com.example.mukhtar.hackernews.api.RestAPI
import com.example.mukhtar.hackernews.di.ApplicationContext
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppNetworkHelper @Inject
constructor(@param:ApplicationContext private val mContext: Context) : NetworkHelper {

    override fun getNews(url: String): Single<Response<List<Int>>> {
        return RestAPI.getApi().getItemIds(url)
    }
}
