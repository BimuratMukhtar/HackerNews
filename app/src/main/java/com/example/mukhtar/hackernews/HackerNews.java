package com.example.mukhtar.hackernews;

import android.app.Application;
import android.content.Context;

import com.androidnetworking.AndroidNetworking;
import com.example.mukhtar.hackernews.data.network.NetworkHelper;
import com.example.mukhtar.hackernews.di.component.ApplicationComponent;
import com.example.mukhtar.hackernews.di.component.DaggerApplicationComponent;
import com.example.mukhtar.hackernews.di.module.ApplicationModule;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

public class HackerNews extends Application {
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        AndroidNetworking.initialize(getApplicationContext());

        SingletonSharedPref.getInstance(getBaseContext());

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        mApplicationComponent.inject(this);

    }

    public static HackerNews get(Context context){
        return (HackerNews) context.getApplicationContext();
    }

    public ApplicationComponent getComponent(){ return mApplicationComponent;}

}
