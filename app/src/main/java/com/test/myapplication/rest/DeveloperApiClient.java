package com.test.myapplication.rest;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.test.myapplication.AppExecutors;
import com.test.myapplication.rest.responses.DeveloperResponse;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

import static com.test.myapplication.utils.Constants.NETWORK_TIMEOUT;

/**
 * Created by Surendra Singh on 2/26/2020.
 * Created for Pitney Bowes
 */
public class DeveloperApiClient {

    private static final String TAG = "DeveloperApiClient";

    private static DeveloperApiClient instance;
    private MutableLiveData<List<DeveloperResponse>> mDevelopers;
    private MutableLiveData<DeveloperResponse> mDeveloper;
    private DeveloperApiClient.RetrieveDevelopersRunnable mRetrieveDeveloepersRunnable;
    private MutableLiveData<Boolean> mDeveloeprsRequestTimeout = new MutableLiveData<>();

    public static DeveloperApiClient getInstance() {
        if (instance == null) {
            instance = new DeveloperApiClient();
        }
        return instance;
    }

    private DeveloperApiClient() {
        mDevelopers = new MutableLiveData<>();
    }


    public void getDevelopers() {
        if (mRetrieveDeveloepersRunnable != null) {
            mRetrieveDeveloepersRunnable = null;
        }
        mRetrieveDeveloepersRunnable = new RetrieveDevelopersRunnable();
        final Future handler = AppExecutors.getInstance().networkIO().submit(mRetrieveDeveloepersRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // let the user know its timed out
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }


    public LiveData<DeveloperResponse> getDeveloper() {
        return mDeveloper;
    }


    public LiveData<List<DeveloperResponse>> getDevelopersList() {
        return mDevelopers;
    }

    public LiveData<Boolean> isDeveloperRequestTimedOut() {
        return mDeveloeprsRequestTimeout;
    }


    private class RetrieveDevelopersRunnable implements Runnable {

        boolean cancelRequest;

        public RetrieveDevelopersRunnable() {
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getDevelopers().execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    List<DeveloperResponse> developers = ((List<DeveloperResponse>) response.body());
                    mDevelopers.postValue(developers);
                } else {
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: " + error);
                    mDevelopers.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mDevelopers.postValue(null);
            }

        }

        private Call<List<DeveloperResponse>> getDevelopers() {
            return ServiceGenerator.getTrendingApi().getDevelopers();
        }

        private void cancelRequest() {
            Log.d(TAG, "cancelRequest: canceling the search request.");
            cancelRequest = true;
        }
    }

    public void cancelRequest() {
        if (mRetrieveDeveloepersRunnable != null) {
            mRetrieveDeveloepersRunnable.cancelRequest();
        }

    }
}

