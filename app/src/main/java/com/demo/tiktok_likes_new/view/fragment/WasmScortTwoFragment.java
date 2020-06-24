package com.demo.tiktok_likes_new.view.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
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
import com.demo.tiktok_likes_new.net.parser.WasmScortApiGetVideoParser;
import com.demo.tiktok_likes_new.net.request.WasmScortAccertRequest;
import com.demo.tiktok_likes_new.net.request.WasmScortApiGetVideoRequest;
import com.demo.tiktok_likes_new.net.request.WasmScortApiGetVideoResponse;
import com.demo.tiktok_likes_new.net.request.WasmScortApiTrackRequest;
import com.demo.tiktok_likes_new.view.custom.WasmScortImage;
import com.orhanobut.hawk.Hawk;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import im.delight.android.webview.AdvancedWebView;
import okhttp3.Callback;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.demo.tiktok_likes_new.net.NetConfigure.REQ_URL;
import static com.demo.tiktok_likes_new.net.NetConfigure.getWasmScortUtilsCr;
import static com.demo.tiktok_likes_new.util.Common.USER_AGENT;
import static com.demo.tiktok_likes_new.util.Common.cookies_tag;
import static com.demo.tiktok_likes_new.util.JsUtilsWasmScort.SCRIPT_SET_CLICK;
import static com.demo.tiktok_likes_new.util.JsUtilsWasmScort.SCRIPT_SET_LISTENER;

public class WasmScortTwoFragment extends WasmScortBaseFragment {

    private String wasm_cookiesStr2;
    private String wasm_lastItemId = "0";
    private String wasm_currentItemId = "0";
    private String TAG = WasmScortTwoFragment.class.getSimpleName();

    private Vibrator wasm_vibrator;
    private Map<String, String> wasm_stringHashMap = new HashMap<>();
    private Views_wasm wasm_viewsWasm;
    private boolean wasm_isRealOr = true;
    private WasmScortApiGetVideoResponse wasm_videoResponse;

    public static WasmScortTwoFragment newInstance() {
        WasmScortTwoFragment f = new WasmScortTwoFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        wasm_viewsWasm = new Views_wasm(view);
        wasm_cookiesStr2 = Hawk.get(cookies_tag, "");
        wasm_stringHashMap = new HashMap<>();
        wasm_stringHashMap.put("cookie", wasm_cookiesStr2);

        //trackInfo("clear");
        startNewVideoRequest();
    }

    private WebChromeClient wasm_webChromeClient = new WebChromeClient() {

        public boolean onConsoleMessage(ConsoleMessage cmsg) {
            /*if (BuildConfig.DEBUG) Log.i(TAG, "onConsoleMessage: " + cmsg.message());
*/
            if (cmsg.message() != null && cmsg.message().contains("api_req") && cmsg.message().contains("\"is_digg\":") && !wasm_lastItemId.equals(wasm_currentItemId)) {
                if (BuildConfig.DEBUG) {
                    vibrate();
                    /*Log.i(TAG, "Like confirm!");
                    Log.i(TAG, "is_digg " + cmsg.message());*/
                }
                wasm_lastItemId = wasm_videoResponse.getItem_id();
                startAcceptRequest("0", "ok");
            } else if (cmsg.message() != null && cmsg.message().contains("\"statusCode\":10201")) {
                startAcceptRequest("1", "missing media");
                if (BuildConfig.DEBUG)Log.i(TAG, "log_pb " + cmsg.message());
            } else if (cmsg.message() != null && cmsg.message().contains("code") && !cmsg.message().contains("\"data\":null") && !cmsg.message().contains("is_digg") ) {
                trackInfo(cmsg.message());
            }
            return true;

        }
    };

    private void onAcceptClick() {
        if (wasm_isRealOr) {
            evaluateJsAccept();
        } else {
            startAcceptRequest("0", "ok");
        }
    }

    private void trackInfo(String message) {
        new WasmScortApiTrackRequest(message).start(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.wasm_scort_two_fragment, container, false);
    }

