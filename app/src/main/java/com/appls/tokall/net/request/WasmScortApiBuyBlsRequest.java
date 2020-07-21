package com.appls.tokall.net.request;

import com.appls.tokall.BuildConfig;
import com.appls.tokall.TokkallApp;
import com.appls.tokall.net.NetConfigure;

import okhttp3.Callback;

import static com.appls.tokall.util.Const.wasm_cbf13;
import static com.appls.tokall.util.Const.wasm_tymty20;
import static com.appls.tokall.util.Const.wasm_tymty21;
import static com.appls.tokall.util.Const.wasm_tymty22;
import static com.appls.tokall.util.Const.wasm_tymty27;
import static com.appls.tokall.util.Const.wasm_tymty3;

public class WasmScortApiBuyBlsRequest extends WasmScortBaseRequest {

    public WasmScortApiBuyBlsRequest(String token, String id) {
        super(wasm_cbf13 + "/");
        wasm_property.addProperty("method", wasm_cbf13);
        wasm_property.addProperty(wasm_tymty27, "0");
        wasm_property.addProperty(wasm_tymty3, TokkallApp.wasm_storage.getApiOneStepResponse().getPassw());

        wasm_property.addProperty(wasm_tymty20, id);
        wasm_property.addProperty(wasm_tymty21, token);
        wasm_property.addProperty(wasm_tymty22, BuildConfig.APPLICATION_ID);
    }

    public void start(Callback callback) {
        NetConfigure.HTTP_CLIENT.newCall(getRequest()).enqueue(callback);
    }
}
