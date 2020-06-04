package com.demo.tiktok_likes_new.network;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.Request;

import static com.demo.tiktok_likes_new.util.KeyPass.cbf01;

public class AppStartRequest extends BaseRequest {

    public AppStartRequest() {
        super(cbf01 + "/");
        coreParams.addProperty("method", cbf01);
    }

    public void start(Callback callback) {
        Constants.HTTP_CLIENT.newCall(getRequest()).enqueue(callback);
    }
}
