package com.demo.tiktok_likes_new.network.parser;

import com.demo.tiktok_likes_new.network.request.ApiOneStepResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class StartAppParser implements IParser<ApiOneStepResponse> {

    @Override
    public ApiOneStepResponse parse(String json) throws JSONException {
        JSONObject baseObj = new JSONObject(json);

        ApiOneStepResponse apiOneStepResponse = new ApiOneStepResponse();

        if (!baseObj.isNull("response")) {
            JSONObject response = baseObj.getJSONObject("response");
            apiOneStepResponse.setPassw(response.getString("android_pass"));

            JSONObject balance = response.getJSONObject("balance");
            apiOneStepResponse.setBalance_lfs(balance.getString("likes"));
            apiOneStepResponse.setBalance_fsf(balance.getString("followers"));

            JSONObject regards = response.getJSONObject("regards");
            apiOneStepResponse.setRegards_lfs(regards.getString("likes"));
            apiOneStepResponse.setRegards_fsf(regards.getString("followers"));

            JSONObject config = response.getJSONObject("config");
            apiOneStepResponse.setUser_bonuce(config.getString("user_bonuce"));
            apiOneStepResponse.setAuth_type(config.getString("auth_type"));

            if (!config.isNull("update")) {
                JSONObject update = config.getJSONObject("update");
                apiOneStepResponse.setUdp_url(update.getString("update_url"));
                apiOneStepResponse.setUdp_type(update.getString("update_type"));
                apiOneStepResponse.setUdp_text(update.getString("update_text"));
            }
        }

        return apiOneStepResponse;
    }
}
