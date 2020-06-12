package com.demo.tiktok_likes_new.activity;

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
import com.demo.tiktok_likes_new.network.request.LoadTrendingRequest;
import com.demo.tiktok_likes_new.util.KeyPass;
import com.orhanobut.hawk.Hawk;

import java.io.IOException;

import static com.demo.tiktok_likes_new.network.Constants.REQ_URL;
import static com.demo.tiktok_likes_new.util.Common.USER_AGENT;
import static com.demo.tiktok_likes_new.util.Common.cookies_tag;
import static com.demo.tiktok_likes_new.util.Common.getPartStr;
import static com.demo.tiktok_likes_new.util.KeyPass.uiid;

public class LoginActivity extends BaseAbstractActivity {

    WebView mWebView;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        CookieManager.getInstance().removeAllCookie();

        mWebView = findViewById(R.id.webView);
        mProgressBar = findViewById(R.id.progressBar);
        mWebView.getSettings().setUserAgentString(USER_AGENT);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        mWebView.setWebViewClient(webViewClient);
        mWebView.loadUrl(REQ_URL + "login/phone-or-email/");
    }

    private WebViewClient webViewClient = new WebViewClient() {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            String cookie = CookieManager.getInstance().getCookie(REQ_URL);
            if (!TextUtils.isEmpty(cookie) && cookie.contains("sessionid")) {
                Hawk.put(cookies_tag, cookie);
                if (!isStartLoadIds) loadAndStoreIds(cookie);
            } else {
                mWebView.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mWebView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        }
    };

    private void loadAndStoreIds(String cookies) {
        isStartLoadIds = true;

        new LoadTrendingRequest().start(cookies, new okhttp3.Callback() {
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
                        Hawk.put(KeyPass.uniqueId, uniqueId);
                        startMainActivity();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean isStartLoadIds = false;

    private void startMainActivity() {
        MainActivity.start(this);
        finish();
    }

    public static void start(final Context context) {
        final Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
