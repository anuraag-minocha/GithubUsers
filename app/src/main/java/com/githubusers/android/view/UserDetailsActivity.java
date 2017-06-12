package com.githubusers.android.view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.githubusers.android.R;
import com.githubusers.android.api.ApiClient;
import com.githubusers.android.api.ApiInterface;
import com.githubusers.android.databinding.ActivityMainBinding;
import com.githubusers.android.databinding.ActivityUserDetailsBinding;
import com.githubusers.android.model.Repo;
import com.githubusers.android.model.User;
import com.githubusers.android.viewmodel.RepoViewModel;
import com.githubusers.android.viewmodel.UserViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailsActivity extends AppCompatActivity {

    User user;
    RepoRecyclerAdapter adapter;
    boolean isSyncing = false;
    int page=1;
    boolean pageEnd=false;
    ArrayList<Repo> items;
    ActivityUserDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user = (User)getIntent().getSerializableExtra("user");

        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_details);

        Picasso.with(this).load(user.getAvatarUrl()).into(binding.imageView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);
        adapter = new RepoRecyclerAdapter(this);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setNestedScrollingEnabled(false);

        items = new ArrayList<>();

        binding.progressBar.setVisibility(View.VISIBLE);
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ArrayList<Repo>> call = apiService.getUserRepos(user.getLogin(),page);
        call.enqueue(new Callback<ArrayList<Repo>>() {
            @Override
            public void onResponse(Call<ArrayList<Repo>>call, Response<ArrayList<Repo>> response) {
                items.addAll(response.body());
                adapter.setItems(items);
                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ArrayList<Repo>>call, Throwable t) {
                // Log error here since request failed
                binding.progressBar.setVisibility(View.GONE);
            }
        });

        binding.scrollView.setScrollViewListener(new EndlessScrollListener() {
            @Override
            public void onScrollChanged(EndlessScrollView scrollView, int x, int y, int oldx, int oldy) {
                View view = scrollView.getChildAt(scrollView.getChildCount() - 1);
                int distanceToEnd = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));

                // if diff is zero, then the bottom has been reached
                if (distanceToEnd == 0) {

                    // do stuff your load more stuff
                    if (!isSyncing && !pageEnd) {
                        isSyncing = true;
                        binding.progressBar.setVisibility(View.VISIBLE);
                        page++;
                        Call<ArrayList<Repo>> call = apiService.getUserRepos(user.getLogin(), page);
                        call.enqueue(new Callback<ArrayList<Repo>>() {
                            @Override
                            public void onResponse(Call<ArrayList<Repo>> call, Response<ArrayList<Repo>> response) {
                                items.addAll(response.body());
                                adapter.setItems(items);
                                isSyncing = false;
                                binding.progressBar.setVisibility(View.GONE);
                                if(response.body().isEmpty())
                                    pageEnd=true;
                            }

                            @Override
                            public void onFailure(Call<ArrayList<Repo>> call, Throwable t) {
                                // Log error here since request failed
                                isSyncing = false;
                                binding.progressBar.setVisibility(View.GONE);
                            }
                        });
                    }
                }
            }
            });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
