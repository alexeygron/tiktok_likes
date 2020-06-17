package com.demo.tiktok_likes_new.net.parser;

import com.demo.tiktok_likes_new.net.request.WasmScortApiOneStepResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class WasmScortStartAppParser implements WasmScortIParser<WasmScortApiOneStepResponse> {

    @Override
    public WasmScortApiOneStepResponse parse(String json) throws JSONException {
        JSONObject baseObj = new JSONObject(json);

        WasmScortApiOneStepResponse apiOneStepResponse = new WasmScortApiOneStepResponse();

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
                apiOneStepResponse.setUdp_type(update.getInt("update_type"));
                apiOneStepResponse.setUdp_text(update.getString("update_text"));
            }
        }

        return apiOneStepResponse;
    }
}
