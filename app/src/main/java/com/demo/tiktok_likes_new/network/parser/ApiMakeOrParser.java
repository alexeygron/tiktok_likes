package com.demo.tiktok_likes_new.network.parser;

import com.demo.tiktok_likes_new.network.request.ApiGetVideoResponse;
import com.demo.tiktok_likes_new.network.request.ApiMakeOrResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class ApiMakeOrParser implements IParser<ApiMakeOrResponse> {

    @Override
    public ApiMakeOrResponse parse(String json) throws JSONException {
        JSONObject baseObj = new JSONObject(json);

        ApiMakeOrResponse makeOrResponse = new ApiMakeOrResponse();

        if (!baseObj.isNull("balance")) {
            JSONObject balance = baseObj.getJSONObject("balance");
            makeOrResponse.setBalance(balance.getString("likes"));
        }


        return makeOrResponse;
    }
}
