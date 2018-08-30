package com.example.mukhtar.hackernews.ui.comments

import com.androidnetworking.error.ANError
import com.example.mukhtar.hackernews.data.database.DatabaseHelper
import com.example.mukhtar.hackernews.data.network.NetworkHelper
import com.example.mukhtar.hackernews.models.Comment
import com.example.mukhtar.hackernews.models.Item
import com.example.mukhtar.hackernews.ui.base.BasePresenter
import com.example.mukhtar.hackernews.utills.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class CommentsPresenter<V : CommentsMvpView> @Inject
constructor(networkHelper: NetworkHelper, databaseHelper: DatabaseHelper, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(networkHelper, databaseHelper, schedulerProvider, compositeDisposable),
        CommentsMvpPresenter<V> {

    private var itemId: Int = 0

    override fun onViewInitialized(itemId: Int) {
        this.itemId = itemId
        getComments()
    }

    override fun onRefresh() {
        getComments()
    }

    private fun getItemFromDatabaseById() {
        compositeDisposable.add(databaseHelper.getLoadedItemById(itemId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe {
                    mvpView.showLoading()
                }
                .doAfterTerminate {
                    mvpView.hideLoading()
                }
                .subscribe(
                        { itemResponse ->
                            if(itemResponse != null){
                                mvpView.onItemUpdated(itemResponse.kidsRealm.map { Comment(id = it) })
                            }
                        },
                        { e ->
                            handleApiError(ANError(e))
                        }
                )
        )
    }

    private fun getComments(){
        if(mvpView.isNetworkConnected){
            getItemFromNetwork()
        }else{
            mvpView.showNoNetworkMessage()
            getItemFromDatabaseById()
        }
    }

    override fun onCommentBound(item: Comment, position: Int) {
        if(mvpView.isNetworkConnected){
            getCommentFromNetwork(item, position)
        }else{
            getCommentFromDatabase(item, position)
        }
    }

    private fun getCommentFromNetwork(item: Comment, position: Int){
        compositeDisposable.add(networkHelper.getCommentById(item.id)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                        { itemResponse ->
                            if (itemResponse.isSuccessful) {
                                val updatedItem = itemResponse.body()
                                if(updatedItem != null){
                                    databaseHelper.insertOrUpdateComment(updatedItem)
                                    mvpView.onCommentUpdated(updatedItem, position)
                                }
                            } else {
                                handleApiError(ANError(itemResponse.raw()))
                            }
                        },
                        { e ->
                            handleApiError(ANError(e))
                        }
                )
        )
    }

    private fun getCommentFromDatabase(item: Comment, position: Int){
        compositeDisposable.add(databaseHelper.getLoadedCommentById(item.id)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                        { itemResponse ->
                            if(itemResponse != null)
                                mvpView.onCommentUpdated(itemResponse, position)
                        },
                        { e ->
                            Timber.e(e)
                        }
                )
        )
    }

    private fun getItemFromNetwork() {
        compositeDisposable.add(networkHelper.getItemById(itemId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe {
                    mvpView.showLoading()
                }
                .doAfterTerminate {
                    mvpView.hideLoading()
                }
                .subscribe(
                        { itemResponse ->
                            if (itemResponse.isSuccessful) {
                                val updatedItem = itemResponse.body()
                                if(updatedItem != null){
                                    databaseHelper.insertOrUpdateItem(updatedItem)
                                    mvpView.onItemUpdated(updatedItem.kidsRealm.map { Comment(id = it) })
                                }
                            } else {
                                handleApiError(ANError(itemResponse.raw()))
                            }
                        },
                        { e ->
                            handleApiError(ANError(e))
                        }
                )
        )
    }


}
