package com.appls.tokall.net.request;

import com.appls.tokall.TokkallApp;
import com.appls.tokall.net.NetConfigure;
import com.orhanobut.hawk.Hawk;

import okhttp3.Callback;

import static com.appls.tokall.util.Const.wasm_cbf02;
import static com.appls.tokall.util.Const.wasm_tymty13;
import static com.appls.tokall.util.Const.wasm_tymty16;
import static com.appls.tokall.util.Const.wasm_tymty3;
import static com.appls.tokall.util.KeysCr.uiid;
import static com.appls.tokall.util.KeysCr.uniqueId;


public class WasmScortApiTwoStepRequest extends WasmScortBaseRequest {

    public WasmScortApiTwoStepRequest() {
        super(wasm_cbf02 + "/");
        wasm_property.addProperty("method", wasm_cbf02);
        wasm_property.addProperty(wasm_tymty13, Hawk.get(uniqueId, ""));
        wasm_property.addProperty(wasm_tymty16, Hawk.get(uiid, ""));
        wasm_property.addProperty(wasm_tymty3, TokkallApp.wasm_storage.getApiOneStepResponse().getPassw());
    }

    public void start(Callback callback) {
        NetConfigure.HTTP_CLIENT.newCall(getRequest()).enqueue(callback);
    }
}
