package com.githubusers.android.api;

import com.githubusers.android.model.Repo;
import com.githubusers.android.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by test01 on 10-May-17.
 */

public interface ApiInterface {
    @GET("users")
    Call<ArrayList<User>> getUsers();

    @GET("users/{login}/repos")
    Call<ArrayList<Repo>> getUserRepos(@Path("login") String login, @Query("page") int page);
}
