package com.example.mukhtar.hackernews.ui

import android.os.Bundle
import com.example.mukhtar.hackernews.ui.news.adapter.ViewPagerAdapter
import android.support.v4.view.ViewPager
import com.example.mukhtar.hackernews.Constants
import com.example.mukhtar.hackernews.ui.news.NewsListFragment
import com.example.mukhtar.hackernews.R
import com.example.mukhtar.hackernews.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_news_list.*


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)
        setSupportActionBar(toolbar)

        setupViewPager(viewpager)

        tablayout.setupWithViewPager(viewpager)


    }

    override fun setUp() {

    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(NewsListFragment.newInstance(Constants.NEW), "New")
        adapter.addFragment(NewsListFragment.newInstance(Constants.TOP), "Top")
        adapter.addFragment(NewsListFragment.newInstance(Constants.BEST), "Best")
        viewPager.adapter = adapter
    }
}
