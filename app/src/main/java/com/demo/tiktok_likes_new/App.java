package com.demo.tiktok_likes_new;

import android.app.Application;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.demo.tiktok_likes_new.network.AppStartResponse;
import com.demo.tiktok_likes_new.ui.demo.InitAppWorker;
import com.demo.tiktok_likes_new.util.Crypto;
import com.orhanobut.hawk.Hawk;

import static com.demo.tiktok_likes_new.util.Common.getAndroidId;

public class App extends Application {

    private static App instance;

    private static final String LOG_DIR_NAME = "logging";
    private static final String LOG_FILE = "main.log";

    private AppStartResponse appStartResponse;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Hawk.init(this).build();

        Crypto.init(getAndroidId());
        initApp();
    }

    private void initApp() {
        OneTimeWorkRequest myWorkerReq = new OneTimeWorkRequest.Builder(InitAppWorker.class).build();
        WorkManager mWorkManager = WorkManager.getInstance();
        mWorkManager
                .beginWith(myWorkerReq)
                .enqueue();
    }

    public AppStartResponse getAppStartResponse() {
        return appStartResponse;
    }

    public void setAppStartResponse(AppStartResponse appStartResponse) {
        this.appStartResponse = appStartResponse;
    }
}