package com.yahumott.tvapp.network.api;


import com.yahumott.tvapp.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface SignUpApi {
    @FormUrlEncoded
    @POST("tvregister")
    Call<User> signUp(@Field("email") String email,
                      @Field("password") String password,
                      @Field("name") String name);

}
