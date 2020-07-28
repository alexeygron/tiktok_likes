package com.demo.tiktok_likes_new.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.demo.tiktok_likes_new.R;
import com.demo.tiktok_likes_new.WaspApp;
import com.demo.tiktok_likes_new.net.NetConfigure;
import com.demo.tiktok_likes_new.util.StarDialog;
import com.demo.tiktok_likes_new.util.WasmScortUpdDialog;
import com.demo.tiktok_likes_new.view.custom.WasmScortText;
import com.demo.tiktok_likes_new.view.fragment.WasmScortFourFragment;
import com.demo.tiktok_likes_new.view.fragment.WasmScortOneFragment;
import com.demo.tiktok_likes_new.view.fragment.WasmScortThreeFragment;
import com.demo.tiktok_likes_new.view.fragment.WasmScortTwoFragment;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.orhanobut.hawk.Hawk;

import static com.demo.tiktok_likes_new.util.Common.DEFAULT_TAB;
import static com.demo.tiktok_likes_new.util.Common.cookies_tag;
import static com.demo.tiktok_likes_new.util.UiUtils.checkStarStatus;
import static com.demo.tiktok_likes_new.util.WasmScortUtilsCr.getStrObj;

public class ListVorednActivityWasmScort extends WasmScortBaseActivity {

    private BottomNavigationViewEx mBottomNavigation;
    private WasmScortText mTextBalanceStatus;
    private String blns = "0";
    DrawerLayout drawer;
    private AppInitListener appInitListener = () -> {
        if (WaspApp.wasm_storage.getApiOneStepResponse() != null)
            runOnUiThread(() -> {
                setUpBarState(WaspApp.wasm_storage.getApiOneStepResponse().getBalance_lfs());


                if (!TextUtils.isEmpty(WaspApp.wasm_storage.getApiOneStepResponse().getUdp_url())) {
                    WasmScortUpdDialog.show(this);
                }

                findViewById(R.id.loading_screen).setVisibility(View.GONE);
            });
    };

    public static void start(final Context context) {
        final Intent intent = new Intent(context, ListVorednActivityWasmScort.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wasm_scort_activity_navigation);

        }


    public void onRats(View view) {
        StarDialog.show(this);
        drawer.closeDrawers();
    }

    public String getBlns() {
        return blns;
    }

    public void setUpBarState(@NonNull String stat) {
        if (mTextBalanceStatus != null && !stat.isEmpty()) {
            blns = stat;
            mTextBalanceStatus.setText(getStrObj(R.string.wasm_scort_vbtgh) + " " + stat);
            mTextBalanceStatus.setVisibility(View.VISIBLE);
        }
    }

    public void onExit(View view) {
        new AlertDialog.Builder(this).setMessage(getStrObj(R.string.wasm_scort_exit_quest)).setPositiveButton(android.R.string.ok, (dialog, which) -> {
            Hawk.delete(cookies_tag);
            AppAuthActivityWasmScort.start(NetConfigure.CONTEXT);
            finish();
        }).setNegativeButton(android.R.string.cancel, null).show();
        drawer.closeDrawers();
    }

    public interface AppInitListener{
        void onAppInit();
    }



}
