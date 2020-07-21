package com.appls.tokall.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.appls.tokall.BuildConfig;
import com.appls.tokall.R;
import com.appls.tokall.TokkallApp;
import com.orhanobut.hawk.Hawk;

import static com.appls.tokall.util.UiUtils.OpenMarket;
import static com.appls.tokall.util.WasmScortUtilsCr.getStrObj;

public class WasmScortUpdDialog extends Dialog {

    String url = "";
    boolean cancel;

    private WasmScortUpdDialog(@NonNull Context context, int style) {
        super(context, style);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    private static WasmScortUpdDialog create(@NonNull Context context) {
        WasmScortUpdDialog dialog = new WasmScortUpdDialog(context, R.style.WasmSctStarDialog);
        if (dialog.getWindow() != null)
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        return dialog;
    }

    public static void show(Context context) {
        WasmScortUpdDialog dialog = WasmScortUpdDialog.create(context);
        dialog.setUrl(TokkallApp.wasm_storage.getApiOneStepResponse().getUdp_url());
        boolean cancelable = (TokkallApp.wasm_storage.getApiOneStepResponse().getUdp_type() == 1 || BuildConfig.DEBUG);
        dialog.setCancel(cancelable);
        dialog.setCancelable(cancelable);
        Hawk.put(WasmScortUpdDialog.class.getName(), true);
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wasm_scort_upd_dialog);

        Button btn1 = findViewById(R.id.later_btn);
        btn1.setText(getStrObj(R.string.dgd_res_ups));

        btn1.setOnClickListener(v -> {
            if (cancel) {
                dismiss();
            }
            OpenMarket(getContext(), url);
        });

        findViewById(R.id.upn).setOnClickListener(v -> {
            if (cancel) {
                dismiss();
            }
            OpenMarket(getContext(), url);
        });

        ((TextView) findViewById(R.id.subTitle)).setText(getStrObj(R.string.dgd_res_update_tx));

    }
}
