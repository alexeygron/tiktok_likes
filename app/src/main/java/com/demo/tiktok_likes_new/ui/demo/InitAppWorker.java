package com.demo.tiktok_likes_new.ui.demo;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.demo.tiktok_likes_new.App;
import com.demo.tiktok_likes_new.network.AppStartRequest;
import com.demo.tiktok_likes_new.network.AppStartResponse;
import com.demo.tiktok_likes_new.network.StartAppParser;
import com.demo.tiktok_likes_new.util.AbaBUtilsCrypt;

import java.io.IOException;

import okhttp3.Callback;

import static com.demo.tiktok_likes_new.util.Common.getAndroidId;
import static com.demo.tiktok_likes_new.util.KeyPass.KEY_CRP;
import static com.demo.tiktok_likes_new.util.KeyPass.PASS_CRP;

public class InitAppWorker extends Worker {

    public InitAppWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    @NonNull
    @Override
    public Worker.Result doWork() {
        Worker.Result result = Worker.Result.retry();
        AbaBUtilsCrypt abaBUtilsCrypt = new AbaBUtilsCrypt(getAndroidId(), KEY_CRP, PASS_CRP);

        new AppStartRequest().start(new Callback() {

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) {
                try {
                    String resp = response.body().string();
                    AppStartResponse appStartResponse =  new StartAppParser().parse(abaBUtilsCrypt.AbaBDecryptString(resp));
                    App.getInstance().setAppStartResponse(appStartResponse);
                    Log.i("loadUserVideos", "resp " + appStartResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
            }
        });

        return Worker.Result.success();
    }
}