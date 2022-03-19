package com.yahumott.tvapp.network.api;

import com.yahumott.tvapp.model.SearchModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SearchApi {

    @POST("search")
    Call<SearchModel> getSearchData(@Query("search") String query);
}
