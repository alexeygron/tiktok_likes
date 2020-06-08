package com.demo.tiktok_likes_new.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.demo.tiktok_likes_new.R;
import com.demo.tiktok_likes_new.activity.MainActivity;
import com.demo.tiktok_likes_new.activity.TestActivity;
import com.demo.tiktok_likes_new.network.parser.ApiGetVideoParser;
import com.demo.tiktok_likes_new.network.request.ApiAccertRequest;
import com.demo.tiktok_likes_new.network.request.ApiGetVideoRequest;
import com.demo.tiktok_likes_new.network.request.ApiGetVideoResponse;
import com.orhanobut.hawk.Hawk;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import im.delight.android.webview.AdvancedWebView;
import okhttp3.Callback;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.demo.tiktok_likes_new.activity.TestActivity.cookies_tag;
import static com.demo.tiktok_likes_new.network.Constants.REQ_URL;
import static com.demo.tiktok_likes_new.network.Constants.getAbaBUtilsCrypt;
import static com.demo.tiktok_likes_new.util.JsUtils.SCRIPT_SET_CLICK;
import static com.demo.tiktok_likes_new.util.JsUtils.SCRIPT_SET_LISTENER;

public class TwoTabFragment extends BaseAbstractFragment {

    private String cookiesStr2;
    private String lastItemId = "0";
    private String currentItemId = "0";
    private String TAG = TwoTabFragment.class.getSimpleName();

    private Vibrator v;
    private Map<String, String> headers = new HashMap<>();
    private Views views;
    private boolean isRealOr = true;
    private ApiGetVideoResponse videoResponseItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.two_tab_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        views = new Views(view);
        cookiesStr2 = Hawk.get(cookies_tag, "");
        headers = new HashMap<>();
        headers.put("cookie", cookiesStr2);

