package com.example.mukhtar.hackernews

object Constants {
    const val API_STATUS_CODE_LOCAL_ERROR = 0
    const val BASE_URL = "https://hacker-news.firebaseio.com/v0/"

    const val NEW = 0
    const val TOP = 1
    const val BEST = 2

    val URL_ARRAY = arrayOf(
            "https://hacker-news.firebaseio.com/v0/newstories.json?print=pretty",
            "https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty",
            "https://hacker-news.firebaseio.com/v0/beststories.json?print=pretty"
    )
}