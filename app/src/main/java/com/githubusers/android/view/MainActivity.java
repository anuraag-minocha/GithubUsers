package com.githubusers.android.view;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.githubusers.android.R;
import com.githubusers.android.api.ApiClient;
import com.githubusers.android.api.ApiInterface;
import com.githubusers.android.database.DatabaseHelper;
import com.githubusers.android.databinding.ActivityMainBinding;
import com.githubusers.android.model.User;
import com.githubusers.android.viewmodel.UserViewModel;
import com.google.gson.Gson;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    UserRecyclerAdapter adapter;
    private DatabaseHelper databaseHelper = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        binding.recyclerView.setLayoutManager(layoutManager);
        adapter  = new UserRecyclerAdapter(this);
        binding.recyclerView.setAdapter(adapter);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);


        final RuntimeExceptionDao<User, Integer> simpleDao = getHelper().getSimpleDataDao();
        // query for all of the data objects in the database
        List<User> list = simpleDao.queryForAll();

        if(list.isEmpty()) {
            progressDialog.show();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);


            Call<ArrayList<User>> call = apiService.getUsers();
            call.enqueue(new Callback<ArrayList<User>>() {
                @Override
                public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                    adapter.setItems(response.body());
                    progressDialog.dismiss();
                    for(int i=0; i<response.body().size(); i++){
                        simpleDao.create(response.body().get(i));
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                    // Log error here since request failed
                    progressDialog.dismiss();
                }
            });
        }
        else{
                adapter.setItems(new ArrayList<User>(list));
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    public DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper =
                    OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }
}
