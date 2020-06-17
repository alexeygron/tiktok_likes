package com.demo.tiktok_likes_new.util;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import java.security.MessageDigest;
import java.util.Locale;

import static com.demo.tiktok_likes_new.net.NetConfigure.CONTEXT;

public class Common {

    public static final String cookies_tag = "cookies";

    public static final String USER_AGENT = "Mozilla/5.0 (Linux; Android 6.0.1; Moto G (4)) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.92 Mobile Safari/537.36";
    public static char[] SDA_ARR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static final boolean TOK_REQUEST_ENABLED = true;
    public static final int DEFAULT_TAB = 0;

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
            //return "bb021t";
            return Settings.Secure.getString(CONTEXT.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getPartStr(String start, String end, String source) {
        String returnStr = source;
        returnStr = returnStr.substring(source.indexOf(start));
        returnStr = returnStr.replace(start, "");
        returnStr = returnStr.substring(0, returnStr.indexOf(end)).trim();
        return returnStr;
    }

    static String MD5(final String s) {
        final String MD5 = "MD5";
        try {
            MessageDigest digest = MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                StringBuilder h = new StringBuilder(Integer.toHexString(0xFF & aMessageDigest));
                while (h.length() < 2)
                    h.insert(0, "0");
                hexString.append(h);
            }
            return hexString.toString();
        } catch (Exception ignored) {

        }
        return "";
    }
}
