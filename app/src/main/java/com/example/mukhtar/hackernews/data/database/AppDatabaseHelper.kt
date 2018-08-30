package com.example.mukhtar.hackernews.data.database

import android.content.Context
import com.example.mukhtar.hackernews.di.ApplicationContext
import com.example.mukhtar.hackernews.models.Category
import com.example.mukhtar.hackernews.models.Comment
import com.example.mukhtar.hackernews.models.Item
import io.reactivex.Single
import io.realm.Realm
import io.realm.RealmList
import io.realm.Sort
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDatabaseHelper @Inject
constructor(@param:ApplicationContext private val mContext: Context) : DatabaseHelper {

    override fun getLoadedItemById(id: Int): Single<Item?> {
        val item = Realm.getDefaultInstance().where(Item::class.java).equalTo("id", id).isNotNull("title").findFirst()
        if(item != null)
            return Single.just(item)
        else{
            return Single.error(NullPointerException())
        }
    }

    override fun insertOrUpdateItem(item: Item) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            val kids = RealmList<Int>()
            if(item.kids!=null)
                kids.addAll(item.kids!!)
            item.kidsRealm = kids
            it.insertOrUpdate(item)
        }
        realm.close()
    }

    override fun insertOrUpdateComment(comment: Comment) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            val kids = RealmList<Int>()
            if(comment.kids!=null)
                kids.addAll(comment.kids!!)
            comment.kidsRealm = kids
            it.insertOrUpdate(comment)
        }
        realm.close()
    }

    override fun getLoadedCommentById(commentId: Int): Single<Comment?> {
        val item = Realm.getDefaultInstance().where(Comment::class.java).equalTo("id", commentId).isNotNull("text").findFirst()
        if(item != null)
            return Single.just(item)
        else{
            return Single.error(NullPointerException())
        }
    }

    @Synchronized override fun saveItems(items: Category) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            it.insertOrUpdate(items)
        }
        realm.close()
    }

    override fun getAllItemsByCategory(categoryId: Int): Single<Category> {
        val items = Realm.getDefaultInstance().where(Category::class.java).equalTo("id", categoryId)
                .findFirst()
        return Single.just(items)
    }
}
