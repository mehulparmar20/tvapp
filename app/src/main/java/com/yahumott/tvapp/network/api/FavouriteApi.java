package com.yahumott.tvapp.network.api;


import com.yahumott.tvapp.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FavouriteApi {

    @POST("favoritelist")
    Call<List<Movie>> getFavoriteList(@Query("user_id") String userId);

}
