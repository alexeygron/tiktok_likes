package com.appls.tokall.net.request;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PositionalDataSource;

import com.appls.tokall.net.NetConfigure;
import com.appls.tokall.net.parser.WasmScortUserVideoListParser;
import com.orhanobut.hawk.Hawk;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Request;

import static com.appls.tokall.net.NetConfigure.REQ_URL;
import static com.appls.tokall.util.KeysCr.uiid;


public class WasmScortUserVideosRequest {

    public LiveData<WasmScortUserVideoResp> loadUserVideos(PositionalDataSource.LoadInitialCallback<WasmScortUserVideoResp.Item> callback, PositionalDataSource.LoadRangeCallback<WasmScortUserVideoResp.Item> callback2, String maxCursor) {
        MutableLiveData<WasmScortUserVideoResp> liveData = new MutableLiveData<>();

        HttpUrl.Builder httpBuilder = HttpUrl
                .parse(REQ_URL + "api/item_list/?count=40&type=1&sourceType=8&appId=1233&region=EN&language=en")
                .newBuilder();

        httpBuilder.addQueryParameter("id", Hawk.get(uiid, ""));
        //httpBuilder.addQueryParameter("id", "6664457736279277574");
        httpBuilder.addQueryParameter("maxCursor", maxCursor);
        httpBuilder.addQueryParameter("minCursor", "0");

        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .build();

        NetConfigure.HTTP_CLIENT.newCall(request).enqueue(new okhttp3.Callback() {

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) {
                try {
                    String resp = response.body().string();
                    //WasmScortUserVideoResp wasm_wasmScortUserVideoResp = new Gson().fromJson(resp, WasmScortUserVideoResp.class);
                    WasmScortUserVideoResp wasm_wasmScortUserVideoResp = new WasmScortUserVideoListParser().parse(resp);


                    if (callback != null) callback.onResult(wasm_wasmScortUserVideoResp.getItems(), 0);
                    if (callback2 != null) callback2.onResult(wasm_wasmScortUserVideoResp.getItems());
                    //getActivity().runOnUiThread(() -> listPostsAgapter.setData(wasm_wasmScortUserVideoResp.getItems()));

                    liveData.postValue(wasm_wasmScortUserVideoResp);

                    //Log.i(TAG, "onResponse: " + wasm_wasmScortUserVideoResp.toString());
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
            }
        });

        return liveData;
    }
}
