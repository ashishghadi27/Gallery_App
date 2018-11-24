package com.kaamchore.rootdevs.ashish.atg;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageAdapter adapter;
    private android.support.v7.app.ActionBar toolbar;
    List<Photo> photos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = findViewById(R.id.swipe);
        toolbar = getSupportActionBar();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                photos.clear();
                loadJSON();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        initViews();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_shop:
                    toolbar.setTitle("Shop");
                    return true;
                case R.id.navigation_gifts:
                    toolbar.setTitle("My Gifts");
                    return true;

            }
            return false;
        }
    };
    private void initViews(){
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 4);
        recyclerView.setLayoutManager(layoutManager);
        loadJSON();
    }
    private void loadJSON(){
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading");
        dialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.flickr.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
         RequestInterface request = retrofit.create(RequestInterface.class);
        Call<Imagelist> call = request.getJSON();
        call.enqueue(new Callback<Imagelist>(){
            @Override
            public void onResponse(@NonNull Call<Imagelist> call, @NonNull Response<Imagelist> response) {
                Log.e("ISSUCCESS", String.valueOf(response.isSuccessful()));
                if(response.isSuccessful()) {
                    dialog.dismiss();
                    photos = response.body().getPhotos().getPhoto();
                    adapter = new ImageAdapter(photos);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Imagelist> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }
}
