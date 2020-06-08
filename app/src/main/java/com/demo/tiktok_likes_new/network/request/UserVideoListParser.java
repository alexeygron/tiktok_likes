package com.demo.tiktok_likes_new.network.request;

import com.demo.tiktok_likes_new.network.data.UserVideoResp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.demo.tiktok_likes_new.util.Common.ANIMATE_PREVIEW;


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

                if (ANIMATE_PREVIEW) item.setPhoto(videoObj.getString("dynamicCover"));
                else item.setPhoto(videoObj.getString("cover"));

                JSONObject statObj = itemObj.getJSONObject("stats");
                item.setLikesCount(statObj.getString("diggCount"));

                JSONObject author = itemObj.getJSONObject("author");
                item.setUniqueId(author.getString("uniqueId"));

                itemList.add(item);
            }

            userVideoResp.setItems(itemList);
        }

        return userVideoResp;
    }
}
