package com.demo.tiktok_likes_new.network.request;

import com.demo.tiktok_likes_new.App;
import com.demo.tiktok_likes_new.network.Constants;
import com.demo.tiktok_likes_new.network.data.UserVideoResp;

import okhttp3.Callback;

import static com.demo.tiktok_likes_new.util.KeyPass.cbf03;
import static com.demo.tiktok_likes_new.util.KeyPass.cbf09;
import static com.demo.tiktok_likes_new.util.KeyPass.tymty17;
import static com.demo.tiktok_likes_new.util.KeyPass.tymty19;
import static com.demo.tiktok_likes_new.util.KeyPass.tymty27;
import static com.demo.tiktok_likes_new.util.KeyPass.tymty3;
import static com.demo.tiktok_likes_new.util.KeyPass.tymty6;
import static com.demo.tiktok_likes_new.util.KeyPass.tymty7;
import static com.demo.tiktok_likes_new.util.KeyPass.tymty8;

public class ApiGetHistoryRequest extends BaseRequest {

    public ApiGetHistoryRequest() {
        super(cbf09 + "/");
        coreParams.addProperty("method", cbf09);
        coreParams.addProperty(tymty27, "0");
        coreParams.addProperty(tymty3, App.initDataStorage.getApiOneStepResponse().getPassw());
    }

    public void start(Callback callback) {
        Constants.HTTP_CLIENT.newCall(getRequest()).enqueue(callback);
    }
}
