package com.example.mukhtar.hackernews.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mukhtar.hackernews.R
import com.example.mukhtar.hackernews.models.Item
import com.example.mukhtar.hackernews.ui.base.BaseFragment
import timber.log.Timber
import javax.inject.Inject

private const val ARG_URL = "url"

class NewsListFragment : BaseFragment(), NewsListMvpView {

    @Inject
    lateinit var mPresenter: NewsListMvpPresenter<NewsListMvpView>

    private lateinit var url: String

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

    }

    override fun updateAdapter(subjects: List<Item>) {
        Timber.d("adapter updated %s", subjects.size.toString())

    }

    override fun onItemClicked(item: Item) {

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
