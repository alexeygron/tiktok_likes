package com.appls.tokall.net.request;

import com.appls.tokall.net.NetConfigure;

import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Request;

import static com.appls.tokall.net.NetConfigure.REQ_URL;

public class WasmScortLoadTrendingRequest {

    public void start(String cookies, Callback callback) {

        HttpUrl httpUrl = HttpUrl
                .parse(REQ_URL + "foryou?lang=en")
                .newBuilder()
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .addHeader("cookie", cookies)
                .build();

        NetConfigure.HTTP_CLIENT.newCall(request).enqueue(callback);
    }
}
