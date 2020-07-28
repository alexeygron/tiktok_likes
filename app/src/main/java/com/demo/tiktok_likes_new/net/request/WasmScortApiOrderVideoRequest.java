package com.demo.tiktok_likes_new.net.request;

import com.demo.tiktok_likes_new.WaspApp;
import com.demo.tiktok_likes_new.net.NetConfigure;

import okhttp3.Callback;

import static com.demo.tiktok_likes_new.util.Const.wasm_cbf03;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty17;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty19;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty27;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty3;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty6;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty7;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty8;

public class WasmScortApiOrderVideoRequest extends WasmScortBaseRequest {

    public WasmScortApiOrderVideoRequest(WasmScortUserVideoResp.Item item, int pos) {
        super(wasm_cbf03 + "/");
        wasm_property.addProperty("method", wasm_cbf03);
        wasm_property.addProperty(wasm_tymty27, "0");
        wasm_property.addProperty(wasm_tymty3, WaspApp.wasm_storage.getApiOneStepResponse().getPassw());
        wasm_property.addProperty(wasm_tymty7, item.getId());
        wasm_property.addProperty(wasm_tymty17, item.getPhoto());
        wasm_property.addProperty(wasm_tymty6, String.valueOf(pos));
        wasm_property.addProperty(wasm_tymty19, item.getUniqueId());
        wasm_property.addProperty(wasm_tymty8, "all");
    }

    public void start(Callback callback) {
        NetConfigure.HTTP_CLIENT.newCall(getRequest()).enqueue(callback);
    }
}
