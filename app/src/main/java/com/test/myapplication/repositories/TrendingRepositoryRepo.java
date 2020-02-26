package com.test.myapplication.repositories;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.test.myapplication.rest.RepositoriesApiClient;
import com.test.myapplication.rest.responses.RepositoryResponse;

import java.util.List;

/**
 * Created by Surendra Singh on 2/26/2020.
 * Created for Pitney Bowes
 */
public class TrendingRepositoryRepo {

    private static TrendingRepositoryRepo instance;
    private RepositoriesApiClient mRepositoryApiClient;
    private String mQuery;
    private MutableLiveData<Boolean> mIsQueryExhausted = new MutableLiveData<>();
    private MediatorLiveData<List<RepositoryResponse>> mRepositories = new MediatorLiveData<>();

    public static TrendingRepositoryRepo getInstance() {
        if (instance == null) {
            instance = new TrendingRepositoryRepo();
        }
        return instance;
    }

    private TrendingRepositoryRepo() {
        mRepositoryApiClient = RepositoriesApiClient.getInstance();
        initMediators();
    }

    private void initMediators() {
        LiveData<List<RepositoryResponse>> repoListApiSource = mRepositoryApiClient.getRepositoryList();
        mRepositories.addSource(repoListApiSource, new Observer<List<RepositoryResponse>>() {
            @Override
            public void onChanged(@Nullable List<RepositoryResponse> repositories) {

                if (repositories != null) {
                    mRepositories.setValue(repositories);
                    doneQuery(repositories);
                } else {
                    // search database cache
                    doneQuery(null);
                }
            }
        });
    }

    private void doneQuery(List<RepositoryResponse> list) {
        if (list != null) {
            mIsQueryExhausted.setValue(true);
        } else {
            mIsQueryExhausted.setValue(true);
        }
    }

    public LiveData<Boolean> isQueryExhausted() {
        return mIsQueryExhausted;
    }

    public void getRepositories() {
        mRepositoryApiClient.getReposities();
    }

    public LiveData<List<RepositoryResponse>> getRepositoriesList() {
        return mRepositoryApiClient.getRepositoryList();
    }

    public LiveData<RepositoryResponse> getRepository() {
        return mRepositoryApiClient.getRepository();
    }


    public void searchRepositoriesById(String repoId) {
        // mRepositoryApiClient.searchRepoById(repoId);
    }


    public void searchRepossitoryApi(String query) {
        mQuery = query;
        mIsQueryExhausted.setValue(false);
        // mRepositoryApiClient.searchRepositoryApi(query, pageNumber);
    }


    public void cancelRequest() {
        mRepositoryApiClient.cancelRequest();
    }

    public LiveData<Boolean> isRepositoryRequestTimedOut() {
        return mRepositoryApiClient.isRepositoryRequestTimedOut();
    }


}





