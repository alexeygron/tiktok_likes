package com.appls.tokall.net.request;

import com.appls.tokall.net.NetConfigure;

import okhttp3.Callback;

import static com.appls.tokall.util.Const.wasm_cbf01;

public class WasmScortApiOneStepRequest extends WasmScortBaseRequest {

    public WasmScortApiOneStepRequest() {
        super(wasm_cbf01 + "/");
        wasm_property.addProperty("method", wasm_cbf01);
    }

    public void start(Callback callback) {
        NetConfigure.HTTP_CLIENT.newCall(getRequest()).enqueue(callback);
    }
}
