package com.demo.tiktok_likes_new.network.request;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PositionalDataSource;

import com.demo.tiktok_likes_new.network.Constants;
import com.demo.tiktok_likes_new.network.data.UserVideoResp;
import com.demo.tiktok_likes_new.network.parser.UserVideoListParser;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Request;

import static com.demo.tiktok_likes_new.network.Constants.REQ_URL;

public class UserVideosRequest {

    public LiveData<UserVideoResp> loadUserVideos(PositionalDataSource.LoadInitialCallback<UserVideoResp.Item> callback, PositionalDataSource.LoadRangeCallback<UserVideoResp.Item> callback2, String maxCursor) {
        MutableLiveData<UserVideoResp> liveData = new MutableLiveData<>();

        HttpUrl.Builder httpBuilder = HttpUrl
                .parse(REQ_URL + "api/item_list/?count=40&type=1&sourceType=8&appId=1233&region=EN&language=en")
                .newBuilder();

        //httpBuilder.addQueryParameter("id", Hawk.get(uiid, ""));
        httpBuilder.addQueryParameter("id", "6664457736279277574");
        httpBuilder.addQueryParameter("maxCursor", maxCursor);
        httpBuilder.addQueryParameter("minCursor", "0");

        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .build();

        Constants.HTTP_CLIENT.newCall(request).enqueue(new okhttp3.Callback() {

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) {
                try {
                    String resp = response.body().string();
                    //UserVideoResp userVideoResp = new Gson().fromJson(resp, UserVideoResp.class);
                    UserVideoResp userVideoResp = new UserVideoListParser().parse(resp);


                    if (callback != null) callback.onResult(userVideoResp.getItems(), 0);
                    if (callback2 != null) callback2.onResult(userVideoResp.getItems());
                    //getActivity().runOnUiThread(() -> listPostsAgapter.setData(userVideoResp.getItems()));

                    liveData.postValue(userVideoResp);

                    //Log.i(TAG, "onResponse: " + userVideoResp.toString());
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
