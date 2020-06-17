package com.demo.tiktok_likes_new.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.demo.tiktok_likes_new.R;
import com.demo.tiktok_likes_new.net.request.WasmScortLoadTrendingRequest;
import com.demo.tiktok_likes_new.util.KeysCr;
import com.orhanobut.hawk.Hawk;

import java.io.IOException;

import static com.demo.tiktok_likes_new.net.NetConfigure.REQ_URL;
import static com.demo.tiktok_likes_new.util.Common.USER_AGENT;
import static com.demo.tiktok_likes_new.util.Common.cookies_tag;
import static com.demo.tiktok_likes_new.util.Common.getPartStr;
import static com.demo.tiktok_likes_new.util.KeysCr.uiid;


public class AppAuthActivityWasmScort extends WasmScortBaseActivity {

    private WebView mWebView;
    private ProgressBar mProgressBar;

    public static void start(final Context context) {
        final Intent intent = new Intent(context, AppAuthActivityWasmScort.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private WebViewClient webViewClient = new WebViewClient() {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            String cookie = CookieManager.getInstance().getCookie(REQ_URL);
            if (!TextUtils.isEmpty(cookie) && cookie.contains("sessionid")) {
                Hawk.put(cookies_tag, cookie);
                if (!isStartLoadIds()) loadAndStoreIds(cookie);
            } else {
                getmWebView().setVisibility(View.VISIBLE);
                getmProgressBar().setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            getmWebView().setVisibility(View.GONE);
            getmProgressBar().setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wasm_scort_activity_auth);
        CookieManager.getInstance().removeAllCookie();

        setmWebView(findViewById(R.id.webView));
        setmProgressBar(findViewById(R.id.progressBar));
        getmWebView().getSettings().setUserAgentString(USER_AGENT);
        getmWebView().getSettings().setJavaScriptEnabled(true);
        getmWebView().getSettings().setDomStorageEnabled(true);
        getmWebView().getSettings().setPluginState(WebSettings.PluginState.ON);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getmWebView().getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        getmWebView().setWebViewClient(getWebViewClient());
        getmWebView().loadUrl(REQ_URL + "login/phone-or-email/");
    }

    private boolean isStartLoadIds = false;

    private void loadAndStoreIds(String cookies) {
        setStartLoadIds(true);

        Log.i("loadAndStor", "cookies: " + cookies);
        new WasmScortLoadTrendingRequest().start(cookies, new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                // liveData.postValue(null);
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) {
                try {
                    String resp = response.body().string();
                    if (resp.contains("\"$user\":{\"uid\":")) {
                        String uid = getPartStr("\"uid\":\"", "\",", resp);
                        String uniqueId = getPartStr("\"uniqueId\":\"", "\",", resp);
                        Hawk.put(uiid, uid);
                        Hawk.put(KeysCr.uniqueId, uniqueId);
                        startMainActivity();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void startMainActivity() {
        WasmScortNavActivityWasmScort.start(this);
        finish();
    }

    public WebView getmWebView() {
        return mWebView;
    }

    public void setmWebView(WebView wasm_mWebView) {
        mWebView = wasm_mWebView;
    }

    public ProgressBar getmProgressBar() {
        return mProgressBar;
    }

    public void setmProgressBar(ProgressBar wasm_mProgressBar) {
        mProgressBar = wasm_mProgressBar;
    }

    public WebViewClient getWebViewClient() {
        return webViewClient;
    }

    public void setWebViewClient(WebViewClient wasm_webViewClient) {
        webViewClient = wasm_webViewClient;
    }

    public boolean isStartLoadIds() {
        return isStartLoadIds;
    }

    public void setStartLoadIds(boolean wasm_startLoadIds) {
        isStartLoadIds = wasm_startLoadIds;
    }
}
