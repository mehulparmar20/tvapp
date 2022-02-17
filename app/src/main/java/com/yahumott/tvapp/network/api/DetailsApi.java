package com.yahumott.tvapp.network.api;

import com.yahumott.tvapp.model.MovieSingleDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface DetailsApi {

    @GET("movie_detail")
    Call<MovieSingleDetails> getSingleDetail(@Header("API-KEY") String apiKey,
                                             @Query("type") String videoType,
                                             @Query("id") String videoId);
}
