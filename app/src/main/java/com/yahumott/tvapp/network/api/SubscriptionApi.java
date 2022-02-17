package com.yahumott.tvapp.network.api;

import com.yahumott.tvapp.model.ActiveStatus;
import com.yahumott.tvapp.model.SubscriptionHistory;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SubscriptionApi {
    @FormUrlEncoded
    @POST("check_user_subscription_status")
    Call<ActiveStatus> getActiveStatus( @Field("user_id") String userId);

    @GET("subscription_history")
    Call<SubscriptionHistory> getSubscriptionHistory(@Header("API-KEY") String apiKey,
                                                     @Query("user_id") String userId);

    @GET("cancel_subscription")
    Call<ResponseBody> cancelSubscription(@Header("API-KEY") String apiKey,
                                          @Query("user_id") String userId,
                                          @Query("subscription_id") String subscriptionId);

}
