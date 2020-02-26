package com.test.myapplication.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.test.myapplication.repositories.TrendingRepositoryRepo;
import com.test.myapplication.rest.responses.RepositoryResponse;

import java.util.List;

/**
 * Created by Surendra Singh on 2/26/2020.
 * Created for Pitney Bowes
 */
public class RepositoriesListViewModel extends ViewModel {

    private TrendingRepositoryRepo mRepository;
    private boolean mIsViewingRepositories;
    private boolean mIsPerformingQuery;

    public RepositoriesListViewModel() {
        mRepository = TrendingRepositoryRepo.getInstance();
        mIsPerformingQuery = false;
    }

    public LiveData<RepositoryResponse> getRepository() {
        return mRepository.getRepository();
    }

    public LiveData<List<RepositoryResponse>> getRepositoriesList() {
       return mRepository.getRepositoriesList();
    }

    public void getRepositories() {
        mRepository.getRepositories();
    }


    public LiveData<Boolean> isQueryExhausted() {
        return mRepository.isQueryExhausted();
    }

    public void searchRepositoriesApi(String query) {
        mIsViewingRepositories = true;
        mIsPerformingQuery = true;
        mRepository.searchRepossitoryApi(query);
    }


    public boolean isViewingRepositories() {
        return mIsViewingRepositories;
    }

    public void setIsViewingRepositories(boolean isViewingepositories) {
        mIsViewingRepositories = isViewingepositories;
    }

    public void setIsPerformingQuery(Boolean isPerformingQuery) {
        mIsPerformingQuery = isPerformingQuery;
    }

    public boolean isPerformingQuery() {
        return mIsPerformingQuery;
    }

    public boolean onBackPressed() {
        if (mIsPerformingQuery) {
            // cancel the query
            mRepository.cancelRequest();
            mIsPerformingQuery = false;
        }
        if (mIsViewingRepositories) {
            mIsViewingRepositories = false;
            return false;
        }
        return true;
    }
}















