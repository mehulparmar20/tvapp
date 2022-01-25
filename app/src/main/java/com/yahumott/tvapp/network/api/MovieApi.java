package com.yahumott.tvapp.network.api;


import com.yahumott.tvapp.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface MovieApi {


    @GET("movie")
    Call<MovieList> getMovies();

    @GET("content_by_genre_id")
    Call<List<Movie>> getMovieByGenre(@Header("API-KEY") String apiKey,
                                      @Query("id") String id,
                                      @Query("page") int page_num);


    @GET("content_by_country_id")
    Call<List<Movie>> getMovieByCountry(@Header("API-KEY") String apiKey,
                                        @Query("id") String id,
                                        @Query("page") int page_number);

}