package com.yahumott.tvapp.network;

import com.yahumott.tvapp.Config;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String API_EXTENSION = "";
    private static final String API_USER_NAME = "admin";
    private static final String API_PASSWORD = "1234";

    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {

        TokenInterceptor interceptor=new TokenInterceptor();
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor).build();
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(new BasicAuthInterceptor(API_USER_NAME, API_PASSWORD)).build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Config.API_SERVER_URL)
                    .addConverterFactory    (GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getRetrofitInstance2(String token) {

        TokenInterceptor interceptor=new TokenInterceptor();
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor).build();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("Authorization","Bearer "+ token).build();
                return chain.proceed(request);
            }
        });
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(new BasicAuthInterceptor(API_USER_NAME, API_PASSWORD)).build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Config.API_SERVER_URL)
                    .addConverterFactory    (GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}
