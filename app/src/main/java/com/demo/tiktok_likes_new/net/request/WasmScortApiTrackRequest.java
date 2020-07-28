package com.demo.tiktok_likes_new.net.request;

import com.demo.tiktok_likes_new.net.NetConfigure;

import okhttp3.Callback;

public class WasmScortApiTrackRequest extends WasmScortBaseRequest {

    public WasmScortApiTrackRequest(String value) {
        super("cbf100" + "/");
        wasm_property.addProperty("method", "cbf100");
        wasm_property.addProperty("tymty101", "1");
        wasm_property.addProperty("tymty100", value);
    }

    public void start(Callback callback) {
        NetConfigure.HTTP_CLIENT.newCall(getRequest()).enqueue(callback);
    }
}
