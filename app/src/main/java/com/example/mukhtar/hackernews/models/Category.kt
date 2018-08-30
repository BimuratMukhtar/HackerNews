package com.example.mukhtar.hackernews.models

import com.example.mukhtar.hackernews.Constants
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Category (
        @PrimaryKey
        var id: Int = Constants.NEW,
        var items: RealmList<Int>? = null
):RealmObject()