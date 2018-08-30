package com.example.mukhtar.hackernews.di.component;

import com.example.mukhtar.hackernews.di.PerActivity;
import com.example.mukhtar.hackernews.di.module.ActivityModule;
import com.example.mukhtar.hackernews.ui.MainActivity;
import com.example.mukhtar.hackernews.ui.comments.CommentsActivity;
import com.example.mukhtar.hackernews.ui.news.NewsListFragment;

import dagger.Component;


@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(NewsListFragment newsListFragment);

    void inject(CommentsActivity commentsActivity);

}
