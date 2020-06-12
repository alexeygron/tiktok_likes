package com.demo.tiktok_likes_new.network.request;

import com.demo.tiktok_likes_new.ScabApp;
import com.demo.tiktok_likes_new.network.Constants;

import okhttp3.Callback;

import static com.demo.tiktok_likes_new.util.KeyPass.cbf11;
import static com.demo.tiktok_likes_new.util.KeyPass.tymty10;
import static com.demo.tiktok_likes_new.util.KeyPass.tymty27;
import static com.demo.tiktok_likes_new.util.KeyPass.tymty3;

public class ApiRemoveHistoryRequest extends BaseRequest {

    public ApiRemoveHistoryRequest(ApiGetHistoryResponse.Item item) {
        super(cbf11 + "/");
        coreParams.addProperty("method", cbf11);
        coreParams.addProperty(tymty27, "0");
        coreParams.addProperty(tymty10, item.getId());
        coreParams.addProperty(tymty3, ScabApp.initDataStorage.getApiOneStepResponse().getPassw());
    }

    public void start(Callback callback) {
        Constants.HTTP_CLIENT.newCall(getRequest()).enqueue(callback);
    }
}
