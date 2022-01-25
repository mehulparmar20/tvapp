package com.yahumott.tvapp.network.api;


import retrofit2.Call;
import retrofit2.http.GET;

public interface TvSeriesApi {

    @GET("tvseries")
    Call<MovieList> getTvSeries();

}
