package com.test.myapplication.repositories;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.test.myapplication.rest.DeveloperApiClient;
import com.test.myapplication.rest.responses.DeveloperResponse;

import java.util.List;

/**
 * Created by Surendra Singh on 2/27/2020.
 * Created for Pitney Bowes
 */
public class TrendingDeveloperRepository {

    private static TrendingDeveloperRepository instance;
    private DeveloperApiClient developerApiClient;
    private String mQuery;
    private MutableLiveData<Boolean> mIsQueryExhausted = new MutableLiveData<>();
    private MediatorLiveData<List<DeveloperResponse>> mDevelopers = new MediatorLiveData<>();

    public static TrendingDeveloperRepository getInstance() {
        if (instance == null) {
            instance = new TrendingDeveloperRepository();
        }
        return instance;
    }

    private TrendingDeveloperRepository() {
        developerApiClient = DeveloperApiClient.getInstance();
        initMediators();
    }

    private void initMediators() {
        LiveData<List<DeveloperResponse>> repoListApiSource = developerApiClient.getDevelopersList();
        mDevelopers.addSource(repoListApiSource, new Observer<List<DeveloperResponse>>() {

            @Override
            public void onChanged(@Nullable List<DeveloperResponse> developers) {

                if (developers != null) {
                    mDevelopers.setValue(developers);
                    doneQuery(developers);
                } else {
                    // search database cache
                    doneQuery(null);
                }
            }
        });
    }

    private void doneQuery(List<DeveloperResponse> list) {
        if (list != null) {
            mIsQueryExhausted.setValue(true);
        } else {
            mIsQueryExhausted.setValue(true);
        }
    }

    public LiveData<Boolean> isQueryExhausted() {
        return mIsQueryExhausted;
    }

    public void getDevelopers() {
        developerApiClient.getDevelopers();
    }

    public LiveData<List<DeveloperResponse>> getDevelopersList() {
        return developerApiClient.getDevelopersList();
    }

    public LiveData<DeveloperResponse> getDeveloper() {
        return developerApiClient.getDeveloper();
    }


    public void searchRepositoriesById(String repoId) {
        // developerApiClient.searchRepoById(repoId);
    }


    public void searchRepossitoryApi(String query) {
        mQuery = query;
        mIsQueryExhausted.setValue(false);
        // developerApiClient.searchRepositoryApi(query, pageNumber);
    }


    public void cancelRequest() {
        developerApiClient.cancelRequest();
    }

    public LiveData<Boolean> isDeveloperRequestTimedOut() {
        return developerApiClient.isDeveloperRequestTimedOut();
    }


}





