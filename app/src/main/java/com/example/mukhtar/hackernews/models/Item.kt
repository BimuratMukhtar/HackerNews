package com.example.mukhtar.hackernews.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.text.SimpleDateFormat
import java.util.*

open class Item(
        @PrimaryKey
        var id: Int = 0,
        var by: String? = null,
        var descendants: Int? = null,
        @Ignore
        var kids: List<Int>? = null,
        var kidsRealm: RealmList<Int> = RealmList(),
        var score: Int? = null,
        var time: Long? = null,
        var title: String? = null,
        var type: String? = null,
        var url: String? = null
): RealmObject(){
        fun getTimeInString(): String{
                val date = Date(time!!*1000)
                val postFormater = SimpleDateFormat("dd.MM.yyyy", Locale.US)
                return postFormater.format(date)
        }
}