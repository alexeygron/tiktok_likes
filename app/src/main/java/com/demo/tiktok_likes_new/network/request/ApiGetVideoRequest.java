package com.demo.tiktok_likes_new.network.request;

import com.demo.tiktok_likes_new.ScabApp;
import com.demo.tiktok_likes_new.network.Constants;

import okhttp3.Callback;

import static com.demo.tiktok_likes_new.util.KeyPass.cbf05;
import static com.demo.tiktok_likes_new.util.KeyPass.tymty27;
import static com.demo.tiktok_likes_new.util.KeyPass.tymty3;

public class ApiGetVideoRequest extends BaseRequest {

    public ApiGetVideoRequest() {
        super(cbf05 + "/");
        coreParams.addProperty("method", cbf05);
        //coreParams.addProperty(tymty13, Hawk.get(uniqueId, ""));
        //coreParams.addProperty(tymty16, Hawk.get(uiid, ""));
        coreParams.addProperty(tymty27, "0");
        coreParams.addProperty(tymty3, ScabApp.initDataStorage.getApiOneStepResponse().getPassw());
    }

    public void start(Callback callback) {
        Constants.HTTP_CLIENT.newCall(getRequest()).enqueue(callback);
    }
}
