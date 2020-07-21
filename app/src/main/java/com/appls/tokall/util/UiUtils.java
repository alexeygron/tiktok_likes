package com.appls.tokall.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.appls.tokall.R;
import com.appls.tokall.net.NetConfigure;
import com.orhanobut.hawk.Hawk;

import static android.view.View.TEXT_ALIGNMENT_CENTER;

public class UiUtils {

    public static Toast showToast(String message) {
        Toast toast = null;
        try {
            toast = Toast.makeText(NetConfigure.CONTEXT, message, Toast.LENGTH_SHORT);
            View view = toast.getView();

            view.getBackground().setColorFilter(NetConfigure.CONTEXT.getResources().getColor(R.color.dgd_res_gray), PorterDuff.Mode.SRC_IN);
            TextView text = view.findViewById(android.R.id.message);
            text.setTextColor(NetConfigure.CONTEXT.getResources().getColor(R.color.dgd_res_white));
            text.setGravity(Gravity.CENTER);
            text.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toast;
    }

    public static void checkStarStatus(Activity activity) {
        if (!Hawk.contains(StarDialog.class.getName())) {
            int launch = Hawk.get("launch", 0);
            if (launch == 0) {
                Hawk.put("launch", 1);
            } else {
                new Handler().postDelayed(() -> StarDialog.show(activity), 7000);
            }
        }

    }

    static void OpenMarket() {
        Intent intent;
        try {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + NetConfigure.CONTEXT.getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            NetConfigure.CONTEXT.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + NetConfigure.CONTEXT.getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            NetConfigure.CONTEXT.startActivity(intent);
        }
    }

    static void OpenMarket(Context context, String url) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
        }
    }
}
