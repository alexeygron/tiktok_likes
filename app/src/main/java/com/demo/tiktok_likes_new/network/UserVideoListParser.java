package com.demo.tiktok_likes_new.network;

import com.demo.tiktok_likes_new.network.data.UserVideoResp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserVideoListParser implements IParser<UserVideoResp> {

    @Override
    public UserVideoResp parse(String json) throws JSONException {
        JSONObject baseObj = new JSONObject(json);

        UserVideoResp userVideoResp = new UserVideoResp();
        List<UserVideoResp.Item> itemList = new ArrayList<>();

        if (!baseObj.isNull("items")) {

            JSONArray itemsArr = baseObj.getJSONArray("items");
            userVideoResp.setMaxCursor(baseObj.getString("maxCursor"));
            userVideoResp.setMinCursor(baseObj.getString("minCursor"));
            userVideoResp.setMore(baseObj.getBoolean("hasMore"));

            for (int i = 0; i < itemsArr.length(); i++) {
                UserVideoResp.Item item = new UserVideoResp.Item();

                JSONObject itemObj = (JSONObject) itemsArr.get(i);
                item.setId(itemObj.getString("id"));

                JSONObject videoObj = itemObj.getJSONObject("video");
                item.setPhoto(videoObj.getString("cover"));

                JSONObject statObj = itemObj.getJSONObject("stats");
                item.setLikesCount(statObj.getString("diggCount"));
                itemList.add(item);
            }

            userVideoResp.setItems(itemList);
        }

        return userVideoResp;
    }
}
