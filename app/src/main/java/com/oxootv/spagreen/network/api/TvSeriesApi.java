package com.oxootv.spagreen.network.api;


import com.oxootv.spagreen.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface TvSeriesApi {

    @GET("tvseries")
    Call<MovieList> getTvSeries();

}
