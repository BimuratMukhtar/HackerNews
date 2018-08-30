package com.example.mukhtar.hackernews.ui.news

import com.androidnetworking.error.ANError
import com.example.mukhtar.hackernews.Constants
import com.example.mukhtar.hackernews.data.database.DatabaseHelper
import com.example.mukhtar.hackernews.data.network.NetworkHelper
import com.example.mukhtar.hackernews.models.Category
import com.example.mukhtar.hackernews.models.Item
import com.example.mukhtar.hackernews.ui.base.BasePresenter
import com.example.mukhtar.hackernews.utills.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.realm.RealmList
import timber.log.Timber
import javax.inject.Inject

class NewsListPresenter<V : NewsListMvpView> @Inject
constructor(networkHelper: NetworkHelper, databaseHelper: DatabaseHelper, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(networkHelper, databaseHelper, schedulerProvider, compositeDisposable),
        NewsListMvpPresenter<V> {

    private var category: Int = -1

    override fun onViewInitialized(type: Int) {
        this.category = type
        getNews()
    }

    override fun onRefresh() {
        getNews()
    }

    private fun getNews(){
        if(mvpView.isNetworkConnected){
            getNewsFromNetwork()
        }else{
            mvpView.showNoNetworkMessage()
            getNewsFromDatabase()
        }
    }

    override fun onItemBound(item: Item, position: Int) {
        if(mvpView.isNetworkConnected){
            getItemFromNetwork(item, position)
        }else{
            getItemFromDatabase(item, position)
        }
    }

    private fun getItemFromNetwork(item: Item, position: Int){
        compositeDisposable.add(networkHelper.getItemById(item.id)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                        { itemResponse ->
                            if (itemResponse.isSuccessful) {
                                val updatedItem = itemResponse.body()
                                if(updatedItem != null){
                                    databaseHelper.insertOrUpdateItem(updatedItem)
                                    mvpView.onItemResultCome(updatedItem, position)
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

    private fun getItemFromDatabase(item: Item, position: Int){
        compositeDisposable.add(databaseHelper.getLoadedItemById(item.id)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                        { itemResponse ->
                            if(itemResponse != null)
                                mvpView.onItemResultCome(itemResponse, position)
                        },
                        { e ->
                            Timber.e(e)
                        }
                )
        )
    }

    private fun getNewsFromNetwork() {
        compositeDisposable.add(networkHelper.getNews(Constants.URL_ARRAY[category])
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe {
                    mvpView.showLoading()
                }
                .doAfterTerminate {
                    mvpView.hideLoading()
                }
                .subscribe(
                        { retrievedItems ->
                            if (retrievedItems.isSuccessful) {
                                val items = retrievedItems.body()!!
                                val realmItems = RealmList<Int>()
                                realmItems.addAll(items)
                                databaseHelper.saveItems(Category(category, realmItems))
                                mvpView.updateAdapter(items.map { Item(id = it) })
                            } else {
                                handleApiError(ANError(retrievedItems.raw()))
                            }
                        },
                        { e ->
                            handleApiError(ANError(e))
                        }
                )
        )
    }

    private fun getNewsFromDatabase(){
        compositeDisposable.add(databaseHelper.getAllItemsByCategory(category)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe {
                    mvpView.showLoading()
                }
                .doAfterTerminate {
                    mvpView.hideLoading()
                }
                .subscribe(
                        { category ->
                            if(category.items != null)
                                mvpView.updateAdapter(category.items!!.map { Item(id = it) })
                        },
                        { e ->
                            handleApiError(ANError(e))
                        }
                )
        )
    }
}
