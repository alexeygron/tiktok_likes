package com.demo.tiktok_likes_new.net.parser;

import com.demo.tiktok_likes_new.net.request.WasmScortApiMakeOrResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class WasmScortApiMakeOrParser implements WasmScortIParser<WasmScortApiMakeOrResponse> {

    @Override
    public WasmScortApiMakeOrResponse parse(String json) throws JSONException {
        JSONObject baseObj = new JSONObject(json);

        WasmScortApiMakeOrResponse makeOrResponse = new WasmScortApiMakeOrResponse();

        if (!baseObj.isNull("balance")) {
            JSONObject balance = baseObj.getJSONObject("balance");
            makeOrResponse.setBalance(balance.getString("likes"));
        }


        return makeOrResponse;
    }
}
