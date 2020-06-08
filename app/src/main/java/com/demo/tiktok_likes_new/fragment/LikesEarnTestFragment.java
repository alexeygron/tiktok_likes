package com.demo.tiktok_likes_new.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.demo.tiktok_likes_new.R;
import com.demo.tiktok_likes_new.activity.TestActivity;
import com.orhanobut.hawk.Hawk;

import java.util.HashMap;
import java.util.Map;

import im.delight.android.webview.AdvancedWebView;

import static com.demo.tiktok_likes_new.activity.TestActivity.cookies_tag;
import static com.demo.tiktok_likes_new.util.JsUtils.SCRIPT_SET_CLICK;
import static com.demo.tiktok_likes_new.util.JsUtils.SCRIPT_SET_LISTENER;

public class LikesEarnTestFragment extends Fragment {

    private AdvancedWebView webView;
    private String cookiesStr2;
    private String TAG = "login_web";

    private Vibrator v;

    String lastId = "";
    private String[] ids = new String[]{
            "6831459866024955141",
            "6831121440658558214",
            "6830773316081470726",
            "6830114386565238021",
            "6830082346386033926",
            "6829977196421991685",
            "6829645429152517382",
            "6827458043014319365",
            "6827446842150702341",
            "6827395140714253574"
    };

    private int likeCounter = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.likes_earn_test_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "LikesEarnTestFragment start ");
        cookiesStr2 = Hawk.get(cookies_tag, "");
        Map<String, String> headers = new HashMap<>();
        headers.put("cookie", cookiesStr2);
        v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        getActivity().getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        webView = view.findViewById(R.id.webView);
        //webView = new AdvancedWebView(getActivity());
        webView.getSettings().setUserAgentString(TestActivity.USER_AGENT);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        //webView.getSettings().setPluginState(WebSettings.PluginState.ON);

        //webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        //webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        //webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        /*webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }*/


        webView.setWebViewClient(webViewClient2);
        webView.setWebChromeClient(new WebChromeClient() {
            public boolean onConsoleMessage(ConsoleMessage cmsg) {
                Log.i(TAG, "onConsoleMessage: " + cmsg.message());

                /*if (cmsg.message().contains("api_req") && cmsg.message().contains("\"is_digg\":")) {
                    Log.i(TAG, "onConsoleMessage: " + cmsg.message());
                }*/

                if (cmsg.message() != null && cmsg.message().contains("api_req") && cmsg.message().contains("\"is_digg\":")) {
                    likeCounter = likeCounter + 1;
                    vibrate();

                    Log.i(TAG, "Like confirm!");
                    Log.i(TAG, "onConsoleMessage: " + cmsg.message());

                    if (likeCounter < ids.length) {
                        Log.i(TAG, "likeCounter " + likeCounter);
                        webView.loadUrl("https://www.tiktok.com/@klavacoca/video/" + ids[likeCounter], headers);
                    }
                }
                return true;

            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                if (newProgress > 90) {
                    if (!lastId.equals(ids[likeCounter])) {
                        evaluateJsListener();
                        new Handler().postDelayed(() -> evaluateJsLike(), 900);
                        lastId = ids[likeCounter];
                        Log.i(TAG, "onPageFinished " + ids[likeCounter]);
                    }
                }
            }
        });

        webView.loadUrl("https://www.tiktok.com/@klavacoca/video/" + ids[0], headers);
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
        public void onLoadResource(WebView view, String url) {
            //Log.i("login_web", "onLoadResource " +  url);

            /*if (url.contains("/api/commit/item/digg/")) {
                Log.i(TAG, "SET LIKE! ");
            }*/
            //view.
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //Log.i(TAG, "onPageFinished " );

            /*if (!lastId.equals(ids[likeCounter])) {
                evaluateJsListener();
                new Handler().postDelayed(() -> evaluateJsLike(), 600);
                lastId = ids[likeCounter];
                Log.i(TAG, "onPageFinished " + ids[likeCounter]);
            }*/


            /*if (!isEval) {
                isEval = true;
                //webView.load("javascript:" + SCRIPT_SET_LISTENER);
                //new Handler().postDelayed(() -> webView.loadUrl("javascript:" + SCRIPT_SET_LISTENER), 1000);
                new Handler().postDelayed(() -> evaluateJsListener(), 0);
            }*/
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }
    };

    private void evaluateJsLike() {
        webView.evaluateJavascript(SCRIPT_SET_CLICK, s -> {
            if (!TextUtils.isEmpty(s) && !"null".equals(s) && !"\"\"".equals(s)) {

            }
        });
    }

    private void evaluateJsListener() {
        webView.evaluateJavascript(SCRIPT_SET_LISTENER, s -> {

        });
    }

    private void vibrate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(200);
        }
    }
}
