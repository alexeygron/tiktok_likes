package com.demo.tiktok_likes_new.net.request;

import com.demo.tiktok_likes_new.net.NetConfigure;

import okhttp3.Callback;

import static com.demo.tiktok_likes_new.util.Const.wasm_cbf01;

public class WasmScortApiOneStepRequest extends WasmScortBaseRequest {

    public WasmScortApiOneStepRequest() {
        super(wasm_cbf01 + "/");
        wasm_property.addProperty("method", wasm_cbf01);
    }

    public void start(Callback callback) {
        NetConfigure.HTTP_CLIENT.newCall(getRequest()).enqueue(callback);
    }
}
