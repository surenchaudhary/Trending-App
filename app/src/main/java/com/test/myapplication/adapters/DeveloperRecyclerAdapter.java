package com.test.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.test.myapplication.R;
import com.test.myapplication.rest.responses.DeveloperResponse;
import com.test.myapplication.rest.responses.RepositoryResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Surendra Singh on 2/27/2020.
 * Created for Pitney Bowes
 */
public class DeveloperRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int REPOSITORY_TYPE = 1;
    private static final int LOADING_TYPE = 2;
    private static final int EXHAUSTED_TYPE = 3;

    private List<DeveloperResponse> mDevelopers;
    private OnRepoListener mOnRepoListener;

    public DeveloperRecyclerAdapter(OnRepoListener mOnRepoListener) {
        this.mOnRepoListener = mOnRepoListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = null;
        switch (i) {

            case REPOSITORY_TYPE: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_develoepr_list_item, viewGroup, false);
                return new DeveloperViewHolder(view, mOnRepoListener);
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
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_develoepr_list_item, viewGroup, false);
                return new DeveloperViewHolder(view, mOnRepoListener);
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
                    .load(mDevelopers.get(i).getAvatar())
                    .into(((DeveloperViewHolder) viewHolder).image);

            ((DeveloperViewHolder) viewHolder).name.setText(mDevelopers.get(i).getName());
            ((DeveloperViewHolder) viewHolder).username.setText(mDevelopers.get(i).getUsername());
            ((DeveloperViewHolder) viewHolder).repoName.setText("Repo: "+mDevelopers.get(i).getRepo().getName());
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (mDevelopers.get(position).getName().equals("LOADING...")) {
            return LOADING_TYPE;
        } else if (mDevelopers.get(position).getName().equals("EXHAUSTED...")) {
            return EXHAUSTED_TYPE;
        } else if (position == mDevelopers.size() - 1
                && position != 0
                && !mDevelopers.get(position).getName().equals("EXHAUSTED...")) {
            return LOADING_TYPE;
        } else {
            return REPOSITORY_TYPE;
        }
    }

    public void setQueryExhausted() {
        hideLoading();
        DeveloperResponse exhaustedRepo = new DeveloperResponse();
        exhaustedRepo.setName("EXHAUSTED...");
        mDevelopers.add(exhaustedRepo);
        notifyDataSetChanged();
    }

    private void hideLoading() {
        if (isLoading()) {
            for (DeveloperResponse repo : mDevelopers) {
                if (repo.getName().equals("LOADING...")) {
                    mDevelopers.remove(repo);
                }
            }
            notifyDataSetChanged();
        }
    }

    public void displayLoading() {
        if (!isLoading()) {
            DeveloperResponse repo = new DeveloperResponse();
            repo.setName("LOADING...");
            List<DeveloperResponse> loadingList = new ArrayList<>();
            loadingList.add(repo);
            mDevelopers = loadingList;
            notifyDataSetChanged();
        }
    }

    private boolean isLoading() {
        if (mDevelopers != null) {
            if (mDevelopers.size() > 0) {
                if (mDevelopers.get(mDevelopers.size() - 1).getName().equals("LOADING...")) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public int getItemCount() {
        if (mDevelopers != null) {
            return mDevelopers.size();
        }
        return 0;
    }

    public void setDeveloeprss(List<DeveloperResponse> repositories) {
        mDevelopers = repositories;
        notifyDataSetChanged();
    }

    public DeveloperResponse getSelectedDeveloper(int position) {
        if (mDevelopers != null) {
            if (mDevelopers.size() > 0) {
                return mDevelopers.get(position);
            }
        }
        return null;
    }

    public void filter(String text) {
        List<DeveloperResponse> temp = new ArrayList();
        for (DeveloperResponse d : mDevelopers) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (d.getName().contains(text)) {
                temp.add(d);
            }
        }
        //update recyclerview
        setDeveloeprss(temp);
    }
}















