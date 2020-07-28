package com.demo.tiktok_likes_new.net.request;

import com.demo.tiktok_likes_new.WaspApp;
import com.demo.tiktok_likes_new.net.NetConfigure;

import okhttp3.Callback;

import static com.demo.tiktok_likes_new.util.Const.wasm_cbf07;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty10;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty18;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty27;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty3;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty7;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty9;

public class WasmScortAccertRequest extends WasmScortBaseRequest {

    public WasmScortAccertRequest(WasmScortApiGetVideoResponse videoItem, String pass, String meta) {
        super(wasm_cbf07 + "/");
        wasm_property.addProperty("method", wasm_cbf07);
        wasm_property.addProperty(wasm_tymty7, videoItem.getItem_id());
        wasm_property.addProperty(wasm_tymty18, pass);
        wasm_property.addProperty(wasm_tymty10, videoItem.getOrder_id());
        wasm_property.addProperty(wasm_tymty9, meta);
        wasm_property.addProperty(wasm_tymty27, "0");
        wasm_property.addProperty(wasm_tymty3, WaspApp.wasm_storage.getApiOneStepResponse().getPassw());
    }

    public void start(Callback callback) {
        NetConfigure.HTTP_CLIENT.newCall(getRequest()).enqueue(callback);
    }
}
