package com.demo.tiktok_likes_new.net.request;

import com.demo.tiktok_likes_new.BuildConfig;
import com.demo.tiktok_likes_new.WaspApp;
import com.demo.tiktok_likes_new.net.NetConfigure;

import okhttp3.Callback;

import static com.demo.tiktok_likes_new.util.Common.getAndroidId;
import static com.demo.tiktok_likes_new.util.Const.wasm_cbf03;
import static com.demo.tiktok_likes_new.util.Const.wasm_cbf13;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty1;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty17;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty19;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty20;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty21;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty22;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty27;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty3;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty6;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty7;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty8;

public class WasmScortApiBuyBlsRequest extends WasmScortBaseRequest {

    public WasmScortApiBuyBlsRequest(String token, String id) {
        super(wasm_cbf13 + "/");
        wasm_property.addProperty("method", wasm_cbf13);
        wasm_property.addProperty(wasm_tymty27, "0");
        wasm_property.addProperty(wasm_tymty3, WaspApp.wasm_storage.getApiOneStepResponse().getPassw());

        wasm_property.addProperty(wasm_tymty20, id);
        wasm_property.addProperty(wasm_tymty21, token);
        wasm_property.addProperty(wasm_tymty22, BuildConfig.APPLICATION_ID);
    }

    public void start(Callback callback) {
        NetConfigure.HTTP_CLIENT.newCall(getRequest()).enqueue(callback);
    }
}
