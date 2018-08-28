package com.example.mukhtar.hackernews.data.database

import android.content.Context
import com.example.mukhtar.hackernews.di.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDatabaseHelper @Inject
constructor(@param:ApplicationContext private val mContext: Context) : DatabaseHelper {


}
