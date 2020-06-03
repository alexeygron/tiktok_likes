package com.demo.tiktok_likes_new;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.orhanobut.hawk.Hawk;

import java.util.HashMap;

import static com.demo.tiktok_likes_new.TestActivity.USER_AGENT;
import static com.demo.tiktok_likes_new.TestActivity.cookies_tag;

public class LoginActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        webView = findViewById(R.id.webView);
        webView.getSettings().setUserAgentString(USER_AGENT);

        webView.getSettings().setUserAgentString(TestActivity.USER_AGENT);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setDomStorageEnabled(true);
            webView.getSettings().setPluginState(WebSettings.PluginState.ON);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

            webView.setWebViewClient(webViewClient);
            webView.loadUrl("https://www.tiktok.com/login/phone-or-email/");

    }

    private WebViewClient webViewClient = new WebViewClient() {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            HashMap<String, String> hashMap = new HashMap<>();
            String cookie = CookieManager.getInstance().getCookie("https://www.tiktok.com/");

            if (!TextUtils.isEmpty(cookie) && cookie.contains("sessionid")) {
                Hawk.put(cookies_tag, cookie);
                Log.i("login_web", "cookies: " + hashMap);

                if (!isStart) startLikesScreen();

            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            //view.loadUrl(url);
            //Log.i("login_web", "onPageStarted: " + url);
        }
    };

    private boolean isStart = false;
    private void startLikesScreen() {
        isStart = true;
        //UserPhotosFragment fragment = new UserPhotosFragment();
        LikesEarnFragment fragment = new LikesEarnFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentFrame, fragment, fragment.getClass().getName())
                .addToBackStack(fragment.getClass().getName())
                .commit();
    }
}
