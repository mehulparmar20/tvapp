package com.yahumott.tvapp.network.api;


import com.yahumott.tvapp.model.SubscriptionStatus;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface SubscriptionStatusApi {
    @GET("check_user_subscription_status")
    Call<SubscriptionStatus> getSubscriptionStatus(@Header("API-KEY") String api_key,
                                                   @Query("user_id") String userId);
}
