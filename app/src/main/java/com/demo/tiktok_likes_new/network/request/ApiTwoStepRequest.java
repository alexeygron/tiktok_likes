package com.demo.tiktok_likes_new.network.request;

import com.demo.tiktok_likes_new.App;
import com.demo.tiktok_likes_new.network.Constants;
import com.orhanobut.hawk.Hawk;

import okhttp3.Callback;

import static com.demo.tiktok_likes_new.util.KeyPass.cbf02;
import static com.demo.tiktok_likes_new.util.KeyPass.tymty13;
import static com.demo.tiktok_likes_new.util.KeyPass.tymty16;
import static com.demo.tiktok_likes_new.util.KeyPass.tymty3;
import static com.demo.tiktok_likes_new.util.KeyPass.uiid;
import static com.demo.tiktok_likes_new.util.KeyPass.uniqueId;

public class ApiTwoStepRequest extends BaseRequest {

    public ApiTwoStepRequest() {
        super(cbf02 + "/");
        coreParams.addProperty("method", cbf02);
        coreParams.addProperty(tymty13, Hawk.get(uniqueId, ""));
        coreParams.addProperty(tymty16, Hawk.get(uiid, ""));
        coreParams.addProperty(tymty3, App.initDataStorage.getApiOneStepResponse().getPassw());
    }

    public void start(Callback callback) {
        Constants.HTTP_CLIENT.newCall(getRequest()).enqueue(callback);
    }
}
