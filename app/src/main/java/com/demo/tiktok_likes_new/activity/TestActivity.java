package com.demo.tiktok_likes_new.activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import com.demo.tiktok_likes_new.fragment.LikesEarnFragment;
import com.demo.tiktok_likes_new.R;
import com.orhanobut.hawk.Hawk;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

import static com.demo.tiktok_likes_new.util.Common.getPartStr;

public class TestActivity extends BaseAbstractActivity {

    private ViewGroup contentFrame;
    private WebView webView;
    private String cookiesStr;
    private String cookiesStr2;
    public static final String cookies_tag = "cookies";
    private String TAG = "login_web";
    public static final Map<String, String> webApiParams = new HashMap<>();

    //public static final String USER_AGENT = "Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1";
    public static final String USER_AGENT = "Mozilla/5.0 (Linux; Android 6.0.1; Moto G (4)) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.92 Mobile Safari/537.36";
    public static final OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor(message -> Log.d("NETWORK_LOG", message)).setLevel(HttpLoggingInterceptor.Level.BODY)).build();

    public static String scriptSetLike = "(function(){document.getElementsByClassName(\"tiktok-toolbar-like\")[0].click()})();";
    public static String scriptSetListener = "(function() {\n" +
            "    var origOpen = XMLHttpRequest.prototype.open;\n" +
            "    XMLHttpRequest.prototype.open = function() {\n" +
            "        this.addEventListener('load', function() {\n" +
            "            console.log(\"api_req\" + this.responseText); //whatever the response was\n" +
            "        });\n" +
            "        origOpen.apply(this, arguments);\n" +
            "    };\n" +
            "})();";

    String uiid = "";
    String userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        if (Hawk.contains("cookies")) {
            LikesEarnFragment fragment = new LikesEarnFragment();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentFrame, fragment, fragment.getClass().getName())
                    .addToBackStack(fragment.getClass().getName())
                    .commit();

            /*cookiesStr = Hawk.get("cookies", "");
            cookiesStr2 = Hawk.get("cookies2", "");
            //Log.i("login_web", "cookies: " + cookiesStr);
            //like();
            //getProfile();

            webView = findViewById(R.id.webView);
            webView.getSettings().setUserAgentString(USER_AGENT);

            webView.getSettings().setJavaScriptEnabled(true);

            webView.getSettings().setDomStorageEnabled(true);


            webView.getSettings().setPluginState(WebSettings.PluginState.ON);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

            CookieSyncManager.getInstance().sync();
            webView.setWebViewClient(webViewClient2);
            webView.setWebChromeClient(new WebChromeClient() {
                public boolean onConsoleMessage(ConsoleMessage cmsg) {
                    if (cmsg.message().contains("api_req")) {
                        Log.i(TAG, "onConsoleMessage: " + cmsg.message());
                    }

                    if (cmsg.message() != null && cmsg.message().contains("api_req") && cmsg.message().contains("\"is_digg\":")) {
                        Toast.makeText(TestActivity.this, "Like confirm!", Toast.LENGTH_SHORT).show();
                    }
                    return true;

                }
            });

            Map<String, String> headers = new HashMap<>();
            headers.put("cookie", cookiesStr);
            webView.loadUrl("https://www.tiktok.com/@klavacoca/video/6831459866024955141", headers);*/

            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.removeAllCookie();
                cookieManager.setAcceptThirdPartyCookies(webView, true);
            } else {
                CookieManager.getInstance().setAcceptCookie(true);
            }*/
        } else {

            webView = findViewById(R.id.webView);
            webView.getSettings().setUserAgentString(USER_AGENT);

            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.getSettings().setDomStorageEnabled(true);

            webView.getSettings().setDisplayZoomControls(false);
            webView.getSettings().setAppCacheEnabled(true);
            webView.getSettings().setLoadsImagesAutomatically(true);
            webView.getSettings().setBuiltInZoomControls(false);
            webView.getSettings().setPluginState(WebSettings.PluginState.ON);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.removeAllCookie();
                cookieManager.setAcceptThirdPartyCookies(webView, true);
            } else {
                CookieManager.getInstance().setAcceptCookie(true);
            }
            webView.setWebViewClient(webViewClient);
            //webView.loadUrl("http://www.tiktok.com");
            webView.loadUrl("https://www.tiktok.com/login/phone-or-email/");
        }
    }

    private WebViewClient webViewClient2 = new WebViewClient() {

        boolean isLiked = false;
        boolean isEval = false;

        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return super.shouldInterceptRequest(view, request);
        }


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //Log.i("login_web", "shouldOverrideUrlLoading " );

            Map<String, String> headers = new HashMap<>();
            headers.put("cookie", cookiesStr2);
            view.loadUrl(url, headers);
            return true;
        }

        @Override
        public void onLoadResource(WebView  view, String  url){
            //Log.i("login_web", "onLoadResource " +  url);

            if (url.contains("/api/commit/item/digg/")) {
                Log.i(TAG, "SET LIKE! ");
            }
            //view.
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //Log.i(TAG, "onPageFinished " );

            if (!isLiked) {
                isLiked = true;
                new Handler().postDelayed(() -> evaluateJsLike(), 1000);
            }

            if (!isEval) {
                isEval = true;
                new Handler().postDelayed(() -> evaluateJsListener(), 0);
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }
    };

    private WebViewClient webViewClient = new WebViewClient() {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            HashMap<String, String> hashMap = new HashMap<>();
            String cookie = android.webkit.CookieManager.getInstance().getCookie("https://www.tiktok.com/");

            if (!TextUtils.isEmpty(cookie) && cookie.contains("sessionid")) {
                // Log.i("login_web", "cookies: " + cookie);
                String[] strings = cookie.split(";");
                for (String s : strings) {
                    String[] key_val = s.split("=");
                    hashMap.put(key_val[0].trim(), key_val[1].trim());
                }

                StringBuilder cookies = new StringBuilder();
                for (Map.Entry entry : hashMap.entrySet()) {
                    cookies.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
                }

                cookiesStr = cookies.toString();
                Hawk.put("cookies", cookiesStr);
                Hawk.put("cookies2", cookie);

                Log.i("login_web", "cookies: " + hashMap);
                loadUserProfile();
            }

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            //view.loadUrl(url);
            //Log.i("login_web", "onPageStarted: " + url);
        }
    };

    private void loadUserProfile() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://www.tiktok.com/trending")
                .addHeader("cookie", cookiesStr)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                // liveData.postValue(null);
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) {
                try {
                    String resp = response.body().string();
                    if (resp.contains("\"$user\":{\"uid\":")) {
                        String uid = getPartStr(resp, "\"uid\":\"", "\",");
                        String uniqueId = getPartStr(resp, "\"uniqueId\":\"", "\",");

                        Hawk.put(uiid, uid);
                        Hawk.put(userId, uniqueId);

                        Log.i("login_web", "data " + uid + " " + uniqueId);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void evaluateJsLike() {
        webView.evaluateJavascript(scriptSetLike, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
                if (!TextUtils.isEmpty(s) && !"null".equals(s) && !"\"\"".equals(s)) {

                }
            }
        });
    }

    private void evaluateJsListener() {
        webView.evaluateJavascript(scriptSetListener, s -> {

        });
    }


    static {
        webApiParams.put("device_id", "6817692846142866949");
        webApiParams.put("device_platform", "web_pc");
        webApiParams.put("app_name", "tiktok_web");
        webApiParams.put("browser_platform", "Win32");
        webApiParams.put("aid", "1988");
        webApiParams.put("type", "1");
        webApiParams.put("app_language", "ru");
        webApiParams.put("cookie_enabled", "true");
        webApiParams.put("browser_name", "Mozilla");
        webApiParams.put("browser_version", "5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.92 Safari/537.36");
        webApiParams.put("timezone_name", "Europe/Moscow");
        webApiParams.put("brand", "");
        webApiParams.put("screen_width", "1920");
        webApiParams.put("screen_height", "1080");
        webApiParams.put("page_referer", "https://www.tiktok.com/discover?lang=ru");
        webApiParams.put("verifyFp", "verify_kaphyktl_BPxMxjAx_ihYn_4Fju_9i1J_isiHy3tvj6dJ");
        webApiParams.put("_signature", "a7VGkAAgEBAdWd6-4Vx2IGu1R4AADV.");
    }

    private void like() {
        HttpUrl.Builder httpBuilder = HttpUrl.parse("https://api2.musical.ly/aweme/v1/commit/item/digg/?").newBuilder();

        MultipartBody.Builder requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        for (Map.Entry<String, String> entry : webApiParams.entrySet()) {
            httpBuilder.addQueryParameter(entry.getKey(), entry.getValue());
            requestBody.addFormDataPart(entry.getKey(), entry.getValue());
        }

        requestBody.addFormDataPart("aweme_id", "6831523353631706374");
        httpBuilder.addQueryParameter("aweme_id", "6831523353631706374");

        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .post(requestBody.build())
                .addHeader("sdk-version", "1")
                .addHeader("user-agent", USER_AGENT)
                .addHeader("cookie", cookiesStr)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) {
                /*try {
                    MediaDetailResponse detailsResponse = new Gson().fromJson(response.body().string(), MediaDetailResponse.class);
                    liveData.postValue(detailsResponse);
                } catch (Exception e) {
                    e.printStackTrace();

                }*/
            }
        });
    }

    private void getProfile() {
        HttpUrl.Builder httpBuilder = HttpUrl.parse("https://www.tiktok.com/@top5experiment").newBuilder();


        for (Map.Entry<String, String> entry : webApiParams.entrySet()) {
            //httpBuilder.addQueryParameter(entry.getKey(), entry.getValue());

        }

        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .addHeader("user-agent", USER_AGENT)
                .addHeader("cookie", cookiesStr)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) {
                /*try {
                    MediaDetailResponse detailsResponse = new Gson().fromJson(response.body().string(), MediaDetailResponse.class);
                    liveData.postValue(detailsResponse);
                } catch (Exception e) {
                    e.printStackTrace();

                }*/
            }
        });
    }
}
