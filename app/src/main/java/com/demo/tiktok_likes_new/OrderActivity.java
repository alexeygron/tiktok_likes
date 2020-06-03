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

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

    }


}
