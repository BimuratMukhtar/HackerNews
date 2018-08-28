package com.example.mukhtar.hackernews.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.example.mukhtar.hackernews.di.ActivityContext;
import com.example.mukhtar.hackernews.ui.news.NewsListFragment;
import com.example.mukhtar.hackernews.ui.news.NewsListMvpPresenter;
import com.example.mukhtar.hackernews.ui.news.NewsListMvpView;
import com.example.mukhtar.hackernews.ui.news.NewsListPresenter;
import com.example.mukhtar.hackernews.utills.AppSchedulerProvider;
import com.example.mukhtar.hackernews.utills.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    NewsListMvpPresenter<NewsListMvpView> provideRatePresenter(
            NewsListPresenter<NewsListMvpView> presenter){
        return presenter;
    }
//
//    @Provides
//    @PerActivity
//    ChatMvpPresenter<ChatMvpView> provideChatPresenter(
//            ChatPresenter<ChatMvpView> presenter) {
//        return presenter;
//    }



}
