package com.demo.tiktok_likes_new.net.request;

import com.demo.tiktok_likes_new.BuildConfig;
import com.demo.tiktok_likes_new.util.WasmScortUtilsCr;
import com.google.gson.JsonObject;

import okhttp3.HttpUrl;
import okhttp3.Request;

import static com.demo.tiktok_likes_new.net.NetConfigure.API_URL;
import static com.demo.tiktok_likes_new.net.NetConfigure.getApiHeaders;
import static com.demo.tiktok_likes_new.util.Common.getAndroidId;
import static com.demo.tiktok_likes_new.util.Common.getLocale;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty1;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty23;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty25;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty26;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty4;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty5;
import static com.demo.tiktok_likes_new.util.KeysCr.KEY_CRP;
import static com.demo.tiktok_likes_new.util.KeysCr.PASS_CRP;

public abstract class WasmScortBaseRequest {

    private String action;
    JsonObject wasm_property;

    {
        wasm_property = new JsonObject();
        //wasm_property.addProperty("method", wasm_cbf01);
        wasm_property.addProperty(wasm_tymty1, getAndroidId());
        wasm_property.addProperty(wasm_tymty4, BuildConfig.APPLICATION_ID);
        wasm_property.addProperty(wasm_tymty5, BuildConfig.VERSION_NAME);
        wasm_property.addProperty(wasm_tymty25, "1");
        wasm_property.addProperty(wasm_tymty26, getLocale());
    }

    WasmScortBaseRequest(String action) {
        this.action = action;
    }

    private String getEncodeParams(String params) {
        WasmScortUtilsCr wasmScortUtilsCr = new WasmScortUtilsCr(getAndroidId(), KEY_CRP, PASS_CRP);
        return wasmScortUtilsCr.sdhtEdefvb(params);
    }

    protected Request getRequest() {
        HttpUrl httpUrl = HttpUrl
                .parse(API_URL + action)
                .newBuilder()
                .addQueryParameter(wasm_tymty23, getEncodeParams(wasm_property.toString()))
                .build();

        return new Request.Builder()
                .url(httpUrl)
                .headers(getApiHeaders())
                .build();
    }
}
