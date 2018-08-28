package com.example.mukhtar.hackernews.di.module;

import android.app.Application;
import android.content.Context;

import com.example.mukhtar.hackernews.data.database.AppDatabaseHelper;
import com.example.mukhtar.hackernews.data.database.DatabaseHelper;
import com.example.mukhtar.hackernews.data.network.AppNetworkHelper;
import com.example.mukhtar.hackernews.data.network.NetworkHelper;
import com.example.mukhtar.hackernews.di.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    NetworkHelper provideNetworkHelper(AppNetworkHelper appNetworkHelper) {
        return appNetworkHelper;
    }

    @Provides
    @Singleton
    DatabaseHelper provideDatabaseHelper(AppDatabaseHelper appDatabaseHelper) {
        return appDatabaseHelper;
    }
}
