package com.demo.tiktok_likes_new.network.request;

import com.demo.tiktok_likes_new.ScabApp;
import com.demo.tiktok_likes_new.network.Constants;
import com.demo.tiktok_likes_new.network.data.UserVideoResp;

import okhttp3.Callback;

import static com.demo.tiktok_likes_new.util.KeyPass.cbf03;
import static com.demo.tiktok_likes_new.util.KeyPass.tymty17;
import static com.demo.tiktok_likes_new.util.KeyPass.tymty19;
import static com.demo.tiktok_likes_new.util.KeyPass.tymty27;
import static com.demo.tiktok_likes_new.util.KeyPass.tymty3;
import static com.demo.tiktok_likes_new.util.KeyPass.tymty6;
import static com.demo.tiktok_likes_new.util.KeyPass.tymty7;
import static com.demo.tiktok_likes_new.util.KeyPass.tymty8;

public class ApiOrderVideoRequest extends BaseRequest {

    public ApiOrderVideoRequest(UserVideoResp.Item item, int pos) {
        super(cbf03 + "/");
        coreParams.addProperty("method", cbf03);
        coreParams.addProperty(tymty27, "0");
        coreParams.addProperty(tymty3, ScabApp.initDataStorage.getApiOneStepResponse().getPassw());
        coreParams.addProperty(tymty7, item.getId());
        coreParams.addProperty(tymty17, item.getPhoto());
        coreParams.addProperty(tymty6, String.valueOf(pos));
        coreParams.addProperty(tymty19, item.getUniqueId());
        coreParams.addProperty(tymty8, "all");
    }

    public void start(Callback callback) {
        Constants.HTTP_CLIENT.newCall(getRequest()).enqueue(callback);
    }
}
