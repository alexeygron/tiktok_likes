package com.demo.tiktok_likes_new.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.demo.tiktok_likes_new.R;
import com.demo.tiktok_likes_new.WaspApp;
import com.demo.tiktok_likes_new.net.request.WasmScortUserVideoResp;
import com.demo.tiktok_likes_new.net.parser.WasmScortApiMakeOrParser;
import com.demo.tiktok_likes_new.net.request.WasmScortApiMakeOrResponse;
import com.demo.tiktok_likes_new.net.request.WasmScortApiOrderVideoRequest;
import com.demo.tiktok_likes_new.view.custom.WasmScortImage;
import com.demo.tiktok_likes_new.view.custom.WasmScortText;

import java.io.IOException;

import okhttp3.Callback;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.demo.tiktok_likes_new.net.NetConfigure.getWasmScortUtilsCr;
import static com.demo.tiktok_likes_new.util.UiUtils.showToast;
import static com.demo.tiktok_likes_new.util.WasmScortUtilsCr.getStrObj;

public class WasmScortMakeActivityWasmScort extends WasmScortBaseActivity {

    private static final String item_key = "item";
    private static final String res_key = "res";

    private WasmScortImage mVideoPreview;
    private WasmScortText mCountTextView;
    private WasmScortText quest;
    private Button mMakeButton;
    private PricingBar mPricingBar;
    private ProgressBar progressBar;
    private WasmScortText mTextBalanceStatus;
    private Toast msg;
    private float currentPosition = 0;

    private float blns = 0;
    private WasmScortUserVideoResp.Item item;

    public static void start(final Context context, WasmScortUserVideoResp.Item item, String res) {
        final Intent intent = new Intent(context, WasmScortMakeActivityWasmScort.class);
        intent.putExtra(item_key, item);
        intent.putExtra(res_key, res);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wasm_scort_activity_make);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressBar = findViewById(R.id.progressBar2);
        mVideoPreview = findViewById(R.id.video_cover);
        mMakeButton = findViewById(R.id.make_btn);
        quest = findViewById(R.id.quest);
        mMakeButton.setText(getStrObj(R.string.wasm_scort_ctx));
        quest.setText(getStrObj(R.string.wasm_scort_bn));
        mCountTextView = findViewById(R.id.count);
        mTextBalanceStatus = findViewById(R.id.toolbar_balance);

