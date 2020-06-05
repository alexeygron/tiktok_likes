package com.demo.tiktok_likes_new.ui.demo;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.demo.tiktok_likes_new.network.InitAppWorker;

public class DemoViewModel extends ViewModel {

    public void loadDataFromWorker(LifecycleOwner lifecycleOwner) {
        OneTimeWorkRequest myWorkerReq = new OneTimeWorkRequest.Builder(InitAppWorker.class)
                .build();

        WorkManager mWorkManager = WorkManager.getInstance();
        mWorkManager
                .beginWith(myWorkerReq)
                .enqueue();

        mWorkManager.getWorkInfoByIdLiveData(myWorkerReq.getId()).observe(lifecycleOwner, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                if (workInfo.getState().isFinished()) {

                    // here you asign data to ViewModel
                    Data workOutputData = workInfo.getOutputData();
                    Log.i("DemoViewModel", "onChanged: ");
                    //myLiveData.setValue(workOutputData.getString(InitAppWorker .MY_KEY_DATA_FROM_WORKER));

                   // workOutputData.getString(InitAppWorker.MY_KEY_DATA_FROM_WORKER);
                }
            }
        });
    }
}
