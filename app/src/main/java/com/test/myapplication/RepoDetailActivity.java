package com.test.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.test.myapplication.rest.responses.RepositoryResponse;
import com.test.myapplication.viewmodels.RepositoryViewModel;

/**
 * Created by Surendra Singh on 2/26/2020.
 * Created for Pitney Bowes
 */
public class RepoDetailActivity extends BaseActivity {


    private static final String TAG = "RepoDetailActivity";

    private TextView tvRepoName;
    private TextView tvRepoDesp;
    private TextView tvRepoAuthor;
    private TextView tvRepoRating;
    private TextView tvRepoForks;
    private TextView tvBuiltBy;
    private LinearLayout layAuthors;


    private RepositoryViewModel mRepositoryViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repo_detail_screen);

        tvRepoName = findViewById(R.id.repo_title);
        tvRepoDesp = findViewById(R.id.repo_desc);
        tvRepoAuthor = findViewById(R.id.author_name);
        tvRepoRating = findViewById(R.id.repo_rating);
        tvRepoForks = findViewById(R.id.repo_fork);
        tvBuiltBy = findViewById(R.id.built_by);
        layAuthors = findViewById(R.id.lay_contributers);

        mRepositoryViewModel = ViewModelProviders.of(this).get(RepositoryViewModel.class);

        showProgressBar(true);
      //  subscribeObservers();
        getIncomingIntent();
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("repository")) {
            RepositoryResponse repository = getIntent().getParcelableExtra("repository");
            setRecipeProperties(repository);
        }
    }

    private void subscribeObservers() {
        mRepositoryViewModel.getRepository().observe(this, new Observer<RepositoryResponse>() {
            @Override
            public void onChanged(@Nullable RepositoryResponse recipe) {
                if (recipe != null) {
                    setRecipeProperties(recipe);
                    mRepositoryViewModel.setRetrievedRepository(true);
                }
            }
        });

        mRepositoryViewModel.isREpositoryeRequestTimedOut().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean && !mRepositoryViewModel.didRetrieveRepository()) {
                    Log.d(TAG, "onChanged: timed out..");
                    displayErrorScreen("Error retrieving data. Check network connection.");
                }
            }
        });
    }


    private void displayErrorScreen(String errorMessage) {
        tvRepoName.setText("Error retrieveing repository...");
        tvRepoDesp.setText("");
        tvRepoAuthor.setText("");
        tvRepoRating.setVisibility(View.GONE);
        tvRepoForks.setVisibility(View.GONE);
        tvBuiltBy.setVisibility(View.GONE);
        // layAuthors.setVisibility(View.GONE);
        TextView textView = new TextView(this);
        if (!errorMessage.equals("")) {
            textView.setText(errorMessage);
        } else {
            textView.setText("Error");
        }
        textView.setTextSize(15);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        layAuthors.addView(textView);


        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        showProgressBar(false);
    }

    private void setRecipeProperties(RepositoryResponse repository) {
        if (repository != null) {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);


            tvRepoName.setText(repository.getName());
            tvRepoDesp.setText(repository.getDescription());
            tvRepoAuthor.setText("Author: " + repository.getAuthor());
            tvRepoRating.setText(String.valueOf(repository.getStars()));
            tvRepoForks.setText(String.valueOf(repository.getForks()));

            layAuthors.removeAllViews();
            for (RepositoryResponse.BuiltByBean builtByBean : repository.getBuiltBy()) {
                ImageView imageAuthors = new ImageView(this);
                Glide.with(this)
                        .setDefaultRequestOptions(requestOptions)
                        .load(builtByBean.getAvatar())
                        .into(imageAuthors);
                imageAuthors.setLayoutParams(new LinearLayout.LayoutParams(
                        120, 120
                ));
                imageAuthors.setPadding(5,5,5,5);
                layAuthors.addView(imageAuthors);
            }
        }

        showProgressBar(false);
    }


}
