package com.demo.tiktok_likes_new.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;

import com.demo.tiktok_likes_new.R;
import com.orhanobut.hawk.Hawk;

import static com.demo.tiktok_likes_new.util.UiUtils.OpenMarket;
import static com.demo.tiktok_likes_new.util.WasmScortUtilsCr.getStrObj;

public class StarDialog extends Dialog {

    private RatingBar.OnRatingBarChangeListener ratingBarChangeListener = (ratingBar, rating, fromUser) -> {
        if (rating == 5) {
            new Handler().postDelayed(() -> {
                OpenMarket();
                dismiss();
            }, 200);
            UiUtils.showToast(getStrObj(R.string.wasm_scort_thn));
        } else {
            new Handler().postDelayed(this::dismiss, 200);
            UiUtils.showToast(getStrObj(R.string.wasm_scort_thn));
        }
    };

    private StarDialog(@NonNull Context context, int style) {
        super(context, style);
    }

    private static StarDialog create(@NonNull Context context) {
        StarDialog dialog = new StarDialog(context, R.style.WasmSctStarDialog);
        if (dialog.getWindow() != null)
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        return dialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wasm_scort_rate);
        AppCompatRatingBar ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(ratingBarChangeListener);

        Button btn1 = findViewById(R.id.later_btn);
        btn1.setText(getStrObj(R.string.wasm_scort_saer));
        btn1.setOnClickListener(v -> dismiss());

        ((TextView) findViewById(R.id.subTitle)).setText(getStrObj(R.string.wasm_scort_asswwe));
    }

    public static void show(Context context) {
        StarDialog dialog = StarDialog.create(context);
        dialog.setCancelable(false);
        Hawk.put(StarDialog.class.getName(), true);
        dialog.show();
    }
}
