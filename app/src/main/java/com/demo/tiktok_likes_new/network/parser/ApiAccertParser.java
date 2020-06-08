package com.demo.tiktok_likes_new.network.parser;

import com.demo.tiktok_likes_new.network.request.ApiGetVideoResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class ApiAccertParser implements IParser<ApiGetVideoResponse> {

    @Override
    public ApiGetVideoResponse parse(String json) throws JSONException {
        JSONObject baseObj = new JSONObject(json);

        ApiGetVideoResponse apiGetVideoResponse = new ApiGetVideoResponse();

        if (!baseObj.isNull("balance")) {
            JSONObject balance = baseObj.getJSONObject("balance");
            apiGetVideoResponse.setBalance(balance.getString("balance"));
        }

        if (!baseObj.isNull("item")) {
            JSONObject item = baseObj.getJSONObject("item");
            apiGetVideoResponse.setOrder_id(item.getString("order_id"));

            apiGetVideoResponse.setItem_id(item.getString("item_id"));
            apiGetVideoResponse.setItem_image(item.getString("item_image"));
            apiGetVideoResponse.setItem_type(item.getInt("item_type"));
            apiGetVideoResponse.setItem_hash(item.getString("item_hash"));
            apiGetVideoResponse.setOrderAvailable(true);
        } else {
            apiGetVideoResponse.setOrderAvailable(false);
        }

        return apiGetVideoResponse;
    }
}
