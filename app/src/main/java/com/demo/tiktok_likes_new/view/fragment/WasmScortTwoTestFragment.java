package com.demo.tiktok_likes_new.view.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.demo.tiktok_likes_new.BuildConfig;
import com.demo.tiktok_likes_new.R;
import com.demo.tiktok_likes_new.net.request.WasmScortApiGetVideoResponse;
import com.demo.tiktok_likes_new.net.request.WasmScortUserVideoResp;
import com.demo.tiktok_likes_new.view.custom.WasmScortImage;
import com.orhanobut.hawk.Hawk;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import im.delight.android.webview.AdvancedWebView;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.demo.tiktok_likes_new.net.NetConfigure.REQ_URL;
import static com.demo.tiktok_likes_new.util.Common.USER_AGENT;
import static com.demo.tiktok_likes_new.util.Common.cookies_tag;
import static com.demo.tiktok_likes_new.util.JsUtilsWasmScort.SCRIPT_SET_CLICK;
import static com.demo.tiktok_likes_new.util.JsUtilsWasmScort.SCRIPT_SET_LISTENER;

public class WasmScortTwoTestFragment extends WasmScortBaseFragment {

    private String wasm_cookiesStr2;
    private String wasm_lastItemId = "0";
    private String wasm_currentItemId = "0";
    private String TAG = WasmScortTwoTestFragment.class.getSimpleName();

    private Vibrator wasm_vibrator;
    private Map<String, String> wasm_stringHashMap = new HashMap<>();
    private Views_wasm wasm_viewsWasm;
    private boolean wasm_isRealOr = true;
    private boolean autoLike = false;

    private static WasmScortTwoTestFragment frag;

    private WasmScortUserVideoResp.Item currentItem;
    private Iterator<WasmScortUserVideoResp.Item> iterator;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        wasm_viewsWasm = new Views_wasm(view);
        wasm_cookiesStr2 = Hawk.get(cookies_tag, "");
        wasm_stringHashMap = new HashMap<>();
        wasm_stringHashMap.put("cookie", wasm_cookiesStr2);
    }

    private void setNewItem() {
        if (iterator.hasNext()) {
            currentItem = iterator.next();
            loadItemByUrl(currentItem.getUniqueId(), currentItem.getId());
        }
    }

    private WebChromeClient wasm_webChromeClient = new WebChromeClient() {

        public boolean onConsoleMessage(ConsoleMessage cmsg) {
            if (BuildConfig.DEBUG) Log.i(TAG, "onConsoleMessage: " + cmsg.message());
            if (cmsg.message() != null && cmsg.message().contains("api_req") && cmsg.message().contains("\"is_digg\":") && !wasm_lastItemId.equals(wasm_currentItemId)) {
                if (BuildConfig.DEBUG) {
                    vibrate();
                    //Log.i(TAG, "Like confirm!");
                    Log.i(TAG, "is_digg " + cmsg.message());
                }
                wasm_lastItemId = currentItem.getId();

                setNewItem();
            } else if (cmsg.message() != null && cmsg.message().contains("\"statusCode\":10201")) {
                if (BuildConfig.DEBUG) Log.i(TAG, "is_digg " + cmsg.message());
            }
            return true;

        }
    };

    private void onAcceptClick() {
        evaluateJsAccept();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.wasm_scort_two_test_fragment, container, false);
    }


    private void loadItemByUrl(String uniqueId, String itemId) {
        wasm_viewsWasm.wasm_webView.loadUrl(REQ_URL + "@" + uniqueId + "/video/" + itemId + 1);
        Log.i(TAG, "wasm_isRealOr: " + wasm_isRealOr);
        Log.i(TAG, "loadItemByUrl: " + uniqueId);
    }

    boolean isLiked = false;
    boolean isEval = false;
    String oldUrl = "";

    private WebViewClient webViewClient2 = new WebViewClient() {

        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return super.shouldInterceptRequest(view, request);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            //Log.i("login_web", "onLoadResource " +  url);

            /*if (url.contains("/api/commit/item/digg/")) {
                Log.i(TAG, "SET LIKE! ");
            }*/
            //wasm_viewsWasm.
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //Log.i(TAG, "onPageFinished " );

            wasm_viewsWasm.setUpControlsForStatus(true);
            if (!oldUrl.equals(url)) {
                Log.i(TAG, "onPageFinished: " + url);
                evaluateJsListener();
                new Handler().postDelayed(() -> evaluateJsAccept(), 1000);
                wasm_currentItemId = currentItem.getId();
                oldUrl = url;
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
    };

    private void evaluateJsAccept() {
        wasm_viewsWasm.wasm_webView.evaluateJavascript(SCRIPT_SET_CLICK, s -> {
        });
        wasm_viewsWasm.setUpControlsForStatus(false);
    }

    private void evaluateJsListener() {
        wasm_viewsWasm.wasm_webView.evaluateJavascript(SCRIPT_SET_LISTENER, s -> {

        });
    }

    static WasmScortTwoTestFragment getFrag() {
        return frag;
    }
    void setGetItems(List<WasmScortUserVideoResp.Item> wasm_getItems) {
        iterator = wasm_getItems.iterator();
        setNewItem();
        currentItem.setId(currentItem.getId() + 1);
    }

    public static WasmScortTwoTestFragment newInstance() {
        WasmScortTwoTestFragment f = new WasmScortTwoTestFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        frag = f;
        return f;
    }


    private void vibrate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wasm_vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            wasm_vibrator.vibrate(200);
        }
    }

    private class Views_wasm implements View.OnClickListener {

        WasmScortImage video_preview;
        ImageButton wasm_acceptBtn;
        ImageButton wasm_skipBtn;
        AdvancedWebView wasm_webView;
        TextView wasm_unique;

        Views_wasm(View rootView) {
            if (rootView != null) {
                video_preview = rootView.findViewById(R.id.video_cover);
                wasm_acceptBtn = rootView.findViewById(R.id.make_btn);
                wasm_skipBtn = rootView.findViewById(R.id.skip_btn);
                wasm_unique = rootView.findViewById(R.id.unique);

                wasm_acceptBtn.setOnClickListener(this);
                wasm_skipBtn.setOnClickListener(this);

                wasm_vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

                wasm_webView = rootView.findViewById(R.id.webView);
                wasm_webView.getSettings().setUserAgentString(USER_AGENT);
                wasm_webView.getSettings().setJavaScriptEnabled(true);
                wasm_webView.getSettings().setDomStorageEnabled(true);

                wasm_webView.setWebViewClient(webViewClient2);
                wasm_webView.setWebChromeClient(wasm_webChromeClient);
            }
        }

        public void setUpControlsForStatus(boolean isLoadingStatus) {
            //wasm_acceptBtn.setEnabled(isLoadingStatus);
            //wasm_skipBtn.setEnabled(isLoadingStatus);
           // video_preview.setVisibility(isLoadingStatus ? View.VISIBLE : View.GONE);
           // wasm_progressBar2.setVisibility(isLoadingStatus ? View.GONE : View.VISIBLE);
           // wasm_unique.setVisibility(isLoadingStatus ? View.VISIBLE : View.GONE);
            //wasm_webView.setVisibility((isLoadingStatus && wasm_isRealOr) ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.make_btn:
                    onAcceptClick();
                    return;
                case R.id.skip_btn:
                    //startAcceptRequest("1", "ok");

            }
        }
    }
}
