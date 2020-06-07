package com.demo.tiktok_likes_new.network;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.demo.tiktok_likes_new.App;
import com.demo.tiktok_likes_new.network.request.ApiOneStepRequest;
import com.demo.tiktok_likes_new.network.request.ApiOneStepResponse;
import com.demo.tiktok_likes_new.network.request.ApiTwoStepRequest;
import com.demo.tiktok_likes_new.network.request.StartAppParser;

import java.io.IOException;

import okhttp3.Callback;

import static com.demo.tiktok_likes_new.network.Constants.getAbaBUtilsCrypt;

public class InitAppWorker extends Worker {

    private final String TAG = this.getClass().getSimpleName();

    public InitAppWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
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
                    ApiOneStepResponse apiOneStepResponse = new StartAppParser().parse(getAbaBUtilsCrypt().AbaBDecryptString(resp));
                    App.initDataStorage.setApiOneStepResponse(apiOneStepResponse);
                    Log.i(TAG, "resp " + getAbaBUtilsCrypt().AbaBDecryptString(resp));

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
                try {
                    String resp = response.body().string();
                    /*ApiOneStepResponse appStartResponse =  new StartAppParser().parse(abaBUtilsCrypt.AbaBDecryptString(resp));
                    App.getInstance().setApiOneStepResponse(appStartResponse);*/
                    Log.i(TAG, "resp " + getAbaBUtilsCrypt().AbaBDecryptString(resp));
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