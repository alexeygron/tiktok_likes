package com.demo.tiktok_likes_new.network;

import android.util.Log;

import androidx.paging.PositionalDataSource;

import com.demo.tiktok_likes_new.RestClient;
import com.demo.tiktok_likes_new.network.data.UserVideoResp;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Request;

import static com.demo.tiktok_likes_new.RestClient.TIKTOK_URL;

public class UserVideosRequest {

    public void loadUserVideos(PositionalDataSource.LoadInitialCallback<UserVideoResp.Item> callback) {

        HttpUrl.Builder httpBuilder = HttpUrl
                .parse(TIKTOK_URL + "api/item_list/?count=30&type=1&sourceType=8&appId=1233&region=EN&language=en")
                .newBuilder();

        httpBuilder.addQueryParameter("id", "6664457736279277574");
        httpBuilder.addQueryParameter("maxCursor", "0");
        httpBuilder.addQueryParameter("minCursor", "0");

        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .build();

        RestClient.getInstance().client.newCall(request).enqueue(new okhttp3.Callback() {

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) {
                try {
                    String resp = response.body().string();
                    //UserVideoResp userVideoResp = new Gson().fromJson(resp, UserVideoResp.class);

                    UserVideoResp userVideoResp = new UserVideoListParser().parse(resp);

                    if (callback != null)
                        callback.onResult(userVideoResp.getItems(), 0);
                    //getActivity().runOnUiThread(() -> listPostsAgapter.setData(userVideoResp.getItems()));

                    //Log.i(TAG, "onResponse: " + userVideoResp.toString());
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
            }
        });

    }
}
