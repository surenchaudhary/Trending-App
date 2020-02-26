package com.test.myapplication.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.test.myapplication.repositories.TrendingRepositoryRepo;
import com.test.myapplication.rest.responses.RepositoryResponse;

/**
 * Created by Surendra Singh on 2/27/2020.
 * Created for Pitney Bowes
 */
public class RepositoryViewModel extends ViewModel {

    private TrendingRepositoryRepo mRepository;
    private boolean mDidRetrieveRepository;

    public RepositoryViewModel() {
        mRepository = TrendingRepositoryRepo.getInstance();
        mDidRetrieveRepository = false;
    }

    public LiveData<RepositoryResponse> getRepository() {
        return mRepository.getRepository();
    }

    public LiveData<Boolean> isREpositoryeRequestTimedOut() {
        return mRepository.isRepositoryRequestTimedOut();
    }


    public void setRetrievedRepository(boolean retrievedRepo) {
        mDidRetrieveRepository = retrievedRepo;
    }

    public boolean didRetrieveRepository() {
        return mDidRetrieveRepository;
    }
}

