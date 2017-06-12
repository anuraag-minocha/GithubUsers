package com.githubusers.android.viewmodel;

import android.content.Context;
import android.widget.ImageView;

import com.githubusers.android.model.Repo;
import com.githubusers.android.model.User;
import com.squareup.picasso.Picasso;

/**
 * Created by test01 on 10-May-17.
 */

public class RepoViewModel {
    Repo repo;
    Context mContext;

    public RepoViewModel(Context mContext, Repo repo){
        this.mContext = mContext;
        this.repo = repo;
    }

    public Repo getRepo(){
        return repo;
    }
}
