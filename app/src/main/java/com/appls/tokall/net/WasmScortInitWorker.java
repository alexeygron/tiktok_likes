package com.appls.tokall.net;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.appls.tokall.TokkallApp;
import com.appls.tokall.net.parser.WasmScortStartAppParser;
import com.appls.tokall.net.request.WasmScortApiOneStepRequest;
import com.appls.tokall.net.request.WasmScortApiOneStepResponse;
import com.appls.tokall.net.request.WasmScortApiTwoStepRequest;

import java.io.IOException;

import okhttp3.Callback;

import static com.appls.tokall.net.NetConfigure.getWasmScortUtilsCr;

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
                    TokkallApp.wasm_storage.setApiOneStepResponse(apiOneStepResponse);
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