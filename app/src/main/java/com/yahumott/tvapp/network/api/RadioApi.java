package com.yahumott.tvapp.network.api;


import com.yahumott.tvapp.model.RadioCategory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RadioApi {

    @GET("get_all_radio_by_category")
    Call<List<RadioCategory>> getAllRadioByCategory(@Query("api_secret_key") String key);
}
