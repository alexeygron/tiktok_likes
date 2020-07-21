package com.appls.tokall.net;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.appls.tokall.BuildConfig;
import com.appls.tokall.util.WasmScortUtilsCr;

import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import static com.appls.tokall.util.Common.getAndroidId;
import static com.appls.tokall.util.KeysCr.KEY_CRP;
import static com.appls.tokall.util.KeysCr.PASS_CRP;
import static com.appls.tokall.util.WasmScortUtilsCr.getStrObj;

public final class NetConfigure {

    public static final String REQ_URL = getStrObj("DWZK/9mTMmbFQiCoaXHzg0v2S69NpRhDoKwtchbUrtM=");
    public static final String API_URL = getStrObj("FvBcsjo+wgzeyw8nDb3GBojdN2hrpls6/YTrVBkd8OI=");

    public static Context CONTEXT;

    private static final int TIMEOUT = 16;

    private static Headers API_HEADERS;

    public static Headers getApiHeaders() {
        if (API_HEADERS == null) initHeaders();
        return API_HEADERS;
    }
    private static WasmScortUtilsCr wasmScortUtilsCr;

    static {
        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Log.i("NET_LOG", message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        final OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.addInterceptor(loggingInterceptor);
        HTTP_CLIENT = httpClientBuilder
                .build();
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

    public static OkHttpClient HTTP_CLIENT;

    public static WasmScortUtilsCr getWasmScortUtilsCr() {
        if (wasmScortUtilsCr == null)
            wasmScortUtilsCr = new WasmScortUtilsCr(getAndroidId(), KEY_CRP, PASS_CRP);
        return wasmScortUtilsCr;
    }

    public static void setCONTEXT(Context CONTEXT) {
        NetConfigure.CONTEXT = CONTEXT;
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
