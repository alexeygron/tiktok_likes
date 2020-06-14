package com.demo.tiktok_likes_new.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;

import androidx.annotation.NonNull;

import com.demo.tiktok_likes_new.BuildConfig;
import com.demo.tiktok_likes_new.R;
import com.demo.tiktok_likes_new.ScabApp;
import com.orhanobut.hawk.Hawk;

import static com.demo.tiktok_likes_new.util.UiUtils.OpenMarket;

public class NewRelleaseDialog extends Dialog {

    String url = "";
    boolean cancel;

    private static NewRelleaseDialog create(@NonNull Context context) {
        NewRelleaseDialog dialog = new NewRelleaseDialog(context, R.style.SctStarDialog);
        if (dialog.getWindow() != null)
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        return dialog;
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

    private NewRelleaseDialog(@NonNull Context context, int style) {
        super(context, style);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_release_dialog_layout);
        findViewById(R.id.later_btn).setOnClickListener(v -> {
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

    }


    public static void show(Context context) {
        NewRelleaseDialog dialog = NewRelleaseDialog.create(context);
        dialog.setUrl(ScabApp.initDataStorage.getApiOneStepResponse().getUdp_url());
        boolean cancelable = (ScabApp.initDataStorage.getApiOneStepResponse().getUdp_type() == 1 || BuildConfig.DEBUG);
        dialog.setCancel(cancelable);
        dialog.setCancelable(cancelable);
        Hawk.put(NewRelleaseDialog.class.getName(), true);
        dialog.show();
    }
}
