package com.demo.tiktok_likes_new.net.parser;

import com.demo.tiktok_likes_new.net.request.WasmScortApiGetVideoResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class WasmScortApiGetVideoParser implements WasmScortIParser<WasmScortApiGetVideoResponse> {

    @Override
    public WasmScortApiGetVideoResponse parse(String json) throws JSONException {
        JSONObject baseObj = new JSONObject(json);

        WasmScortApiGetVideoResponse wasmScortApiGetVideoResponse = new WasmScortApiGetVideoResponse();

        if (!baseObj.isNull("balance")) {
            JSONObject balance = baseObj.getJSONObject("balance");
            wasmScortApiGetVideoResponse.setBalance(balance.getString("balance"));
        }

        if (!baseObj.isNull("item")) {
            JSONObject item = baseObj.getJSONObject("item");
            wasmScortApiGetVideoResponse.setOrder_id(item.getString("order_id"));

            wasmScortApiGetVideoResponse.setItem_id(item.getString("item_id"));
            wasmScortApiGetVideoResponse.setItem_image(item.getString("item_image"));
            wasmScortApiGetVideoResponse.setItem_type(item.getInt("item_type"));
            wasmScortApiGetVideoResponse.setItem_hash(item.getString("item_hash"));
            wasmScortApiGetVideoResponse.setOrderAvailable(true);
        } else {
            wasmScortApiGetVideoResponse.setOrderAvailable(false);
        }

        return wasmScortApiGetVideoResponse;
    }
}
