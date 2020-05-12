package com.example.imagica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    /*  private RecyclerView recyclerView;
      private RecyclerViewAdapter adapter;
      private EndlessRecyclerViewScrollListener scrollListener;
      private int page = 1;
      RetrofitClient retrofitClient;
      Retrofit retrofit;
      private static final int LIMIT = 1000;*/
    private String ID = "1580860";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        tabLayout.addTab(tabLayout.newTab().setText("Vintage Decor"));
        tabLayout.addTab(tabLayout.newTab().setText("Felis Catus"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), MainActivity.this, tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(TAG, "onTabSelected: " + tab.getPosition());
                viewPager.setCurrentItem(tab.getPosition());
               /* switch (tab.getPosition()) {
                    case 0:
                        ID = "1580860";

                        break;
                    case 1:
                        ID = "139386";
                        break;
                    default:
                        break;

                }*/
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

  /*      adapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        scrollListener = new EndlessRecyclerViewScrollListener(new GridLayoutManager(MainActivity.this, 2)) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                setupRetrofit();
            }
        };


        setupRetrofit();*/
    }

/*    private void setupRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new AuthenticationInterceptor()).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(AceessKey.BASE_URL_UNSPLASH)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitClient = retrofit.create(RetrofitClient.class);
        Log.d(TAG, "stupRetroFit: " + ID);
        Call<List<Photo>> getPhotos = retrofitClient.getCollection(ID, page, LIMIT);
        //Log.d(TAG, "stupRetroFit: ");
        getPhotos.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                //Log.d(TAG, "onResponse: " + response.body().get(1).getCurrent_user_collections().getTitle());
                List<Photo> photos = response.body();
                //    Log.d(TAG, "onResponse:   " + photos.size());
                page++;
                adapter.addPhotos(photos);


                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });


    }*/

    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.pager);
        //   recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }
}
