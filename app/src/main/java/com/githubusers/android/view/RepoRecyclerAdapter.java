package com.githubusers.android.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.githubusers.android.R;
import com.githubusers.android.databinding.ItemRepoBinding;
import com.githubusers.android.databinding.ItemUserBinding;
import com.githubusers.android.model.Repo;
import com.githubusers.android.model.User;
import com.githubusers.android.viewmodel.RepoViewModel;
import com.githubusers.android.viewmodel.UserViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by test01 on 10-May-17.
 */

public class RepoRecyclerAdapter extends RecyclerView.Adapter<RepoRecyclerAdapter.BindingHolder> {
    private List<Repo> itemsList;
    private Context mContext;

    public RepoRecyclerAdapter(Context context) {
        mContext = context;
        itemsList = new ArrayList<>();
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemRepoBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_repo,
                parent,
                false);
        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        final ItemRepoBinding repoBinding = holder.binding;
        repoBinding.setRvm(new RepoViewModel(mContext, itemsList.get(position)));
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public void setItems(List<Repo> items) {
        itemsList = items;
        notifyDataSetChanged();
    }


    public static class BindingHolder extends RecyclerView.ViewHolder {
        private ItemRepoBinding binding;

        public BindingHolder(ItemRepoBinding binding) {
            super(binding.cardView);
            this.binding = binding;
        }
    }
}