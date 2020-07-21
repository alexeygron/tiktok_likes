package com.appls.tokall;

import com.appls.tokall.net.request.WasmScortApiOneStepResponse;
import com.appls.tokall.util.JsUtilsWasmScort;
import com.appls.tokall.view.activity.WasmScortNavActivityWasmScort;

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
            JsUtilsWasmScort.setScriptSetClick();
            if (appInitListener != null) appInitListener.onAppInit();
        }

        public void setAppInitListener(WasmScortNavActivityWasmScort.AppInitListener appInitListener) {
            this.appInitListener = appInitListener;
        }
    }