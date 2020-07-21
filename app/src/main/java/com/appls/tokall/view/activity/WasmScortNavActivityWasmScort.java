package com.appls.tokall.view.activity;

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

import com.appls.tokall.R;
import com.appls.tokall.TokkallApp;
import com.appls.tokall.net.NetConfigure;
import com.appls.tokall.util.StarDialog;
import com.appls.tokall.util.WasmScortUpdDialog;
import com.appls.tokall.view.custom.WasmScortText;
import com.appls.tokall.view.fragment.WasmScortFourFragment;
import com.appls.tokall.view.fragment.WasmScortOneFragment;
import com.appls.tokall.view.fragment.WasmScortThreeFragment;
import com.appls.tokall.view.fragment.WasmScortTwoFragment;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.orhanobut.hawk.Hawk;

import static com.appls.tokall.util.Common.DEFAULT_TAB;
import static com.appls.tokall.util.Common.cookies_tag;
import static com.appls.tokall.util.UiUtils.checkStarStatus;
import static com.appls.tokall.util.WasmScortUtilsCr.getStrObj;

public class WasmScortNavActivityWasmScort extends WasmScortBaseActivity {

    private BottomNavigationViewEx mBottomNavigation;
    private WasmScortText mTextBalanceStatus;
    private String blns = "0";
    DrawerLayout drawer;
    private AppInitListener appInitListener = () -> {
        if (TokkallApp.wasm_storage.getApiOneStepResponse() != null)
            runOnUiThread(() -> {
                setUpBarState(TokkallApp.wasm_storage.getApiOneStepResponse().getBalance_lfs());

                CostPagerAdapter pagerAdapter = new CostPagerAdapter(getSupportFragmentManager());
                ViewPager viewPager = findViewById(R.id.view_pager);
                viewPager.setOffscreenPageLimit(4);
                viewPager.setAdapter(pagerAdapter);
                viewPager.setCurrentItem(DEFAULT_TAB);
                mBottomNavigation.setCurrentItem(DEFAULT_TAB);
                mBottomNavigation.setupWithViewPager(viewPager);

                if (!TextUtils.isEmpty(TokkallApp.wasm_storage.getApiOneStepResponse().getUdp_url())) {
                    WasmScortUpdDialog.show(this);
                }

                findViewById(R.id.loading_screen).setVisibility(View.GONE);
            });
    };

    public static void start(final Context context) {
        final Intent intent = new Intent(context, WasmScortNavActivityWasmScort.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wasm_scort_activity_navigation);
        TokkallApp.startAppReq();

        if (!Hawk.contains(cookies_tag)) {
            AppAuthActivityWasmScort.start(NetConfigure.CONTEXT);
            //startActivity(new Intent(WasmScortNavActivityWasmScort.this, AppAuthActivityWasmScort.class));
        } else {
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }

            mTextBalanceStatus = findViewById(R.id.toolbar_balance);
            drawer = findViewById(R.id.drawer_layout);

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.dgd_res_emp, R.string.dgd_res_emp);
            drawer.addDrawerListener(toggle);
            toggle.setDrawerIndicatorEnabled(true);
            toggle.syncState();

            mBottomNavigation = findViewById(R.id.bottom_nav);
            mBottomNavigation.setTextVisibility(false);

            checkStarStatus(this);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            TokkallApp.wasm_storage.setAppInitListener(appInitListener);

            ((TextView) findViewById(R.id.menu)).setText(getStrObj(R.string.dgd_res_menu_text));
            ((TextView) findViewById(R.id.text1)).setText(getStrObj(R.string.dgd_res_exit));
            ((TextView) findViewById(R.id.text2)).setText(getStrObj(R.string.dgd_res_set_rate));
        }
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
            mTextBalanceStatus.setText(getStrObj(R.string.dgd_res_vbtgh) + " " + stat);
            mTextBalanceStatus.setVisibility(View.VISIBLE);
        }
    }

    public void onExit(View view) {
        new AlertDialog.Builder(this).setMessage(getStrObj(R.string.dgd_res_exit_quest)).setPositiveButton(android.R.string.ok, (dialog, which) -> {
            Hawk.delete(cookies_tag);
            AppAuthActivityWasmScort.start(NetConfigure.CONTEXT);
            finish();
        }).setNegativeButton(android.R.string.cancel, null).show();
        drawer.closeDrawers();
    }

    public interface AppInitListener{
        void onAppInit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpBarState(String.valueOf(TokkallApp.wasm_storage.getBfgl()));
    }

    public class CostPagerAdapter extends FragmentStatePagerAdapter {

        private int COUNT_ITEMS = 4;

        CostPagerAdapter(@NonNull FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }


        @Override
        public int getCount() {
            return COUNT_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return WasmScortOneFragment.newInstance();
                case 1:
                    //return WasmScortTwoTestFragment.newInstance();
                    return WasmScortTwoFragment.newInstance();
                case 2:
                    return WasmScortFourFragment.newInstance();
                case 3:
                    return WasmScortThreeFragment.newInstance();
                default:
                    return WasmScortOneFragment.newInstance();
            }
        }
    }
}
