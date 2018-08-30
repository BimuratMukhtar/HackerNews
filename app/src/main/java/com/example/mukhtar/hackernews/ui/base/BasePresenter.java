package com.example.mukhtar.hackernews.ui.base;


import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.example.mukhtar.hackernews.Constants;
import com.example.mukhtar.hackernews.R;
import com.example.mukhtar.hackernews.SingletonSharedPref;
import com.example.mukhtar.hackernews.data.database.DatabaseHelper;
import com.example.mukhtar.hackernews.data.network.ApiError;
import com.example.mukhtar.hackernews.data.network.NetworkHelper;
import com.example.mukhtar.hackernews.utills.SchedulerProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.net.UnknownHostException;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * onAttach() and onDetach(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling getMvpView().
 */
public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private static final String TAG = "BasePresenter";


    private final NetworkHelper networkHelper;
    private final SchedulerProvider mSchedulerProvider;
    private final CompositeDisposable mCompositeDisposable;
    private final DatabaseHelper databaseHelper;

    private V mMvpView;

    @Inject
    public BasePresenter(NetworkHelper dataManager,
                         DatabaseHelper databaseHelper,
                         SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable) {
        this.networkHelper = dataManager;
        this.mSchedulerProvider = schedulerProvider;
        this.mCompositeDisposable = compositeDisposable;
        this.databaseHelper = databaseHelper;
    }

    @Override
    public void onAttach(V mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void onDetach() {
        mCompositeDisposable.dispose();
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public V getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {

    }

    public NetworkHelper getNetworkHelper() {
        return networkHelper;
    }

    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public SingletonSharedPref getSharedPreferences() {
        return SingletonSharedPref.getInstance();
    }

    public DatabaseHelper getDatabaseHelper() {
        return databaseHelper;
    }

    @Override
    public void handleApiError(ANError error) {

        if(error != null && error.getCause() instanceof UnknownHostException){
            getMvpView().onError(R.string.internet_connection_error);
            return;
        }

        if (error == null || error.getErrorBody() == null) {
            getMvpView().onError(R.string.api_default_error);
            return;
        }

        if (error.getErrorCode() == Constants.API_STATUS_CODE_LOCAL_ERROR
                && error.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
            getMvpView().onError(R.string.internet_connection_error);
            return;
        }

        if (error.getErrorCode() == Constants.API_STATUS_CODE_LOCAL_ERROR
                && error.getErrorDetail().equals(ANConstants.REQUEST_CANCELLED_ERROR)) {
            getMvpView().onError(R.string.api_retry_error);
            return;
        }

        final GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();

        try {
            ApiError apiError = gson.fromJson(error.getErrorBody(), ApiError.class);

            if (apiError == null || apiError.getMessage() == null) {
                getMvpView().onError(R.string.api_default_error);
                return;
            }

            switch (error.getErrorCode()) {
                case HttpsURLConnection.HTTP_UNAUTHORIZED:
                case HttpsURLConnection.HTTP_FORBIDDEN:
//                    setUserAsLoggedOut();
//                    getMvpView().openActivityOnTokenExpire();
                case HttpsURLConnection.HTTP_INTERNAL_ERROR:
                case HttpsURLConnection.HTTP_NOT_FOUND:
                default:
                    getMvpView().onError(apiError.getMessage());
            }
        } catch (JsonSyntaxException | NullPointerException e) {
            getMvpView().onError(R.string.api_default_error);
        }
    }

//    @Override
//    public void setUserAsLoggedOut() {
//        getNetworkHelper().setUserAsLoggedOut();
//    }
}