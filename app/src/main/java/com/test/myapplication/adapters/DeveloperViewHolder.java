package com.test.myapplication.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.test.myapplication.R;

/**
 * Created by Surendra Singh on 2/27/2020.
 * Created for Pitney Bowes
 */
public class DeveloperViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView name, username, repoName;
    AppCompatImageView image;
    OnRepoListener onRepoListener;

    public DeveloperViewHolder(@NonNull View itemView, OnRepoListener onRepoListener) {
        super(itemView);

        this.onRepoListener = onRepoListener;

        name = itemView.findViewById(R.id.author_name);
        username = itemView.findViewById(R.id.author_username);
        repoName = itemView.findViewById(R.id.repo_name);
        image = itemView.findViewById(R.id.author_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onRepoListener.onDeveloperClick(getAdapterPosition());
    }
}



