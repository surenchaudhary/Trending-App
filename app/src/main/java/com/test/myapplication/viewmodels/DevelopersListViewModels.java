package com.test.myapplication.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.test.myapplication.repositories.TrendingDeveloperRepository;
import com.test.myapplication.rest.responses.DeveloperResponse;

import java.util.List;

/**
 * Created by Surendra Singh on 2/27/2020.
 * Created for Pitney Bowes
 */
public class DevelopersListViewModels extends ViewModel {

    private TrendingDeveloperRepository mRepository;
    private boolean mIsViewingDeveloeprs;
    private boolean mIsPerformingQuery;

    public DevelopersListViewModels() {
        mRepository = TrendingDeveloperRepository.getInstance();
        mIsPerformingQuery = false;
    }

    public LiveData<DeveloperResponse> getRepository() {
        return mRepository.getDeveloper();
    }

    public LiveData<List<DeveloperResponse>> getDevelopersList() {
        return mRepository.getDevelopersList();
    }

    public void getDevelopers() {
        mRepository.getDevelopers();
    }


    public LiveData<Boolean> isQueryExhausted() {
        return mRepository.isQueryExhausted();
    }

    public void searchDevelopersApi(String query) {
        mIsViewingDeveloeprs = true;
        mIsPerformingQuery = true;
        mRepository.searchRepossitoryApi(query);
    }


    public boolean isViewingDeveloeprs() {
        return mIsViewingDeveloeprs;
    }

    public void setIsViewingDeveloeprs(boolean isViewing) {
        mIsViewingDeveloeprs = isViewing;
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
        if (mIsViewingDeveloeprs) {
            mIsViewingDeveloeprs = false;
            return false;
        }
        return true;
    }
}
















