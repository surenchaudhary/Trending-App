package com.test.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.test.myapplication.adapters.DeveloperRecyclerAdapter;
import com.test.myapplication.adapters.OnRepoListener;
import com.test.myapplication.adapters.ReposotriesRecyclerAdapter;
import com.test.myapplication.rest.responses.DeveloperResponse;
import com.test.myapplication.rest.responses.RepositoryResponse;
import com.test.myapplication.utils.VerticalSpacingItemDecorator;
import com.test.myapplication.viewmodels.DevelopersListViewModels;
import com.test.myapplication.viewmodels.RepositoriesListViewModel;

import java.util.List;

public class MainActivity extends BaseActivity implements OnRepoListener {

    private static final String TAG = "MainActivity";
    private RepositoriesListViewModel mRepositoryListViewModel;
    private DevelopersListViewModels mDeveloperListViewModel;
    private RecyclerView mRecyclerView;
    private ReposotriesRecyclerAdapter mRepositoryAdapter;
    private DeveloperRecyclerAdapter mDeveloeprAdapter;
    private SearchView mSearchView;
    private TabLayout tabLayout;
    private boolean isRepoSelected = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tabs);
        setupTabs();
        mRecyclerView = findViewById(R.id.recyclerview);
        mSearchView = findViewById(R.id.search_view);
        mRepositoryListViewModel = ViewModelProviders.of(this).get(RepositoriesListViewModel.class);
        mDeveloperListViewModel = ViewModelProviders.of(this).get(DevelopersListViewModels.class);
        initRecyclerView();
        subscribeObservers();
        initSearchView();
        // display search categories
        loadRepositories();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }


    private void setupTabs() {
        tabLayout.addTab(tabLayout.newTab().setText(R.string.label_repositories));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.label_developers));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        loadRepositories();
                        break;
                    case 1:
                        loadDeveloeprs();
                        break;
                }
            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    private void loadRepositories() {
        mRecyclerView.setAdapter(mRepositoryAdapter);
        isRepoSelected = true;
        mRepositoryAdapter.displayLoading();
        mRepositoryListViewModel.getRepositories();
        mSearchView.clearFocus();
    }

    private void loadDeveloeprs() {
        mRecyclerView.setAdapter(mDeveloeprAdapter);
        isRepoSelected = false;
        mDeveloeprAdapter.displayLoading();
        mDeveloperListViewModel.getDevelopers();
        mSearchView.clearFocus();
    }


    private void subscribeObservers() {
        mRepositoryListViewModel.getRepositoriesList().observe(this, new Observer<List<RepositoryResponse>>() {
            @Override
            public void onChanged(@Nullable List<RepositoryResponse> repository) {
                if (repository != null) {
                    mRepositoryListViewModel.setIsPerformingQuery(false);
                    mRepositoryAdapter.setRepositories(repository);
                    mRepositoryAdapter.setQueryExhausted();
                }
            }
        });

        mRepositoryListViewModel.isQueryExhausted().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                Log.d(TAG, "onChanged: the query is exhausted..." + aBoolean);
                if (aBoolean) {
                    mRepositoryAdapter.setQueryExhausted();
                }
            }
        });

        mDeveloperListViewModel.getDevelopersList().observe(this, new Observer<List<DeveloperResponse>>() {
            @Override
            public void onChanged(@Nullable List<DeveloperResponse> developer) {
                if (developer != null) {
                    mDeveloperListViewModel.setIsPerformingQuery(false);
                    mDeveloeprAdapter.setDeveloeprss(developer);
                    mDeveloeprAdapter.setQueryExhausted();
                }
            }
        });

        mDeveloperListViewModel.isQueryExhausted().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                Log.d(TAG, "onChanged: the query is exhausted..." + aBoolean);
                if (aBoolean) {
                    mDeveloeprAdapter.setQueryExhausted();
                }
            }
        });
    }


    private void initRecyclerView() {
        mRepositoryAdapter = new ReposotriesRecyclerAdapter(this);
        mDeveloeprAdapter = new DeveloperRecyclerAdapter(this);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(30);
        mRecyclerView.addItemDecoration(itemDecorator);
        mRecyclerView.setAdapter(mRepositoryAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initSearchView() {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (isRepoSelected) {
                    mSearchView.clearFocus();
                    mRepositoryAdapter.filter(s.toString());
                } else {
                    mSearchView.clearFocus();
                    mDeveloeprAdapter.filter(s.toString());
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s)) {
                    if (isRepoSelected) {
                        loadRepositories();
                    } else {
                        loadDeveloeprs();
                    }
                }

                return false;
            }
        });
    }

    @Override
    public void onRepoClick(int position) {
        Intent intent = new Intent(this, RepoDetailActivity.class);
        intent.putExtra("repository", mRepositoryAdapter.getSelectedRepo(position));
        startActivity(intent);
    }

    @Override
    public void onDeveloperClick(int position) {
      /*  Intent intent = new Intent(this, RepoDetailActivity.class);
        intent.putExtra("recipe", mDeveloeprAdapter.getSelectedDeveloper(position));
        startActivity(intent);*/
    }

 /*   @Override
    public void onCategoryClick(String category) {
        mAdapter.displayLoading();
        mRepositoryListViewModel.searchRepositoriesApi(category, 1);
        mSearchView.clearFocus();
    }*/


}