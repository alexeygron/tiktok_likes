package com.demo.tiktok_likes_new.network.request;

import com.demo.tiktok_likes_new.ScabApp;
import com.demo.tiktok_likes_new.network.Constants;

import okhttp3.Callback;

import static com.demo.tiktok_likes_new.util.KeyPass.cbf07;
import static com.demo.tiktok_likes_new.util.KeyPass.tymty10;
import static com.demo.tiktok_likes_new.util.KeyPass.tymty18;
import static com.demo.tiktok_likes_new.util.KeyPass.tymty27;
import static com.demo.tiktok_likes_new.util.KeyPass.tymty3;
import static com.demo.tiktok_likes_new.util.KeyPass.tymty7;
import static com.demo.tiktok_likes_new.util.KeyPass.tymty9;

public class ApiAccertRequest extends BaseRequest {

    public ApiAccertRequest(ApiGetVideoResponse videoItem, String pass, String meta) {
        super(cbf07 + "/");
        coreParams.addProperty("method", cbf07);
        coreParams.addProperty(tymty7, videoItem.getItem_id());
        coreParams.addProperty(tymty18, pass);
        coreParams.addProperty(tymty10, videoItem.getOrder_id());
        coreParams.addProperty(tymty9, meta);
        coreParams.addProperty(tymty27, "0");
        coreParams.addProperty(tymty3, ScabApp.initDataStorage.getApiOneStepResponse().getPassw());
    }

    public void start(Callback callback) {
        Constants.HTTP_CLIENT.newCall(getRequest()).enqueue(callback);
    }
}
