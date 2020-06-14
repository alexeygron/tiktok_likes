package com.demo.tiktok_likes_new.network.request;

import android.util.Log;

import com.demo.tiktok_likes_new.network.Constants;

import okhttp3.Callback;

import static com.demo.tiktok_likes_new.util.Common.getAndroidId;
import static com.demo.tiktok_likes_new.util.KeyPass.cbf01;

public class ApiOneStepRequest extends BaseRequest {

    public ApiOneStepRequest() {
        super(cbf01 + "/");
        coreParams.addProperty("method", cbf01);
    }

    public void start(Callback callback) {
        Constants.HTTP_CLIENT.newCall(getRequest()).enqueue(callback);
    }
}
