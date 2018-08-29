package com.example.mukhtar.hackernews.ui.news

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mukhtar.hackernews.R
import com.example.mukhtar.hackernews.models.Item
import com.example.mukhtar.hackernews.ui.base.BaseFragment
import com.example.mukhtar.hackernews.ui.item_detail.ItemDetailActivity
import com.example.mukhtar.hackernews.ui.news.adapter.NewsListAdapter
import kotlinx.android.synthetic.main.fragment_news_list.*
import timber.log.Timber
import javax.inject.Inject

private const val ARG_URL = "url"

class NewsListFragment : BaseFragment(), NewsListMvpView, NewsListListener {

    @Inject
    lateinit var mPresenter: NewsListMvpPresenter<NewsListMvpView>

    private lateinit var url: String
    private var newsListAdapter: NewsListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(ARG_URL)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activityComponent.inject(this)
        mPresenter.onAttach(this)
        return inflater.inflate(R.layout.fragment_news_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.onViewInitialized(url)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.onDetach()
    }

    override fun setUp(view: View?) {

        newsListAdapter = NewsListAdapter(this)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            if(adapter == null){
                adapter = newsListAdapter
            }
        }

        swipeRefreshLayout.setOnRefreshListener {
            mPresenter.onRefresh()
        }
    }

    override fun showLoading() {
        if(!swipeRefreshLayout.isRefreshing){
            swipeRefreshLayout.isRefreshing = true
        }
    }

    override fun hideLoading() {
        if(swipeRefreshLayout.isRefreshing){
            swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun updateAdapter(subjects: List<Item>) {
        Timber.d("newsListAdapter updated %s", subjects.size.toString())
        newsListAdapter?.clearAndAddAll(subjects)

    }

    override fun onItemClick(item: Item) {
        if(item.url != null){
            ItemDetailActivity.open(this.context!!, item.url!!)
        }else{
            showMessage("Does not have url!")
        }
    }

    override fun onOpenCommentsClick(item: Item) {
        showMessage("Opening Comments")
    }

    override fun onItemResultCome(item: Item, position: Int) {
        newsListAdapter?.changeItemAtPosition(position, item)
    }

    override fun onItemViewBound(item: Item, position: Int) {
        mPresenter.onItemBound(item, position)
    }

    companion object {
        @JvmStatic
        fun newInstance(url: String) =
                NewsListFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_URL, url)
                    }
                }
    }
}
