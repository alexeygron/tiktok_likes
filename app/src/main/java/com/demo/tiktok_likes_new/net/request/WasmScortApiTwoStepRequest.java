package com.demo.tiktok_likes_new.net.request;

import com.demo.tiktok_likes_new.WaspApp;
import com.demo.tiktok_likes_new.net.NetConfigure;
import com.orhanobut.hawk.Hawk;

import okhttp3.Callback;

import static com.demo.tiktok_likes_new.util.Const.wasm_cbf02;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty13;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty16;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty3;
import static com.demo.tiktok_likes_new.util.KeysCr.uiid;
import static com.demo.tiktok_likes_new.util.KeysCr.uniqueId;


public class WasmScortApiTwoStepRequest extends WasmScortBaseRequest {

    public WasmScortApiTwoStepRequest() {
        super(wasm_cbf02 + "/");
        wasm_property.addProperty("method", wasm_cbf02);
        wasm_property.addProperty(wasm_tymty13, Hawk.get(uniqueId, ""));
        wasm_property.addProperty(wasm_tymty16, Hawk.get(uiid, ""));
        wasm_property.addProperty(wasm_tymty3, WaspApp.wasm_storage.getApiOneStepResponse().getPassw());
    }

    public void start(Callback callback) {
        NetConfigure.HTTP_CLIENT.newCall(getRequest()).enqueue(callback);
    }
}
