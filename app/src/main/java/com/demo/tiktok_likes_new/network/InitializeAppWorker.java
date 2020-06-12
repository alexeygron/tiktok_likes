package com.demo.tiktok_likes_new.network;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.demo.tiktok_likes_new.ScabApp;
import com.demo.tiktok_likes_new.network.request.ApiOneStepRequest;
import com.demo.tiktok_likes_new.network.request.ApiOneStepResponse;
import com.demo.tiktok_likes_new.network.request.ApiTwoStepRequest;
import com.demo.tiktok_likes_new.network.parser.StartAppParser;

import java.io.IOException;

import okhttp3.Callback;

import static com.demo.tiktok_likes_new.network.Constants.getShiUtilsSa;

public class InitializeAppWorker extends Worker {

    private final String TAG = this.getClass().getSimpleName();

    public InitializeAppWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    @NonNull
    @Override
    public Worker.Result doWork() {
        Worker.Result result = Worker.Result.retry();
        startApiOneStepRequest();
        return Worker.Result.success();
    }

    private void startApiOneStepRequest() {
        new ApiOneStepRequest().start(new Callback() {

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) {
                try {
                    String resp = response.body().string();
                    ApiOneStepResponse apiOneStepResponse = new StartAppParser().parse(getShiUtilsSa().ShaiDesc(resp));
                    ScabApp.initDataStorage.setApiOneStepResponse(apiOneStepResponse);
                    startApiTwoStepRequest();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
            }
        });
    }

    private void startApiTwoStepRequest() {
        new ApiTwoStepRequest().start(new Callback() {

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) {

            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
            }
        });
    }
}