package com.githubusers.android.viewmodel;

import android.content.Context;
import android.widget.ImageView;

import com.githubusers.android.model.User;
import com.squareup.picasso.Picasso;

/**
 * Created by test01 on 10-May-17.
 */

public class UserViewModel {
    User user;
    Context mContext;

    public UserViewModel(Context mContext, User user){
        this.mContext = mContext;
        this.user = user;
    }

    public User getUser(){
        return user;
    }

    public void setImage(ImageView imageView, String url) {
        Picasso.with(imageView.getContext()).load(url).into(imageView);
    }
}
