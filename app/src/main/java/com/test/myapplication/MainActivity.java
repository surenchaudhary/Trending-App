package com.test.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.test.myapplication.adapters.OnRepoListener;
import com.test.myapplication.adapters.ReposotriesRecyclerAdapter;
import com.test.myapplication.rest.responses.RepositoryResponse;
import com.test.myapplication.utils.VerticalSpacingItemDecorator;
import com.test.myapplication.viewmodels.RepositoriesListViewModel;

import java.util.List;

public class MainActivity extends BaseActivity implements OnRepoListener {

    private static final String TAG = "MainActivity";
    private RepositoriesListViewModel mRepositoryListViewModel;
    private RecyclerView mRecyclerView;
    private ReposotriesRecyclerAdapter mAdapter;
    private SearchView mSearchView;
    private TabLayout tabLayout;
    private boolean isRepoSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tabs);
        setupTabs();
        mRecyclerView = findViewById(R.id.recyclerview);
        mSearchView = findViewById(R.id.search_view);
        mRepositoryListViewModel = ViewModelProviders.of(this).get(RepositoriesListViewModel.class);
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

     /*   ConstraintLayout tabOne = (ConstraintLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab_item, null);
        ImageView usps = tabOne.findViewById(R.id.iv_carrier);
        usps.setImageResource(R.drawable.usps);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        ConstraintLayout tabTwo = (ConstraintLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab_item, null);
        ImageView fedex = tabTwo.findViewById(R.id.iv_carrier);
        fedex.setImageResource(R.drawable.fedex);

        tabLayout.getTabAt(1).setCustomView(tabTwo);

        ConstraintLayout tabThree = (ConstraintLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab_item, null);
        ImageView ups = tabThree.findViewById(R.id.iv_carrier);
        ups.setImageResource(R.drawable.ups);

        tabLayout.getTabAt(2).setCustomView(tabThree);*/
    }

    private void loadRepositories() {
        isRepoSelected = true;
        mAdapter.displayLoading();
        mRepositoryListViewModel.getRepositories();
        mSearchView.clearFocus();
    }

    private void loadDeveloeprs() {
        isRepoSelected = false;
    }

    private void subscribeObservers() {
        mRepositoryListViewModel.getRepositoriesList().observe(this, new Observer<List<RepositoryResponse>>() {
            @Override
            public void onChanged(@Nullable List<RepositoryResponse> repository) {
                if (repository != null) {
                    mRepositoryListViewModel.setIsPerformingQuery(false);
                    mAdapter.setRepositories(repository);
                }
            }
        });

        mRepositoryListViewModel.isQueryExhausted().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                Log.d(TAG, "onChanged: the query is exhausted..." + aBoolean);
                if (aBoolean) {
                    mAdapter.setQueryExhausted();
                }
            }
        });
    }


    private void initRecyclerView() {
        mAdapter = new ReposotriesRecyclerAdapter(this);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(30);
        mRecyclerView.addItemDecoration(itemDecorator);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initSearchView() {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mAdapter.displayLoading();
                mRepositoryListViewModel.searchRepositoriesApi(s);
                mSearchView.clearFocus();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    @Override
    public void onRepoClick(int position) {
        Intent intent = new Intent(this, RepoDetailActivity.class);
        intent.putExtra("recipe", mAdapter.getSelectedRepo(position));
        startActivity(intent);
    }

 /*   @Override
    public void onCategoryClick(String category) {
        mAdapter.displayLoading();
        mRepositoryListViewModel.searchRepositoriesApi(category, 1);
        mSearchView.clearFocus();
    }*/


}