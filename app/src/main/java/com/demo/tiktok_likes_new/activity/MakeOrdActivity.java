package com.demo.tiktok_likes_new.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.demo.tiktok_likes_new.App;
import com.demo.tiktok_likes_new.R;
import com.demo.tiktok_likes_new.network.data.UserVideoResp;
import com.demo.tiktok_likes_new.network.parser.ApiMakeOrParser;
import com.demo.tiktok_likes_new.network.parser.StartAppParser;
import com.demo.tiktok_likes_new.network.request.ApiMakeOrResponse;
import com.demo.tiktok_likes_new.network.request.ApiOneStepRequest;
import com.demo.tiktok_likes_new.network.request.ApiOneStepResponse;
import com.demo.tiktok_likes_new.network.request.ApiOrderVideoRequest;

import java.io.IOException;

import okhttp3.Callback;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.demo.tiktok_likes_new.network.Constants.getAbaBUtilsCrypt;
import static com.demo.tiktok_likes_new.util.UiUtils.showToast;

public class MakeOrdActivity extends BaseAbstractActivity {

    private static final String item_key = "item";
    private static final String res_key = "res";

    private ImageView mVideoPreview;
    private TextView mCountTextView;
    private Button mMakeButton;
    private PricingBar mPricingBar;
    private ProgressBar progressBar;
    private TextView mTextBalanceStatus;
    private Toast msg;
    private float currentPosition = 0;

    private float blns = 0;
    private UserVideoResp.Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_ord);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressBar = findViewById(R.id.progressBar2);
        mVideoPreview = findViewById(R.id.video_cover);
        mMakeButton = findViewById(R.id.make_btn);
        mCountTextView = findViewById(R.id.count);
        mTextBalanceStatus = findViewById(R.id.toolbar_balance);

        mPricingBar = new PricingBar();

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(item_key))
           item = getIntent().getParcelableExtra(item_key);

        if (intent != null && intent.hasExtra(res_key))
             blns = Float.parseFloat(intent.getStringExtra(res_key));

       /* Log.i(TAG, "onCreate: " +  item.toString());
        Log.i(TAG, "onCreate: " +  blns);*/

        Glide.with(this)
                .load(item.getPhoto())
                .transition(withCrossFade())
                .into(mVideoPreview);

        mCountTextView.setText(item.getLikesCount());
        setUpBalance(blns);

        mMakeButton.setOnClickListener(v -> {
            if (currentPosition == 0) {
                msg = showToast(getString(R.string.msg2));
            } else {
                startOrdVideoRequest();
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }
    
    private void startOrdVideoRequest() {
        new ApiOrderVideoRequest(item, (int) currentPosition).start(new Callback() {

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) {
                try {
                    String resp = response.body().string();
                    ApiMakeOrResponse makeOrResponse = new ApiMakeOrParser().parse(getAbaBUtilsCrypt().AbaBDecryptString(resp));
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
                msg = showToast(getString(R.string.msg3));
                App.initDataStorage.setBfgl(bln);
            } else {
                msg = showToast(getString(R.string.msg4));
            }
            progressBar.setVisibility(View.GONE);
        });
    }

    public void setUpBalance(@NonNull float balance) {
        if (mTextBalanceStatus != null ) {
            mTextBalanceStatus.setText(String.format(getString(R.string.you_balance), String.valueOf(balance)));
            mTextBalanceStatus.setVisibility(View.VISIBLE);
        }
    }

    public static void start(final Context context, UserVideoResp.Item item, String res) {
        final Intent intent = new Intent(context, MakeOrdActivity.class);
        intent.putExtra(item_key, item);
        intent.putExtra(res_key, res);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private class PricingBar implements View.OnClickListener {

        private TextView text1,text2,text3,text4,text5,text6;
        private ImageView iv1,iv2,iv3,iv4,iv5,iv6;
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
                        iv1.setImageResource(R.drawable.ic_red_big);
                        text1.setTextColor(resources.getColor(R.color.black));
                        currentPosition = 10;
                    }
                    break;
                case R.id.iv2:
                    if (check(25)) {
                        resetAll();
                        iv2.setImageResource(R.drawable.ic_red_big);
                        text2.setTextColor(resources.getColor(R.color.black));
                        currentPosition = 25;
                    }
                    break;
                case R.id.iv3:
                    if (check(50)) {
                        resetAll();
                        iv3.setImageResource(R.drawable.ic_red_big);
                        text3.setTextColor(resources.getColor(R.color.black));
                        currentPosition = 50;
                    }
                    break;
                case R.id.iv4:
                    if (check(100)) {
                        resetAll();
                        iv4.setImageResource(R.drawable.ic_red_big);
                        text4.setTextColor(resources.getColor(R.color.black));
                        currentPosition = 100;
                    }
                    break;
                case R.id.iv5:
                    if (check(200)) {
                        resetAll();
                        iv5.setImageResource(R.drawable.ic_red_big);
                        text5.setTextColor(resources.getColor(R.color.black));
                        currentPosition = 200;
                    }
                    break;
                case R.id.iv6:
                    if (check(500)) {
                        resetAll();
                        iv6.setImageResource(R.drawable.ic_red_big);
                        text6.setTextColor(resources.getColor(R.color.black));
                        currentPosition = 500;
                    }
                    break;
            }
        }

        private boolean check(float pos) {
            if (blns < pos) {
                if (msg != null) msg.cancel();
                msg = showToast(String.format(getString(R.string.msg), String.valueOf(pos - blns)));
                return false;
            }
            return true;
        }

        public void resetAll() {
            iv1.setImageResource(R.drawable.ic_empty_icon);
            iv2.setImageResource(R.drawable.ic_empty_icon);
            iv3.setImageResource(R.drawable.ic_empty_icon);
            iv4.setImageResource(R.drawable.ic_empty_icon);
            iv5.setImageResource(R.drawable.ic_empty_icon);
            iv6.setImageResource(R.drawable.ic_empty_icon);

            text1.setTextColor(resources.getColor(R.color.whiteGray));
            text2.setTextColor(resources.getColor(R.color.whiteGray));
            text3.setTextColor(resources.getColor(R.color.whiteGray));
            text4.setTextColor(resources.getColor(R.color.whiteGray));
            text5.setTextColor(resources.getColor(R.color.whiteGray));
            text6.setTextColor(resources.getColor(R.color.whiteGray));
        }
    }
}
