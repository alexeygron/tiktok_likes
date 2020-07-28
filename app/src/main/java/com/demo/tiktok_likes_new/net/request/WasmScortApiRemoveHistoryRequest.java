package com.demo.tiktok_likes_new.net.request;

import com.demo.tiktok_likes_new.WaspApp;
import com.demo.tiktok_likes_new.net.NetConfigure;

import okhttp3.Callback;

import static com.demo.tiktok_likes_new.util.Const.wasm_cbf11;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty10;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty27;
import static com.demo.tiktok_likes_new.util.Const.wasm_tymty3;

public class WasmScortApiRemoveHistoryRequest extends WasmScortBaseRequest {

    public WasmScortApiRemoveHistoryRequest(WasmScortApiGetHistoryResponse.Item item) {
        super(wasm_cbf11 + "/");
        wasm_property.addProperty("method", wasm_cbf11);
        wasm_property.addProperty(wasm_tymty27, "0");
        wasm_property.addProperty(wasm_tymty10, item.getId());
        wasm_property.addProperty(wasm_tymty3, WaspApp.wasm_storage.getApiOneStepResponse().getPassw());
    }

    public void start(Callback callback) {
        NetConfigure.HTTP_CLIENT.newCall(getRequest()).enqueue(callback);
    }
}
