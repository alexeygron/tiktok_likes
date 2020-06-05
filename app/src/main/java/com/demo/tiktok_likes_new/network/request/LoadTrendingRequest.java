package com.demo.tiktok_likes_new.network.request;

import com.demo.tiktok_likes_new.network.Constants;

import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Request;

import static com.demo.tiktok_likes_new.network.Constants.API_URL;
import static com.demo.tiktok_likes_new.network.Constants.REQ_URL;

public class LoadTrendingRequest {

    public void start(String cookies, Callback callback) {

        HttpUrl httpUrl = HttpUrl
                .parse(REQ_URL + "trending")
                .newBuilder()
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .addHeader("cookie", cookies)
                .build();

        Constants.HTTP_CLIENT.newCall(request).enqueue(callback);
    }
}
