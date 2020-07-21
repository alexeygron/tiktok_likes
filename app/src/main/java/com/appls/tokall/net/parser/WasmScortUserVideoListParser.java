package com.appls.tokall.net.parser;

import com.appls.tokall.net.request.WasmScortUserVideoResp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class WasmScortUserVideoListParser implements WasmScortIParser<WasmScortUserVideoResp> {

    @Override
    public WasmScortUserVideoResp parse(String json) throws JSONException {
        JSONObject baseObj = new JSONObject(json);

        WasmScortUserVideoResp wasm_wasmScortUserVideoResp = new WasmScortUserVideoResp();
        List<WasmScortUserVideoResp.Item> itemList = new ArrayList<>();

        if (!baseObj.isNull("items")) {

            JSONArray itemsArr = baseObj.getJSONArray("items");
            wasm_wasmScortUserVideoResp.setMaxCursor(baseObj.getString("maxCursor"));
            wasm_wasmScortUserVideoResp.setMinCursor(baseObj.getString("minCursor"));
            wasm_wasmScortUserVideoResp.setMore(baseObj.getBoolean("hasMore"));

            for (int i = 0; i < itemsArr.length(); i++) {
                WasmScortUserVideoResp.Item item = new WasmScortUserVideoResp.Item();

                JSONObject itemObj = (JSONObject) itemsArr.get(i);
                item.setId(itemObj.getString("id"));

                JSONObject videoObj = itemObj.getJSONObject("video");


                item.setPhotoAnim(videoObj.getString("dynamicCover"));
                 item.setPhoto(videoObj.getString("cover"));

                JSONObject statObj = itemObj.getJSONObject("stats");
                item.setLikesCount(statObj.getString("diggCount"));

                JSONObject author = itemObj.getJSONObject("author");
                item.setUniqueId(author.getString("uniqueId"));

                itemList.add(item);
            }

            wasm_wasmScortUserVideoResp.setItems(itemList);
        }

        return wasm_wasmScortUserVideoResp;
    }
}
