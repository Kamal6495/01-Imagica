package com.example.imagica;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
    private int page = 1;
    private RetrofitClient retrofitClient;
    private Retrofit retrofit;
    private String ID = "1580860";
    private boolean isLoading = false;
    private List<Photo> photos = new ArrayList<>();
    private int PhotosSize = 0;
    private List<Photo> temp = new ArrayList<>();

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

    //Parsing Json Objects
    private void setupRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new AuthenticationInterceptor()).build();
        retrofit = new Retrofit.Builder()
                .baseUrl(AceessKey_Constants.BASE_URL_UNSPLASH)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitClient = retrofit.create(RetrofitClient.class);
        Call<List<Photo>> getPhotos = retrofitClient.getCollection(ID, page, LIMIT, AceessKey_Constants.IMAGE_ORINTATION);

        getPhotos.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                photos = response.body();
                PhotosSize += photos.size();
                page++;
                adapter.addPhotos(photos);
                adapter.notifyDataSetChanged();
                // Don't use setAdapter(adapter) Not Used So as it changes data from start
                // recyclerView.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                Toast.makeText(getActivity(), "Image Loading Failed Try After Some time ", Toast.LENGTH_SHORT).show();
            }
        });


    }

}
