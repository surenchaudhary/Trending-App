package com.test.myapplication.rest;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.test.myapplication.AppExecutors;
import com.test.myapplication.rest.responses.RepositoryResponse;

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
public class RepositoriesApiClient {

    private static final String TAG = "RepositoriesApiClient";

    private static RepositoriesApiClient instance;
    private MutableLiveData<List<RepositoryResponse>> mRepositories;
    private MutableLiveData<RepositoryResponse> mRepository;
    private RetrieveRepositoriesRunnable mRetrieveRepositoriesRunnable;
    private MutableLiveData<Boolean> mRepositoriesRequestTimeout = new MutableLiveData<>();

    public static RepositoriesApiClient getInstance() {
        if (instance == null) {
            instance = new RepositoriesApiClient();
        }
        return instance;
    }

    private RepositoriesApiClient() {
        mRepositories = new MutableLiveData<>();
    }


    public void getReposities() {
        if (mRetrieveRepositoriesRunnable != null) {
            mRetrieveRepositoriesRunnable = null;
        }
        mRetrieveRepositoriesRunnable = new RetrieveRepositoriesRunnable();
        final Future handler = AppExecutors.getInstance().networkIO().submit(mRetrieveRepositoriesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // let the user know its timed out
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }


    public LiveData<RepositoryResponse> getRepository() {
        return mRepository;
    }


    public LiveData<List<RepositoryResponse>> getRepositoryList() {
        return mRepositories;
    }

    public LiveData<Boolean> isRepositoryRequestTimedOut() {
        return mRepositoriesRequestTimeout;
    }


    private class RetrieveRepositoriesRunnable implements Runnable {

        boolean cancelRequest;

        public RetrieveRepositoriesRunnable() {
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getRepositories().execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    List<RepositoryResponse> repositories = ((List<RepositoryResponse>) response.body());
                    mRepositories.postValue(repositories);
                } else {
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: " + error);
                    mRepositories.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mRepositories.postValue(null);
            }

        }

        private Call<List<RepositoryResponse>> getRepositories() {
            return ServiceGenerator.getTrendingApi().getRepositories();
        }

        private void cancelRequest() {
            Log.d(TAG, "cancelRequest: canceling the search request.");
            cancelRequest = true;
        }
    }

    public void cancelRequest() {
        if (mRetrieveRepositoriesRunnable != null) {
            mRetrieveRepositoriesRunnable.cancelRequest();
        }

    }
}

