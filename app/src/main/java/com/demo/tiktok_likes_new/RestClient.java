package com.demo.tiktok_likes_new;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class RestClient {


    public static final String TIKTOK_URL = "https://www.tiktok.com/";

    public final OkHttpClient client;
    private static volatile RestClient instance;

    private static final int TIMEOUT = 3;

    public RestClient() {
        client = new OkHttpClient().newBuilder()
                .connectTimeout(TIMEOUT, TimeUnit.MINUTES)
                .readTimeout(TIMEOUT, TimeUnit.MINUTES)
                .addInterceptor(new HttpLoggingInterceptor(message -> Log.d("NETWORK_LOG", message)).setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    public static RestClient getInstance() {
        RestClient NewInstance = instance;
        if (NewInstance == null) {
            synchronized (RestClient.class) {
                NewInstance = instance;
                if (NewInstance == null) {
                    instance = NewInstance = new RestClient();
                }
            }
        }
        return NewInstance;
    }
}
