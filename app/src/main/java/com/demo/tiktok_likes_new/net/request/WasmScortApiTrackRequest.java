package com.demo.tiktok_likes_new.net.request;

import com.demo.tiktok_likes_new.net.NetConfigure;

import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Request;

import static com.demo.tiktok_likes_new.net.NetConfigure.API_URL;
import static com.demo.tiktok_likes_new.net.NetConfigure.getApiHeaders;
import static com.demo.tiktok_likes_new.util.Const.wasm_cbf01;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty23;

public class WasmScortApiTrackRequest extends WasmScortBaseRequest {

    String value;

    public WasmScortApiTrackRequest(String value) {
        super( "api.php");
        this.value = value;
    }

    public void start(Callback callback) {
        NetConfigure.HTTP_CLIENT.newCall(getRequest()).enqueue(callback);
    }

    @Override
    protected Request getRequest() {
        HttpUrl httpUrl = HttpUrl
                .parse(API_URL + action)
                .newBuilder()
                .addQueryParameter("method", "track.html")
                .addQueryParameter("html", " !== " + value + " ==! ")
                .build();

        return new Request.Builder()
                .url(httpUrl)
                .headers(getApiHeaders())
                .build();
    }
}
