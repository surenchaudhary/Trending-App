package com.test.myapplication.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.test.myapplication.R;


public class RepoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView title, description, rating;
    AppCompatImageView image;
    OnRepoListener onRepoListener;

    public RepoViewHolder(@NonNull View itemView, OnRepoListener onRepoListener) {
        super(itemView);

        this.onRepoListener = onRepoListener;

        title = itemView.findViewById(R.id.repo_title);
        description = itemView.findViewById(R.id.repo_desc);
        rating = itemView.findViewById(R.id.repo_rating);
        image = itemView.findViewById(R.id.author_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onRepoListener.onRepoClick(getAdapterPosition());
    }
}





