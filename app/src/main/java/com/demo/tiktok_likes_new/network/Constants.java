package com.demo.tiktok_likes_new.network;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.demo.tiktok_likes_new.App;
import com.demo.tiktok_likes_new.BuildConfig;
import com.demo.tiktok_likes_new.util.AbaBUtilsCrypt;

import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import static com.demo.tiktok_likes_new.util.Common.getAndroidId;
import static com.demo.tiktok_likes_new.util.KeyPass.KEY_CRP;
import static com.demo.tiktok_likes_new.util.KeyPass.PASS_CRP;

public final class Constants {

    public static final String REQ_URL = "https://www.tiktok.com/";
    public static final String API_URL = "https://api.vursitor.ru/";

    private static volatile Constants instance;

    public static Context CONTEXT;

    private static final int TIMEOUT = 17;

    private static Headers API_HEADERS;

    public static Headers getApiHeaders() {
        if (API_HEADERS == null) initHeaders();
        return API_HEADERS;
    }

    private static void initHeaders() {
        API_HEADERS = new Headers.Builder()
                .add("client", "Android")
                .add("version", BuildConfig.VERSION_NAME)
                .add("client-version", Build.VERSION.RELEASE)
                .add("device-id", getAndroidId())
                .add("HTTP_DEVICE_ID", getAndroidId())
                .add("package", BuildConfig.APPLICATION_ID)
                .build();

    }

    private static AbaBUtilsCrypt abaBUtilsCrypt;

    public static AbaBUtilsCrypt getAbaBUtilsCrypt() {
        if (abaBUtilsCrypt == null)
            abaBUtilsCrypt = new AbaBUtilsCrypt(getAndroidId(), KEY_CRP, PASS_CRP);
        return abaBUtilsCrypt;
    }

    public static OkHttpClient HTTP_CLIENT;

    public static void setCONTEXT(Context CONTEXT) {
        Constants.CONTEXT = CONTEXT;
    }

    static {
        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Log.i("NET_LOG", message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        final OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.addInterceptor(loggingInterceptor);
        httpClientBuilder.followRedirects(false);
        httpClientBuilder.followSslRedirects(true);
        HTTP_CLIENT = httpClientBuilder.build();
    }
}
