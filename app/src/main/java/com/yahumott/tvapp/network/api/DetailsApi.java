package com.yahumott.tvapp.network.api;

import com.yahumott.tvapp.model.MovieSingleDetails;
import com.yahumott.tvapp.model.SeriesDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface DetailsApi {

    @GET("movie_detail")
    Call<MovieSingleDetails> getSingleDetail(@Query("movie_id") String videoId,
                                             @Query("type") String videoType);

    @GET("series_detail")
    Call<MovieSingleDetails> getSingleTVDetail(@Query("series_id") String videoId,
                                          @Query("type") String videoType);
}
