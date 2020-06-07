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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.demo.tiktok_likes_new.R;
import com.demo.tiktok_likes_new.activity.TestActivity;
import com.demo.tiktok_likes_new.network.data.UserVideoResp;
import com.orhanobut.hawk.Hawk;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import im.delight.android.webview.AdvancedWebView;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.demo.tiktok_likes_new.activity.TestActivity.cookies_tag;
import static com.demo.tiktok_likes_new.network.Constants.REQ_URL;
import static com.demo.tiktok_likes_new.util.JsUtils.SCRIPT_SET_CLICK;
import static com.demo.tiktok_likes_new.util.JsUtils.SCRIPT_SET_LISTENER;

public class TwoTabFragment extends Fragment {

    private String cookiesStr2;
    private String lastItemId = "0";
    private String currentItemId = "0";
    private String TAG = TwoTabFragment.class.getSimpleName();

    private Vibrator v;
    Map<String, String> headers = new HashMap<>();
    private Views views;

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
    }

    private WebChromeClient webChromeClient = new WebChromeClient() {

        public boolean onConsoleMessage(ConsoleMessage cmsg) {
            Log.i(TAG, "onConsoleMessage: " + cmsg.message());

            if (cmsg.message() != null && cmsg.message().contains("api_req") && cmsg.message().contains("\"is_digg\":") && !lastItemId.equals(currentItemId)) {
                vibrate();
                Log.i(TAG, "Like confirm!");
                Log.i(TAG, "is_digg " + cmsg.message());
                lastItemId = item.getId();
                nextItem();
            } else if(cmsg.message() != null && cmsg.message().contains("log_pb")) {
                Log.i(TAG, "log_pb " + cmsg.message());
            }
            return true;

        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            /*if (newProgress > 90) {
                if (!lastId.equals(ids[likeCounter])) {
                    evaluateJsListener();
                    new Handler().postDelayed(() -> evaluateJsLike(), 900);
                    lastId = ids[likeCounter];
                    Log.i(TAG, "onPageFinished " + ids[likeCounter]);
                }
            }*/
        }
    };

    List<UserVideoResp.Item> items;
    UserVideoResp.Item item;
    Iterator<UserVideoResp.Item> iterator;

    public void setData(List<UserVideoResp.Item> items) {
        this.items = items;
        iterator = items.iterator();
        nextItem();
    }

    public void nextItem() {
        item = iterator.next();
        showPreview(item);
        loadItemByUrl(item.getUniqueId(), item.getId());
        isEval = false;
    }

    private void loadItemByUrl(String uniqueId, String itemId) {
        views.webView.loadUrl(REQ_URL + "@" + uniqueId + "/video/" + itemId);
    }

    private void onClickedSkip() {
        nextItem();
    }
    
    private void showPreview(UserVideoResp.Item item) {
        Glide.with(this)
                .load(item.getPhoto())
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
                        /*showProgress(false);
                        enableControls(true);
                        if (autoLike) onLikeClick();*/
                        return false;
                    }
                })
                .into(views.video_preview);

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
                currentItemId = item.getId();
                oldUrl = url;
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
    };

    private void evaluateJsPut() {
        views.webView.evaluateJavascript(SCRIPT_SET_CLICK, s -> {
        });
        views.setUpControlsForStatus(false);
    }

    private void evaluateJsListener() {
        Log.i(TAG, "evaluateJsListener: ");
        views.webView.evaluateJavascript(SCRIPT_SET_LISTENER, s -> {

        });
        views.setUpControlsForStatus(false);
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
        Button accept_btn;
        Button skip_btn;
        AdvancedWebView webView;
        ProgressBar progressBar2;

        Views(View rootView) {
            if (rootView != null) {
                video_preview = rootView.findViewById(R.id.video_cover);
                progressBar2 = rootView.findViewById(R.id.progressBar2);
                accept_btn = rootView.findViewById(R.id.accept_btn);
                skip_btn = rootView.findViewById(R.id.skip_btn);

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
            //webView.setVisibility(isLoadingStatus ? View.VISIBLE : View.GONE);
            progressBar2.setVisibility(isLoadingStatus ? View.GONE : View.VISIBLE);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.accept_btn:
                    evaluateJsPut();
                    return;
                case R.id.skip_btn:
                    onClickedSkip();
                    return;

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
