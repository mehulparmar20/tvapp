package com.yahumott.tvapp.network.api;

import com.yahumott.tvapp.model.FavoriteModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FavoriteApi {

    @POST("add_favorite")
    Call<FavoriteModel> addToFavorite(@Query("user_id") String userId,
                                      @Query("videos_id") String videoId);

    @POST("remove_favorite")
    Call<FavoriteModel> removeFromFavorite(@Query("user_id") String userId,
                                           @Query("videos_id") String videoId);

    @POST("verify_favorite_list")
    Call<FavoriteModel> verifyFavoriteList(@Query("user_id") String userId,
                                           @Query("videos_id") String videoId);
}
