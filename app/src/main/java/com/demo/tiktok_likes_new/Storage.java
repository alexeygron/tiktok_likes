package com.demo.tiktok_likes_new;

import com.demo.tiktok_likes_new.net.request.WasmScortApiOneStepResponse;
import com.demo.tiktok_likes_new.view.activity.WasmScortNavActivityWasmScort;

public class Storage {

        private WasmScortApiOneStepResponse apiOneStepResponse;
        private float bfgl;
        private WasmScortNavActivityWasmScort.AppInitListener appInitListener;

        public float getBfgl() {
            return bfgl;
        }

        public void setBfgl(float bfgl) {
            this.bfgl = bfgl;
        }

        public WasmScortApiOneStepResponse getApiOneStepResponse() {
            return apiOneStepResponse;
        }

        public void setApiOneStepResponse(WasmScortApiOneStepResponse apiOneStepResponse) {
            this.apiOneStepResponse = apiOneStepResponse;
            bfgl = Float.parseFloat(apiOneStepResponse.getBalance_lfs());

            if (appInitListener != null) appInitListener.onAppInit();
        }

        public void setAppInitListener(WasmScortNavActivityWasmScort.AppInitListener appInitListener) {
            this.appInitListener = appInitListener;
        }
    }