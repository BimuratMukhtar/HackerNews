package com.example.mukhtar.hackernews.api

import com.example.mukhtar.hackernews.SingletonSharedPref
import com.example.mukhtar.hackernews.models.Item
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface APIInterface {

//    @FormUrlEncoded
//    @POST("api/token")
//    fun getToken(
//            @Field("username") username: String,
//            @Field("password") password: String
//    ): Single<Response<LoginResponse>>
//
    @GET("item/{id}.json?print=pretty")
    fun getItemById(
            @Path("id") id: Int
    ): Single<Response<Item>>

    @GET
    fun getItemIds(
            @Url url: String
    ): Single<Response<List<Int>>>



}