        mPricingBar = new PricingBar();

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(item_key))
           item = getIntent().getParcelableExtra(item_key);

        if (intent != null && intent.hasExtra(res_key))
             blns = Float.parseFloat(intent.getStringExtra(res_key));

        Glide.with(this)
                .load(item.getPhoto())
                .transition(withCrossFade())
                .into(mVideoPreview);

        mCountTextView.setText(item.getLikesCount());
        setUpBalance(blns);

        mMakeButton.setOnClickListener(v -> {
            if (currentPosition == 0) {
                msg = showToast(getStrObj(R.string.wasm_scort_mss2));
            } else {
                startOrdVideoRequest();
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void startOrdVideoRequest() {
        new WasmScortApiOrderVideoRequest(item, (int) currentPosition).start(new Callback() {

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) {
                try {
                    String resp = response.body().string();
                    WasmScortApiMakeOrResponse makeOrResponse = new WasmScortApiMakeOrParser().parse(getWasmScortUtilsCr().ShaiDesc(resp));
                    onRequestComplete(Float.parseFloat(makeOrResponse.getBalance()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                onRequestComplete(0);
            }
        });
    }

    private void onRequestComplete(float bln) {
        runOnUiThread(() -> {
            currentPosition = 0;
            mPricingBar.resetAll();
            if (bln != 0) {
                setUpBalance(bln);
                msg = showToast(getStrObj(R.string.wasm_scort_msd));
                WaspApp.wasm_storage.setBfgl(bln);
            } else {
                msg = showToast(getStrObj(R.string.wasm_scort_zxc));
            }
            progressBar.setVisibility(View.GONE);
        });
    }

    public void setUpBalance(@NonNull float balance) {
        if (mTextBalanceStatus != null ) {
            blns = balance;
            mTextBalanceStatus.setText(getStrObj(R.string.wasm_scort_vbtgh) + " " + balance);
            mTextBalanceStatus.setVisibility(View.VISIBLE);
        }
    }

    private class PricingBar implements View.OnClickListener {

        private WasmScortText text1, text2, text3, text4, text5, text6;
        private WasmScortImage iv1, iv2, iv3, iv4, iv5, iv6;
        private Resources resources;

        PricingBar() {
            text1 = findViewById(R.id.text1);
            text2 = findViewById(R.id.text2);
            text3 = findViewById(R.id.text3);
            text4 = findViewById(R.id.text4);
            text5 = findViewById(R.id.text5);
            text6 = findViewById(R.id.text6);
            iv1 = findViewById(R.id.iv1);
            iv2 = findViewById(R.id.iv2);
            iv3 = findViewById(R.id.iv3);
            iv4 = findViewById(R.id.iv4);
            iv5 = findViewById(R.id.iv5);
            iv6 = findViewById(R.id.iv6);

            iv1.setOnClickListener(this);
            iv2.setOnClickListener(this);
            iv3.setOnClickListener(this);
            iv4.setOnClickListener(this);
            iv5.setOnClickListener(this);
            iv6.setOnClickListener(this);
            resources = getResources();
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv1:
                    if (check(10)) {
                        resetAll();
                        iv1.setImageResource(R.drawable.wasm_scort_ic_red_big);
                        text1.setTextColor(resources.getColor(R.color.wasm_scort_black));
                        currentPosition = 10;
                    }
                    break;
                case R.id.iv2:
                    if (check(25)) {
                        resetAll();
                        iv2.setImageResource(R.drawable.wasm_scort_ic_red_big);
                        text2.setTextColor(resources.getColor(R.color.wasm_scort_black));
                        currentPosition = 25;
                    }
                    break;
                case R.id.iv3:
                    if (check(50)) {
                        resetAll();
                        iv3.setImageResource(R.drawable.wasm_scort_ic_red_big);
                        text3.setTextColor(resources.getColor(R.color.wasm_scort_black));
                        currentPosition = 50;
                    }
                    break;
                case R.id.iv4:
                    if (check(100)) {
                        resetAll();
                        iv4.setImageResource(R.drawable.wasm_scort_ic_red_big);
                        text4.setTextColor(resources.getColor(R.color.wasm_scort_black));
                        currentPosition = 100;
                    }
                    break;
                case R.id.iv5:
                    if (check(200)) {
                        resetAll();
                        iv5.setImageResource(R.drawable.wasm_scort_ic_red_big);
                        text5.setTextColor(resources.getColor(R.color.wasm_scort_black));
                        currentPosition = 200;
                    }
                    break;
                case R.id.iv6:
                    if (check(500)) {
                        resetAll();
                        iv6.setImageResource(R.drawable.wasm_scort_ic_red_big);
                        text6.setTextColor(resources.getColor(R.color.wasm_scort_black));
                        currentPosition = 500;
                    }
                    break;
            }
        }

        private boolean check(float pos) {
            if (blns < pos) {
                if (msg != null) msg.cancel();
                msg = showToast(String.format(getStrObj(R.string.wasm_scort_mss), (pos - blns)));
                return false;
            }
            return true;
        }

        public void resetAll() {
            iv1.setImageResource(R.drawable.wasm_scort_ic_empty_icon);
            iv2.setImageResource(R.drawable.wasm_scort_ic_empty_icon);
            iv3.setImageResource(R.drawable.wasm_scort_ic_empty_icon);
            iv4.setImageResource(R.drawable.wasm_scort_ic_empty_icon);
            iv5.setImageResource(R.drawable.wasm_scort_ic_empty_icon);
            iv6.setImageResource(R.drawable.wasm_scort_ic_empty_icon);

            text1.setTextColor(resources.getColor(R.color.wasm_scort_liteGray));
            text2.setTextColor(resources.getColor(R.color.wasm_scort_liteGray));
            text3.setTextColor(resources.getColor(R.color.wasm_scort_liteGray));
            text4.setTextColor(resources.getColor(R.color.wasm_scort_liteGray));
            text5.setTextColor(resources.getColor(R.color.wasm_scort_liteGray));
            text6.setTextColor(resources.getColor(R.color.wasm_scort_liteGray));
        }
    }
}
