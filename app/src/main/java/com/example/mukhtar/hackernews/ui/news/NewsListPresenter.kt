package com.example.mukhtar.hackernews.ui.news

import com.androidnetworking.error.ANError
import com.example.mukhtar.hackernews.data.database.DatabaseHelper
import com.example.mukhtar.hackernews.data.network.NetworkHelper
import com.example.mukhtar.hackernews.models.Item
import com.example.mukhtar.hackernews.ui.base.BasePresenter
import com.example.mukhtar.hackernews.utills.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class NewsListPresenter<V : NewsListMvpView> @Inject
constructor(networkHelper: NetworkHelper, databaseHelper: DatabaseHelper, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(networkHelper, databaseHelper, schedulerProvider, compositeDisposable),
        NewsListMvpPresenter<V> {

    private lateinit var url: String

    override fun onViewInitialized(url: String) {
        this.url = url
        getNews()
    }

    override fun onRefresh() {
        getNews()
    }

    override fun onItemBound(item: Item, position: Int) {
        compositeDisposable.add(networkHelper.getItemById(item.id)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                { itemResponse ->
                    if (itemResponse.isSuccessful) {
                        mvpView.onItemResultCome(itemResponse.body()!!, position)
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

    private fun getNews() {
        compositeDisposable.add(networkHelper.getNews(url)
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
                                mvpView.updateAdapter(retrievedItems.body()!!.map { Item(id = it) })
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
}
