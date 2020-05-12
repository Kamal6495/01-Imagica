package com.example.imagica;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Tab1 extends Fragment {
    private static final String TAG = "Tab1";
    private static final int LIMIT = 30;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private int page = 1;
    //  private Context context;
    private RetrofitClient retrofitClient;
    private Retrofit retrofit;
    private String ID = "1580860";

    public Tab1() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        loadImages();
        return view;
    }

    private void loadImages() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter(new ArrayList<Photo>(), getActivity());
        recyclerView.setAdapter(adapter);
        setupRetrofit();
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                setupRetrofit();
            }


        });


    }

    private void setupRetrofit() {

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new AuthenticationInterceptor()).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(AceessKey_Constants.BASE_URL_UNSPLASH)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitClient = retrofit.create(RetrofitClient.class);
        Log.d(TAG, "stupRetroFit: " + ID);
        Call<List<Photo>> getPhotos = retrofitClient.getCollection(ID, page, LIMIT, AceessKey_Constants.IMAGE_ORINTATION);
        //Log.d(TAG, "stupRetroFit: ");
        getPhotos.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                //Log.d(TAG, "onResponse: " + response.body().get(1).getCurrent_user_collections().getTitle());
                List<Photo> photos = response.body();
                {
                    page++;
                    adapter.addPhotos(photos);
                    recyclerView.setAdapter(adapter);

                }

            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });


    }
}
