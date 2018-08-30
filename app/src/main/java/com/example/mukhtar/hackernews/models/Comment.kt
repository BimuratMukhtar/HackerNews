package com.example.mukhtar.hackernews.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*


open class Comment(
        @PrimaryKey
        var id: Int = 0,
        var by: String? = null,
        var text: String? = null,
        var type: String? = null,
        var time: Long = 0,
        var parent: Int? = null,
        @Ignore
        var kids: List<Int>? = null,
        var kidsRealm: RealmList<Int> = RealmList()
): RealmObject(){
        fun getTimeInString(): String{
                val date = Date(time*1000)
                val postFormater = SimpleDateFormat("dd.MM.yyyy", Locale.US)
                return postFormater.format(date)
        }
}