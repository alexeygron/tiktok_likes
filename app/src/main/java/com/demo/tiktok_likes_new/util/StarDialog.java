package com.demo.tiktok_likes_new.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;

import com.demo.tiktok_likes_new.R;
import com.orhanobut.hawk.Hawk;

import static com.demo.tiktok_likes_new.util.UiUtils.OpenMarket;

public class StarDialog extends Dialog {

    private static StarDialog create(@NonNull Context context) {
        StarDialog dialog = new StarDialog(context, R.style.SctStarDialog);
        if (dialog.getWindow() != null)
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        return dialog;
    }

    private StarDialog(@NonNull Context context, int style) {
        super(context, style);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_dialog_layout);
        AppCompatRatingBar ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(ratingBarChangeListener);
        findViewById(R.id.later_btn).setOnClickListener(v -> dismiss());

    }

    private RatingBar.OnRatingBarChangeListener ratingBarChangeListener = (ratingBar, rating, fromUser) -> {
        if (rating == 5) {
            new Handler().postDelayed(() -> {
                OpenMarket();
                dismiss();
            }, 200);
            UiUtils.showToast(getContext().getString(R.string.thank_you));
        } else {
            new Handler().postDelayed(this::dismiss, 200);
            UiUtils.showToast(getContext().getString(R.string.thank_you));
        }
    };

    public static void show(Context context) {
        StarDialog dialog = StarDialog.create(context);
        dialog.setCancelable(false);
        Hawk.put(StarDialog.class.getName(), true);
        dialog.show();
    }
}
