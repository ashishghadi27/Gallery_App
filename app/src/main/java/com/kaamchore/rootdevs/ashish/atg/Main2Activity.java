package com.kaamchore.rootdevs.ashish.atg;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main2Activity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private Navi_drawer navi_drawer;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageAdapter adapter;
    private EditText editText;
    private android.support.v7.app.ActionBar toolbar;
    List<Photo> photos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        navigationView = findViewById(R.id.nav_view);
        mDrawerLayout = findViewById(R.id.navi);
        navi_drawer = new Navi_drawer();
        navi_drawer.nav(mDrawerLayout, navigationView);

        swipeRefreshLayout = findViewById(R.id.swipe);
        toolbar = getSupportActionBar();
        toolbar.setTitle("Search");
        editText = (EditText) findViewById(R.id.searchfield);

        initViews();

        editText.setOnEditorActionListener(new EditText.OnEditorActionListener() {


            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;


                if (actionId == EditorInfo.IME_ACTION_GO && (!editText.getText().toString().equals(""))) {
                    SharedPreferences sharedPreferences = getSharedPreferences("searchkey",MODE_PRIVATE);
                    final SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("word", editText.getText().toString());
                    editor.apply();
                    if(isNetworkAvailable())
                        loadJSON();
                    else Toast.makeText(Main2Activity.this,"Check Connection", Toast.LENGTH_SHORT).show();
                    handled = true;
                }
                return handled;
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                photos.clear();
                if(isNetworkAvailable())
                    loadJSON();
                else Toast.makeText(Main2Activity.this,"Check Connection", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    private void initViews(){
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 4);
        recyclerView.setLayoutManager(layoutManager);

    }
    private void loadJSON(){
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading");
        dialog.show();
        SharedPreferences sharedPreferences = getSharedPreferences("searchkey",MODE_PRIVATE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.flickr.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<Imagelist> call = request.getSearch(sharedPreferences.getString("word", ""));
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
    public void drawerclick(View view){
        mDrawerLayout.openDrawer(Gravity.LEFT);

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
