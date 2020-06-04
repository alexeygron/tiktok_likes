package com.demo.tiktok_likes_new.network;

import com.demo.tiktok_likes_new.network.data.UserVideoResp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StartAppParser implements IParser<AppStartResponse> {

    @Override
    public AppStartResponse parse(String json) throws JSONException {
        JSONObject baseObj = new JSONObject(json);

        AppStartResponse appStartResponse = new AppStartResponse();
        List<UserVideoResp.Item> itemList = new ArrayList<>();

        if (!baseObj.isNull("response")) {
            JSONObject response = baseObj.getJSONObject("response");
            appStartResponse.setPassw(response.getString("android_pass"));

            JSONObject balance = response.getJSONObject("balance");
            appStartResponse.setBalance_lfs(balance.getString("likes"));
            appStartResponse.setBalance_fsf(balance.getString("followers"));

            JSONObject regards = response.getJSONObject("regards");
            appStartResponse.setRegards_lfs(regards.getString("likes"));
            appStartResponse.setRegards_fsf(regards.getString("followers"));

            JSONObject config = response.getJSONObject("config");
            appStartResponse.setUser_bonuce(config.getString("user_bonuce"));
            appStartResponse.setAuth_type(config.getString("auth_type"));

            if (!config.isNull("update")) {
                JSONObject update = config.getJSONObject("update");
                appStartResponse.setUdp_url(update.getString("update_url"));
                appStartResponse.setUdp_type(update.getString("update_type"));
                appStartResponse.setUdp_text(update.getString("update_text"));
            }
        }

        return appStartResponse;
    }
}
