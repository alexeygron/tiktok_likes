package com.demo.tiktok_likes_new.util;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import java.util.Locale;

import static com.demo.tiktok_likes_new.network.Constants.CONTEXT;

public class Common {

    private static Handler mainThreadHandler;

    private static Handler getMainThreadHandler() {
        if (mainThreadHandler == null) {
            mainThreadHandler = new Handler(Looper.getMainLooper());
        }
        return mainThreadHandler;
    }

    public static void runOnMainThread(Runnable rnb) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            rnb.run();
        } else {
            getMainThreadHandler().post(rnb);
        }
    }

    public static String getLocale() {
        String locale = "";
        try {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
                locale = Locale.getDefault().toString();
            } else {
                locale = Locale.getDefault().toString();
            }
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }

        return locale;
    }

    @SuppressLint("HardwareIds")
    public static String getAndroidId() {
        try {
            return Settings.Secure.getString(CONTEXT.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getPartStr(String source, String start, String end) {
        String returnStr = source;
        returnStr = returnStr.substring(source.indexOf(start));
        returnStr = returnStr.replace(start, "");
        returnStr = returnStr.substring(0, returnStr.indexOf(end)).trim();
        return returnStr;
    }

}