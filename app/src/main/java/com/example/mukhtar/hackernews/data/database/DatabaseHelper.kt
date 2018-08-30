package com.example.mukhtar.hackernews.data.database

import com.example.mukhtar.hackernews.models.Category
import com.example.mukhtar.hackernews.models.Comment
import com.example.mukhtar.hackernews.models.Item
import io.reactivex.Single
import retrofit2.Response

interface DatabaseHelper {
    public fun getLoadedItemById(id: Int): Single<Item?>
    public fun insertOrUpdateItem(item: Item)
    public fun saveItems(items: Category)
    public fun getAllItemsByCategory(category: Int): Single<Category>
    public fun insertOrUpdateComment(comment: Comment)
    public fun getLoadedCommentById(commentId: Int): Single<Comment?>
}