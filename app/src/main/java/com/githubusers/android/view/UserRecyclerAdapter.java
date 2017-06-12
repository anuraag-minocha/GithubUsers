package com.githubusers.android.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.githubusers.android.R;
import com.githubusers.android.databinding.ItemUserBinding;
import com.githubusers.android.model.User;
import com.githubusers.android.viewmodel.UserViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by test01 on 10-May-17.
 */

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.BindingHolder> {
    private ArrayList<User> itemsList;
    private Context mContext;

    public UserRecyclerAdapter(Context context) {
        mContext = context;
        itemsList = new ArrayList<>();
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemUserBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_user,
                parent,
                false);
        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        final ItemUserBinding userBinding = holder.binding;
        userBinding.setUvm(new UserViewModel(mContext, itemsList.get(position)));
        userBinding.getUvm().setImage(userBinding.imageView,userBinding.getUvm().getUser().getAvatarUrl());
        userBinding.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext,UserDetailsActivity.class);
                i.putExtra("user",userBinding.getUvm().getUser());
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public void setItems(ArrayList<User> items) {
        itemsList = items;
        notifyDataSetChanged();
    }


    public static class BindingHolder extends RecyclerView.ViewHolder {
        private ItemUserBinding binding;

        public BindingHolder(ItemUserBinding binding) {
            super(binding.cardView);
            this.binding = binding;
        }
    }

}