        startNewVideoRequest();
    }

    private WebChromeClient webChromeClient = new WebChromeClient() {

        public boolean onConsoleMessage(ConsoleMessage cmsg) {
            Log.i(TAG, "onConsoleMessage: " + cmsg.message());

            if (cmsg.message() != null && cmsg.message().contains("api_req") && cmsg.message().contains("\"is_digg\":") && !lastItemId.equals(currentItemId)) {
                vibrate();
                Log.i(TAG, "Like confirm!");
                Log.i(TAG, "is_digg " + cmsg.message());
                lastItemId = videoResponseItem.getItem_id();
                startAcceptRequest("0", "ok");
            } else if(cmsg.message() != null && cmsg.message().contains("log_pb")) {
                Log.i(TAG, "log_pb " + cmsg.message());
            }
            return true;

        }
    };

    private void onAcceptClick() {
        if (isRealOr) {
            evaluateJsAccept();
        } else {
            startAcceptRequest("0", "ok");
        }
    }

    private void startNewVideoRequest() {
        new ApiGetVideoRequest().start(new Callback() {

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) {
                try {
                    String resp = response.body().string();
                    ApiGetVideoResponse apiGetVideoResponse = new ApiGetVideoParser().parse(getAbaBUtilsCrypt().AbaBDecryptString(resp));
                    if (apiGetVideoResponse.isOrderAvailable()) {
                        getActivity().runOnUiThread(() -> onItemLoaded(apiGetVideoResponse));
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
        new ApiAccertRequest(videoResponseItem, pass, meta).start(new Callback() {

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) {
                try {
                    String resp = response.body().string();
                    ApiGetVideoResponse apiGetVideoResponse = new ApiGetVideoParser().parse(getAbaBUtilsCrypt().AbaBDecryptString(resp));
                    if (apiGetVideoResponse.isOrderAvailable()) {
                        getActivity().runOnUiThread(() -> onItemLoaded(apiGetVideoResponse));
                    }

                    setBalance(apiGetVideoResponse.getBalance());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
            }
        });

        views.setUpControlsForStatus(false);
    }

    private void onItemLoaded(ApiGetVideoResponse apiGetVideoResponse) {
        this.videoResponseItem = apiGetVideoResponse;
        isRealOr = apiGetVideoResponse.getItem_type() == 1;
        if (apiGetVideoResponse.getItem_type() == 1) {
            loadItemByUrl(apiGetVideoResponse.getItem_hash(), apiGetVideoResponse.getItem_id());
        } else {

        }
        showPreview();
    }

    private void loadItemByUrl(String uniqueId, String itemId) {
        views.webView.loadUrl(REQ_URL + "@" + uniqueId + "/video/" + itemId);
    }

    private String revertUrlDomain(String url) {
        String newUrl = url;
        if (url != null && url.contains("p16-musical-va.ibyteimg.com")) {
            newUrl = url.replace("p16-musical-va.ibyteimg.com", "p16.muscdn.com");
        }
        return newUrl;
    }

    private void showPreview() {
        String url = revertUrlDomain(videoResponseItem.getItem_image());
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
                        if (!isRealOr) {
                            views.setUpControlsForStatus(true);
                        }
                        return false;
                    }
                })
                .into(views.video_preview);

        views.unique.setText("@" + videoResponseItem.getItem_hash());

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
            //views.
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //Log.i(TAG, "onPageFinished " );

            views.setUpControlsForStatus(true);

            /*if (!lastId.equals(ids[likeCounter])) {
                evaluateJsListener();
                new Handler().postDelayed(() -> evaluateJsLike(), 600);
                lastId = ids[likeCounter];
                Log.i(TAG, "onPageFinished " + ids[likeCounter]);
            }*/


            if (!oldUrl.equals(url)) {
                Log.i(TAG, "onPageFinished: " + url);
                evaluateJsListener();
                currentItemId = videoResponseItem.getItem_id();
                oldUrl = url;
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
    };

    private void evaluateJsAccept() {
        views.webView.evaluateJavascript(SCRIPT_SET_CLICK, s -> {
        });
        views.setUpControlsForStatus(false);
    }

    private void evaluateJsListener() {
        Log.i(TAG, "evaluateJsListener: ");
        views.webView.evaluateJavascript(SCRIPT_SET_LISTENER, s -> {

        });
    }

    private void vibrate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(200);
        }
    }

    private class Views implements View.OnClickListener {

        ImageView video_preview;
        ImageButton accept_btn;
        ImageButton skip_btn;
        AdvancedWebView webView;
        ProgressBar progressBar2;
        TextView unique;

        Views(View rootView) {
            if (rootView != null) {
                video_preview = rootView.findViewById(R.id.video_cover);
                progressBar2 = rootView.findViewById(R.id.progressBar2);
                accept_btn = rootView.findViewById(R.id.accept_btn);
                skip_btn = rootView.findViewById(R.id.skip_btn);
                unique = rootView.findViewById(R.id.unique);

                accept_btn.setOnClickListener(this);
                skip_btn.setOnClickListener(this);

                v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

                webView = rootView.findViewById(R.id.webView);
                webView.getSettings().setUserAgentString(TestActivity.USER_AGENT);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setDomStorageEnabled(true);

                webView.setWebViewClient(webViewClient2);
                webView.setWebChromeClient(webChromeClient);
            }
        }

        public void setUpControlsForStatus(boolean isLoadingStatus) {
            accept_btn.setEnabled(isLoadingStatus);
            skip_btn.setEnabled(isLoadingStatus);
            video_preview.setVisibility(isLoadingStatus ? View.VISIBLE : View.GONE);
            progressBar2.setVisibility(isLoadingStatus ? View.GONE : View.VISIBLE);
            unique.setVisibility(isLoadingStatus ? View.VISIBLE : View.GONE);
            //webView.setVisibility((isLoadingStatus && isRealOr) ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.accept_btn:
                    onAcceptClick();
                    return;
                case R.id.skip_btn:
                    startAcceptRequest("1", "ok");

            }
        }
    }

    public static TwoTabFragment newInstance() {
        TwoTabFragment f = new TwoTabFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }
}
