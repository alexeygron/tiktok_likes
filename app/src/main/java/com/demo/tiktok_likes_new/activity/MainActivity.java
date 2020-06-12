package com.demo.tiktok_likes_new.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import com.demo.tiktok_likes_new.ScabApp;
import com.demo.tiktok_likes_new.R;
import com.demo.tiktok_likes_new.fragment.FourTabFragment;
import com.demo.tiktok_likes_new.fragment.OneTabFragment;
import com.demo.tiktok_likes_new.fragment.ThreeTabFragment;
import com.demo.tiktok_likes_new.fragment.TwoTabFragment;
import com.demo.tiktok_likes_new.network.Constants;
import com.demo.tiktok_likes_new.util.StarDialog;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.orhanobut.hawk.Hawk;

import static com.demo.tiktok_likes_new.util.Common.DEFAULT_TAB;
import static com.demo.tiktok_likes_new.util.Common.cookies_tag;
import static com.demo.tiktok_likes_new.util.UiUtils.checkStarStatus;

public class MainActivity extends BaseAbstractActivity {

    private BottomNavigationViewEx mBottomNavigation;
    private TextView mTextBalanceStatus;
    private String blns = "0";
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ScabApp.initApp();

        if (!Hawk.contains(cookies_tag)) {
            LoginActivity.start(Constants.CONTEXT);
            //startActivity(new Intent(MainActivity.this, LoginActivity.class));
        } else {
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }

            mTextBalanceStatus = findViewById(R.id.toolbar_balance);
            drawer = findViewById(R.id.drawer_layout);

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.none, R.string.none);
            drawer.addDrawerListener(toggle);
            toggle.setDrawerIndicatorEnabled(true);
            toggle.syncState();

            mBottomNavigation = findViewById(R.id.bottom_nav);
            mBottomNavigation.setTextVisibility(false);

            checkStarStatus(this);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            ScabApp.initDataStorage.setAppInitListener(appInitListener);
        }
    }

    public void setUpBarState(@NonNull String stat) {
        if (mTextBalanceStatus != null && !stat.isEmpty()) {
            blns = stat;
            mTextBalanceStatus.setText(String.format(getString(R.string.you_balance), stat));
            mTextBalanceStatus.setVisibility(View.VISIBLE);
        }
    }

    public void onExit(View view) {
        new AlertDialog.Builder(this).setMessage(R.string.sdsf).setPositiveButton(android.R.string.ok, (dialog, which) -> {
            Hawk.delete(cookies_tag);
            LoginActivity.start(Constants.CONTEXT);
            finish();
        }).setNegativeButton(android.R.string.cancel, null).show();
    }

    public void onRats(View view) {
        StarDialog.show(this);
        drawer.closeDrawers();
    }

    public String getBlns() {
        return blns;
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
                    return OneTabFragment.newInstance();
                case 1:
                    return TwoTabFragment.newInstance();
                case 2:
                    return FourTabFragment.newInstance();
                case 3:
                    return ThreeTabFragment.newInstance();
                default:
                    return OneTabFragment.newInstance();
            }
        }
    }

    private AppInitListener appInitListener = () -> {
        if (ScabApp.initDataStorage.getApiOneStepResponse() != null)
            runOnUiThread(() -> {
                setUpBarState(ScabApp.initDataStorage.getApiOneStepResponse().getBalance_lfs());

                CostPagerAdapter pagerAdapter = new CostPagerAdapter(getSupportFragmentManager());
                ViewPager viewPager = findViewById(R.id.view_pager);
                viewPager.setOffscreenPageLimit(4);
                viewPager.setAdapter(pagerAdapter);
                viewPager.setCurrentItem(DEFAULT_TAB);
                mBottomNavigation.setCurrentItem(DEFAULT_TAB);
                mBottomNavigation.setupWithViewPager(viewPager);

                findViewById(R.id.loading_screen).setVisibility(View.GONE);
            });
    };

    public static interface AppInitListener{
        void onAppInit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpBarState(String.valueOf(ScabApp.initDataStorage.getBfgl()));
    }

    public static void start(final Context context) {
        final Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
