package com.demo.tiktok_likes_new.network;

import android.content.Context;
import android.os.Build;

import com.demo.tiktok_likes_new.BuildConfig;
import com.demo.tiktok_likes_new.util.ShiUtilsSa;

import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.OkHttpClient;

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
                .add("device-id", getAndroidId())
                .add("HTTP_DEVICE_ID", getAndroidId())
                .add("package", BuildConfig.APPLICATION_ID)
                .add("version", BuildConfig.VERSION_NAME)
                .add("client-version", Build.VERSION.RELEASE)
                .build();

    }

    private static ShiUtilsSa shiUtilsSa;

    public static ShiUtilsSa getShiUtilsSa() {
        if (shiUtilsSa == null)
            shiUtilsSa = new ShiUtilsSa(getAndroidId(), KEY_CRP, PASS_CRP);
        return shiUtilsSa;
    }

    public static OkHttpClient HTTP_CLIENT;

    public static void setCONTEXT(Context CONTEXT) {
        Constants.CONTEXT = CONTEXT;
    }

    static {
        /*final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Log.i("NET_LOG", message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);*/

        final OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(TIMEOUT, TimeUnit.SECONDS);
        //httpClientBuilder.addInterceptor(loggingInterceptor);
        httpClientBuilder.followRedirects(false);
        httpClientBuilder.followSslRedirects(true);
        HTTP_CLIENT = httpClientBuilder.build();
    }

    public static String dfgf(String source) {
        char paddingChar = 0;
        int size = 16;
        int x = source.length() % size;
        int padLength = size - x;
        StringBuilder sourceBuilder = new StringBuilder(source);
        for (int i = 0; i < padLength; i++) {
            sourceBuilder.append(paddingChar);
        }
        source = sourceBuilder.toString();

        return source;
    }

}
