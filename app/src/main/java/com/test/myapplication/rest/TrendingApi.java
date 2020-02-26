package com.test.myapplication.rest;

import com.test.myapplication.rest.responses.RepositoryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Surendra Singh on 2/26/2020.
 * Created for Pitney Bowes
 */
public interface TrendingApi {

    // GET Repositories REQUEST
    @GET("repositories")
    Call<List<RepositoryResponse>> getRepositories();

}
