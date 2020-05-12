package com.example.imagica;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitClient {
    @GET("/collections/{id}/photos")
    Call<List<Photo>> getCollection(@Path("id") String id, @Query("page") Integer page, @Query("per_page") Integer perPage
            , @Query("orientation") String orientation);
}
