package com.example.mukhtar.hackernews.models

data class Item(
        val by: String? = null,
        val descendants: Int? = null,
        val id: Int,
        var kids: List<Int>? = null,
        var score: Int? = null,
        var time: Int? = null,
        var title: String? = null,
        var type: String? = null,
        var url: String? = null
)