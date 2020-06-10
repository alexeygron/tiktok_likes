package com.demo.tiktok_likes_new.util;

import android.graphics.PorterDuff;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.tiktok_likes_new.R;
import com.demo.tiktok_likes_new.network.Constants;

import static android.view.View.TEXT_ALIGNMENT_CENTER;

public class UiUtils {

    public static Toast showToast(String message) {
        Toast toast = null;
        try {
            toast = Toast.makeText(Constants.CONTEXT, message, Toast.LENGTH_SHORT);
            View view = toast.getView();

            view.getBackground().setColorFilter(Constants.CONTEXT.getResources().getColor(R.color.gray), PorterDuff.Mode.SRC_IN);
            TextView text = view.findViewById(android.R.id.message);
            text.setTextColor(Constants.CONTEXT.getResources().getColor(R.color.white));
            text.setGravity(Gravity.CENTER);
            text.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toast;
    }
}
