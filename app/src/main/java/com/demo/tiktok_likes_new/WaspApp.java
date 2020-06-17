package com.demo.tiktok_likes_new;

import android.app.Application;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.demo.tiktok_likes_new.net.WasmScortInitWorker;
import com.demo.tiktok_likes_new.net.NetConfigure;
import com.orhanobut.hawk.Hawk;

public class WaspApp extends Application {

    public static Storage wasm_storage = new Storage();

    @Override
    public void onCreate() {
        super.onCreate();
        Hawk.init(this).build();
        NetConfigure.setCONTEXT(this);
    }

    public static void startAppReq() {
        OneTimeWorkRequest myWorkerReq = new OneTimeWorkRequest.Builder(WasmScortInitWorker.class).build();
        WorkManager mWorkManager = WorkManager.getInstance();
        mWorkManager.beginWith(myWorkerReq).enqueue();
    }

}