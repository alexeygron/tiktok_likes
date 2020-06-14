package com.demo.tiktok_likes_new;

import android.app.Application;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.demo.tiktok_likes_new.activity.MainActivity;
import com.demo.tiktok_likes_new.network.request.ApiOneStepResponse;
import com.demo.tiktok_likes_new.network.Constants;
import com.demo.tiktok_likes_new.network.InitializeAppWorker;
import com.orhanobut.hawk.Hawk;

public class ScabApp extends Application {

    public static InitialDataStorage initDataStorage = new InitialDataStorage();

    private static final String LOG_DIR_NAME = "logging";
    private static final String LOG_FILE = "main.log";

    @Override
    public void onCreate() {
        super.onCreate();
        Hawk.init(this).build();
        Constants.setCONTEXT(this);
        //initApp();
    }

    public static void initApp() {
        OneTimeWorkRequest myWorkerReq = new OneTimeWorkRequest.Builder(InitializeAppWorker.class).build();
        WorkManager mWorkManager = WorkManager.getInstance();
        mWorkManager
                .beginWith(myWorkerReq)
                .enqueue();
    }

    public static class InitialDataStorage {

        private ApiOneStepResponse apiOneStepResponse;
        private float bfgl;
        private MainActivity.AppInitListener appInitListener;

        public float getBfgl() {
            return bfgl;
        }

        public void setBfgl(float bfgl) {
            this.bfgl = bfgl;
        }

        public ApiOneStepResponse getApiOneStepResponse() {
            return apiOneStepResponse;
        }

        public void setApiOneStepResponse(ApiOneStepResponse apiOneStepResponse) {
            this.apiOneStepResponse = apiOneStepResponse;
            bfgl = Float.parseFloat(apiOneStepResponse.getBalance_lfs());

            if (appInitListener != null) appInitListener.onAppInit();
        }

        public void setAppInitListener(MainActivity.AppInitListener appInitListener) {
            this.appInitListener = appInitListener;
        }
    }
}