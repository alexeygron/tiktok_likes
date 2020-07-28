package com.demo.tiktok_likes_new.net;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.demo.tiktok_likes_new.WaspApp;
import com.demo.tiktok_likes_new.net.parser.WasmScortStartAppParser;
import com.demo.tiktok_likes_new.net.request.WasmScortApiOneStepRequest;
import com.demo.tiktok_likes_new.net.request.WasmScortApiOneStepResponse;
import com.demo.tiktok_likes_new.net.request.WasmScortApiTwoStepRequest;

import java.io.IOException;

import okhttp3.Callback;

import static com.demo.tiktok_likes_new.net.NetConfigure.getWasmScortUtilsCr;

public class WasmScortInitWorker extends Worker {

    private final String TAG = this.getClass().getSimpleName();

    public WasmScortInitWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
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
        new WasmScortApiOneStepRequest().start(new Callback() {

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) {
                try {
                    String resp = response.body().string();
                    WasmScortApiOneStepResponse apiOneStepResponse = new WasmScortStartAppParser().parse(getWasmScortUtilsCr().ShaiDesc(resp));
                    WaspApp.wasm_storage.setApiOneStepResponse(apiOneStepResponse);
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
        new WasmScortApiTwoStepRequest().start(new Callback() {

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) {

            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
            }
        });
    }
}