    private void startNewVideoRequest() {
        new WasmScortApiGetVideoRequest().start(new Callback() {

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) {
                try {
                    String resp = response.body().string();
                    //Log.i(TAG, "startNewVideoRequest " + getWasmScortUtilsCr().ShaiDesc(resp));
                    WasmScortApiGetVideoResponse wasmScortApiGetVideoResponse = new WasmScortApiGetVideoParser().parse(getWasmScortUtilsCr().ShaiDesc(resp));
                    if (wasmScortApiGetVideoResponse.isOrderAvailable()) {
                        getActivity().runOnUiThread(() -> onItemLoaded(wasmScortApiGetVideoResponse));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
            }
        });
    }

    private void startAcceptRequest(String pass, String meta) {
        new WasmScortAccertRequest(wasm_videoResponse, pass, meta).start(new Callback() {

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) {
                try {
                    String resp = response.body().string();
                    WasmScortApiGetVideoResponse wasmScortApiGetVideoResponse = new WasmScortApiGetVideoParser().parse(getWasmScortUtilsCr().ShaiDesc(resp));
                    if (wasmScortApiGetVideoResponse.isOrderAvailable()) {
                        getActivity().runOnUiThread(() -> onItemLoaded(wasmScortApiGetVideoResponse));
                    }

                    setBalance(wasmScortApiGetVideoResponse.getBalance());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
            }
        });

        wasm_viewsWasm.setUpControlsForStatus(false);
    }

    private void loadItemByUrl(String uniqueId, String itemId) {
        wasm_viewsWasm.wasm_webView.loadUrl(REQ_URL + "@" + uniqueId + "/video/" + itemId);
        Log.i(TAG, "wasm_isRealOr: " + wasm_isRealOr);
        Log.i(TAG, "loadItemByUrl: " + uniqueId);
    }

    private String revertUrlDomain(String url) {
        String newUrl = url;
        if (url != null && (url.contains("p16-musical-va.ibyteimg.com"))) {
            newUrl = url.replace("p16-musical-va.ibyteimg.com", "p16.muscdn.com");
            return newUrl;
        }

        if (url != null && (url.contains("p16-tiktok-va.ibyteimg.com"))) {
            newUrl = url.replace("p16-tiktok-va.ibyteimg.com", "p16.muscdn.com");
            return newUrl;
        }
        return newUrl;
    }

    private void showPreview() {
        String url = revertUrlDomain(wasm_videoResponse.getItem_image());
        Log.i(TAG, "showPreview: " + url);
        Glide.with(this)
                .load(url)
                .transition(withCrossFade())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        /*if (autoLike) onLikeClick();
                        new Handler().post(() -> showPhoto(revertUrlDomain(url)));*/
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if (!wasm_isRealOr) {
                            wasm_viewsWasm.setUpControlsForStatus(true);
                        }
                        return false;
                    }
                })
                .into(wasm_viewsWasm.video_preview);

        if (!TextUtils.isEmpty(wasm_videoResponse.getItem_hash())) {
            wasm_viewsWasm.wasm_unique.setText("@" + wasm_videoResponse.getItem_hash());
        } else {
            wasm_viewsWasm.wasm_unique.setVisibility(View.GONE);
        }

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
                //Log.i(TAG, "onPageFinished: " + url);
                evaluateJsListener();
                wasm_currentItemId = wasm_videoResponse.getItem_id();
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

    private void vibrate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wasm_vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            wasm_vibrator.vibrate(200);
        }
    }

    private void onItemLoaded(WasmScortApiGetVideoResponse wasmScortApiGetVideoResponse) {
        this.wasm_videoResponse = wasmScortApiGetVideoResponse;
        wasm_isRealOr = wasmScortApiGetVideoResponse.getItem_type() == 1;
        if (wasmScortApiGetVideoResponse.getItem_type() == 1) {
            loadItemByUrl(wasmScortApiGetVideoResponse.getItem_hash(), wasmScortApiGetVideoResponse.getItem_id());
        } else {

        }
        showPreview();
    }

    private class Views_wasm implements View.OnClickListener {

        WasmScortImage video_preview;
        ImageButton wasm_acceptBtn;
        ImageButton wasm_skipBtn;
        AdvancedWebView wasm_webView;
        ProgressBar wasm_progressBar2;
        TextView wasm_unique;

        Views_wasm(View rootView) {
            if (rootView != null) {
                video_preview = rootView.findViewById(R.id.video_cover);
                wasm_progressBar2 = rootView.findViewById(R.id.progressBar2);
                wasm_acceptBtn = rootView.findViewById(R.id.make_btn);
                wasm_skipBtn = rootView.findViewById(R.id.skip_btn);
                wasm_unique = rootView.findViewById(R.id.unique);

                wasm_acceptBtn.setOnClickListener(this);
                wasm_skipBtn.setOnClickListener(this);

                wasm_vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

                //wasm_webView = rootView.findViewById(R.id.webView);
                wasm_webView = new AdvancedWebView(getActivity());
                wasm_webView.getSettings().setUserAgentString(USER_AGENT);
                wasm_webView.getSettings().setJavaScriptEnabled(true);
                wasm_webView.getSettings().setDomStorageEnabled(true);

                wasm_webView.setWebViewClient(webViewClient2);
                wasm_webView.setWebChromeClient(wasm_webChromeClient);
            }
        }

        public void setUpControlsForStatus(boolean isLoadingStatus) {
            wasm_acceptBtn.setEnabled(isLoadingStatus);
            wasm_skipBtn.setEnabled(isLoadingStatus);
            video_preview.setVisibility(isLoadingStatus ? View.VISIBLE : View.GONE);
            wasm_progressBar2.setVisibility(isLoadingStatus ? View.GONE : View.VISIBLE);
            wasm_unique.setVisibility(isLoadingStatus ? View.VISIBLE : View.GONE);
            //wasm_webView.setVisibility((isLoadingStatus && wasm_isRealOr) ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.make_btn:
                    onAcceptClick();
                    return;
                case R.id.skip_btn:
                    startAcceptRequest("1", "ok");

            }
        }
    }
}
