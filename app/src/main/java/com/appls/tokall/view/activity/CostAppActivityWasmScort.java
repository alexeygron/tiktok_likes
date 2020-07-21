package com.appls.tokall.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.appls.tokall.R;
import com.appls.tokall.net.request.WasmScortUserVideoResp;
import com.appls.tokall.view.custom.WasmScortImage;
import com.appls.tokall.view.custom.WasmScortText;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.appls.tokall.util.UiUtils.showToast;
import static com.appls.tokall.util.WasmScortUtilsCr.getStrObj;

public class CostAppActivityWasmScort extends WasmScortBaseActivity {

    private WasmScortText mTextBalanceStatus;
    private Toast msg;
    private float currentPosition = 0;

    private float blns = 0;
    private WasmScortUserVideoResp.Item item;

    public static void start(final Context context, WasmScortUserVideoResp.Item item, String res) {
        final Intent intent = new Intent(context, CostAppActivityWasmScort.class);
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
                        text1.setTextColor(resources.getColor(R.color.dgd_res_black));
                        currentPosition = 10;
                    }
                    break;
                case R.id.iv2:
                    if (check(25)) {
                        resetAll();
                        iv2.setImageResource(R.drawable.wasm_scort_ic_red_big);
                        text2.setTextColor(resources.getColor(R.color.dgd_res_black));
                        currentPosition = 25;
                    }
                    break;
                case R.id.iv3:
                    if (check(50)) {
                        resetAll();
                        iv3.setImageResource(R.drawable.wasm_scort_ic_red_big);
                        text3.setTextColor(resources.getColor(R.color.dgd_res_black));
                        currentPosition = 50;
                    }
                    break;
                case R.id.iv4:
                    if (check(100)) {
                        resetAll();
                        iv4.setImageResource(R.drawable.wasm_scort_ic_red_big);
                        text4.setTextColor(resources.getColor(R.color.dgd_res_black));
                        currentPosition = 100;
                    }
                    break;
                case R.id.iv5:
                    if (check(200)) {
                        resetAll();
                        iv5.setImageResource(R.drawable.wasm_scort_ic_red_big);
                        text5.setTextColor(resources.getColor(R.color.dgd_res_black));
                        currentPosition = 200;
                    }
                    break;
                case R.id.iv6:
                    if (check(500)) {
                        resetAll();
                        iv6.setImageResource(R.drawable.wasm_scort_ic_red_big);
                        text6.setTextColor(resources.getColor(R.color.dgd_res_black));
                        currentPosition = 500;
                    }
                    break;
            }
        }

        private boolean check(float pos) {
            if (blns < pos) {
                if (msg != null) msg.cancel();
                msg = showToast(getStrObj(R.string.dgd_res_mss) + " " + (pos - blns));
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

            text1.setTextColor(resources.getColor(R.color.dgd_res_liteGray));
            text2.setTextColor(resources.getColor(R.color.dgd_res_liteGray));
            text3.setTextColor(resources.getColor(R.color.dgd_res_liteGray));
            text4.setTextColor(resources.getColor(R.color.dgd_res_liteGray));
            text5.setTextColor(resources.getColor(R.color.dgd_res_liteGray));
            text6.setTextColor(resources.getColor(R.color.dgd_res_liteGray));
        }
    }
}
