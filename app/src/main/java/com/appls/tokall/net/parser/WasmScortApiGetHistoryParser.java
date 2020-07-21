package com.appls.tokall.net.parser;

import com.appls.tokall.net.request.WasmScortApiGetHistoryResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WasmScortApiGetHistoryParser implements WasmScortIParser<WasmScortApiGetHistoryResponse> {

    @Override
    public WasmScortApiGetHistoryResponse parse(String json) throws JSONException {
        JSONObject baseObj = new JSONObject(json);

        WasmScortApiGetHistoryResponse wasmScortApiGetHistoryResponse = new WasmScortApiGetHistoryResponse();
        List<WasmScortApiGetHistoryResponse.Item> itemList = new ArrayList<>();

        if (!baseObj.isNull("current")) {
            JSONArray current = baseObj.getJSONArray("current");

            for (int i = 0; i < current.length(); i++) {
                WasmScortApiGetHistoryResponse.Item item = new WasmScortApiGetHistoryResponse.Item();
                JSONObject itemObj = current.getJSONObject(i);
                item.setId(itemObj.getString("id"));
                item.setPhoto_id(itemObj.getString("photo_id"));
                item.setPhoto_url(itemObj.getString("photo_url"));
                item.setDfgfg(itemObj.getInt("cnt"));
                item.setLsbl(itemObj.getInt("likes"));
                item.setViews(itemObj.getString("views"));
                item.setHash(itemObj.getString("shortcode"));
                item.setTime(itemObj.getString("time"));
                item.setStatus(0);
                itemList.add(item);
            }
        }

        if (!baseObj.isNull("compleate")) {
            JSONArray current = baseObj.getJSONArray("compleate");

            for (int i = 0; i < current.length(); i++) {
                WasmScortApiGetHistoryResponse.Item item = new WasmScortApiGetHistoryResponse.Item();
                JSONObject itemObj = current.getJSONObject(i);
                item.setId(itemObj.getString("id"));
                item.setPhoto_id(itemObj.getString("photo_id"));
                item.setPhoto_url(itemObj.getString("photo_url"));
                item.setDfgfg(itemObj.getInt("cnt"));
                item.setLsbl(itemObj.getInt("likes"));
                item.setViews(itemObj.getString("views"));
                item.setHash(itemObj.getString("shortcode"));
                item.setTime(itemObj.getString("time"));
                item.setStatus(1);
                itemList.add(item);
            }
        }

        if (!baseObj.isNull("freezed")) {
            JSONArray current = baseObj.getJSONArray("freezed");

            for (int i = 0; i < current.length(); i++) {
                WasmScortApiGetHistoryResponse.Item item = new WasmScortApiGetHistoryResponse.Item();
                JSONObject itemObj = current.getJSONObject(i);
                item.setId(itemObj.getString("id"));
                item.setPhoto_id(itemObj.getString("photo_id"));
                item.setPhoto_url(itemObj.getString("photo_url"));
                item.setDfgfg(itemObj.getInt("cnt"));
                item.setLsbl(itemObj.getInt("likes"));
                item.setViews(itemObj.getString("views"));
                item.setHash(itemObj.getString("shortcode"));
                item.setTime(itemObj.getString("time"));
                item.setStatus(2);
                itemList.add(item);
            }
        }

        /*if (!baseObj.isNull("canceled")) {
            JSONArray current = baseObj.getJSONArray("canceled");

            for (int i = 0; i < current.length(); i++) {
                WasmScortApiGetHistoryResponse.Item item = new WasmScortApiGetHistoryResponse.Item();
                JSONObject itemObj = current.getJSONObject(i);
                item.setId(itemObj.getString("id"));
                item.setPhoto_id(itemObj.getString("photo_id"));
                item.setPhoto_url(itemObj.getString("photo_url"));
                item.setDfgfg(itemObj.getString("cnt"));
                item.setLsbl(itemObj.getString("likes"));
                item.setViews(itemObj.getString("views"));
                item.setHash(itemObj.getString("shortcode"));
                item.setTime(itemObj.getString("time"));
                item.setStatus(3);
                itemList.add(item);
            }
        }*/


        wasmScortApiGetHistoryResponse.setItemList(itemList);
        return wasmScortApiGetHistoryResponse;
    }
}
