package com.test.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.test.myapplication.R;
import com.test.myapplication.rest.responses.RepositoryResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Surendra Singh on 2/26/2020.
 * Created for Pitney Bowes
 */
public class ReposotriesRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int REPOSITORY_TYPE = 1;
    private static final int LOADING_TYPE = 2;
    private static final int EXHAUSTED_TYPE = 3;

    private List<RepositoryResponse> mRepositories;
    private OnRepoListener mOnRepoListener;

    public ReposotriesRecyclerAdapter(OnRepoListener mOnRepoListener) {
        this.mOnRepoListener = mOnRepoListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = null;
        switch (i) {

            case REPOSITORY_TYPE: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_repository_list_item, viewGroup, false);
                return new RepoViewHolder(view, mOnRepoListener);
            }

            case LOADING_TYPE: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_loading_list_item, viewGroup, false);
                return new LoadingViewHolder(view);
            }

            case EXHAUSTED_TYPE: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_search_exhausted, viewGroup, false);
                return new SearchExhaustedViewHolder(view);
            }

            default: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_repository_list_item, viewGroup, false);
                return new RepoViewHolder(view, mOnRepoListener);
            }
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        int itemViewType = getItemViewType(i);
        if (itemViewType == REPOSITORY_TYPE) {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);

            Glide.with(viewHolder.itemView.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(mRepositories.get(i).getAvatar())
                    .into(((RepoViewHolder) viewHolder).image);

            ((RepoViewHolder) viewHolder).title.setText(mRepositories.get(i).getName());
            ((RepoViewHolder) viewHolder).description.setText(mRepositories.get(i).getDescription());
            ((RepoViewHolder) viewHolder).rating.setText(String.valueOf(mRepositories.get(i).getStars()));
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (mRepositories.get(position).getName().equals("LOADING...")) {
            return LOADING_TYPE;
        } else if (mRepositories.get(position).getName().equals("EXHAUSTED...")) {
            return EXHAUSTED_TYPE;
        } else if (position == mRepositories.size() - 1
                && position != 0
                && !mRepositories.get(position).getName().equals("EXHAUSTED...")) {
            return LOADING_TYPE;
        } else {
            return REPOSITORY_TYPE;
        }
    }

    public void setQueryExhausted() {
        hideLoading();
        RepositoryResponse exhaustedRepo = new RepositoryResponse();
        exhaustedRepo.setName("EXHAUSTED...");
        mRepositories.add(exhaustedRepo);
        notifyDataSetChanged();
    }

    private void hideLoading() {
        if (isLoading()) {
            for (RepositoryResponse repo : mRepositories) {
                if (repo.getName().equals("LOADING...")) {
                    mRepositories.remove(repo);
                }
            }
            notifyDataSetChanged();
        }
    }

    public void displayLoading() {
        if (!isLoading()) {
            RepositoryResponse repo = new RepositoryResponse();
            repo.setName("LOADING...");
            List<RepositoryResponse> loadingList = new ArrayList<>();
            loadingList.add(repo);
            mRepositories = loadingList;
            notifyDataSetChanged();
        }
    }

    private boolean isLoading() {
        if (mRepositories != null) {
            if (mRepositories.size() > 0) {
                if (mRepositories.get(mRepositories.size() - 1).getName().equals("LOADING...")) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public int getItemCount() {
        if (mRepositories != null) {
            return mRepositories.size();
        }
        return 0;
    }

    public void setRepositories(List<RepositoryResponse> repositories) {
        mRepositories = repositories;
        notifyDataSetChanged();
    }

    public RepositoryResponse getSelectedRepo(int position) {
        if (mRepositories != null) {
            if (mRepositories.size() > 0) {
                return mRepositories.get(position);
            }
        }
        return null;
    }

    public void filter(String text) {
        List<RepositoryResponse> temp = new ArrayList();
        for (RepositoryResponse d : mRepositories) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (d.getName().contains(text)|| d.getName().startsWith(text)) {
                temp.add(d);
            }
        }
        //update recyclerview
        setRepositories(temp);
    }

}















