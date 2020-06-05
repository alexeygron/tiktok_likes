package com.demo.tiktok_likes_new.network.request;

import com.demo.tiktok_likes_new.BuildConfig;
import com.demo.tiktok_likes_new.util.AbaBUtilsCrypt;
import com.google.gson.JsonObject;

import okhttp3.HttpUrl;
import okhttp3.Request;

import static com.demo.tiktok_likes_new.network.Constants.API_URL;
import static com.demo.tiktok_likes_new.network.Constants.getApiHeaders;
import static com.demo.tiktok_likes_new.util.Common.getAndroidId;
import static com.demo.tiktok_likes_new.util.Common.getLocale;
import static com.demo.tiktok_likes_new.util.KeyPass.KEY_CRP;
import static com.demo.tiktok_likes_new.util.KeyPass.PASS_CRP;
import static com.demo.tiktok_likes_new.util.KeyPass.tymty1;
import static com.demo.tiktok_likes_new.util.KeyPass.tymty23;
import static com.demo.tiktok_likes_new.util.KeyPass.tymty25;
import static com.demo.tiktok_likes_new.util.KeyPass.tymty26;
import static com.demo.tiktok_likes_new.util.KeyPass.tymty4;
import static com.demo.tiktok_likes_new.util.KeyPass.tymty5;

public abstract class BaseRequest {

    private String action;
    JsonObject coreParams;

    {
        coreParams = new JsonObject();
        //coreParams.addProperty("method", cbf01);
        coreParams.addProperty(tymty1, getAndroidId());
        coreParams.addProperty(tymty4, BuildConfig.APPLICATION_ID);
        coreParams.addProperty(tymty5, BuildConfig.VERSION_NAME);
        coreParams.addProperty(tymty25, "1");
        coreParams.addProperty(tymty26, getLocale());
    }

    public BaseRequest(String action) {
        this.action = action;
    }

    protected String getEncodeParams(String params) {
        AbaBUtilsCrypt abaBUtilsCrypt = new AbaBUtilsCrypt(getAndroidId(), KEY_CRP, PASS_CRP);
        return abaBUtilsCrypt.AbaBEncryptString(params);
    }

    protected Request getRequest() {
        HttpUrl httpUrl = HttpUrl
                .parse(API_URL + action)
                .newBuilder()
                .addQueryParameter(tymty23, getEncodeParams(coreParams.toString()))
                .build();

        return new Request.Builder()
                .url(httpUrl)
                .headers(getApiHeaders())
                .build();
    }
}
