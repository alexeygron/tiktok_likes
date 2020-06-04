package com.demo.tiktok_likes_new.network;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.demo.tiktok_likes_new.App;
import com.demo.tiktok_likes_new.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import static com.demo.tiktok_likes_new.util.Common.getAndroidId;

public final class Constants {

    public static final String REQ_URL = "https://www.tiktok.com/";
    public static final String API_URL = "https://api.vursitor.ru/";

    private static volatile Constants instance;

    public static final Context CONTEXT = App.getInstance();

    private static final int TIMEOUT = 17;
    public static final boolean DEBUG_MODE = true;
    public static final boolean TOK_REQUEST_ENABLED = true;
    public static final boolean ANIMATE_PREVIEW = false;

    public static Headers API_HEADERS = new Headers.Builder()
            .add("client", "Android")
            .add("version", BuildConfig.VERSION_NAME)
            .add("client-version", Build.VERSION.RELEASE)
            .add("device-id", getAndroidId())
            .add("package", BuildConfig.APPLICATION_ID)
            .build();

    public static OkHttpClient HTTP_CLIENT;

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
