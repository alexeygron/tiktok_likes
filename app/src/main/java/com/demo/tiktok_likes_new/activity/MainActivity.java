package com.demo.tiktok_likes_new.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.viewpager.widget.ViewPager;

import com.demo.tiktok_likes_new.App;
import com.demo.tiktok_likes_new.R;
import com.demo.tiktok_likes_new.fragment.EmptyFragment;
import com.demo.tiktok_likes_new.fragment.FourTabFragment;
import com.demo.tiktok_likes_new.fragment.ThreeTabFragment;
import com.demo.tiktok_likes_new.fragment.TwoTabFragment;
import com.demo.tiktok_likes_new.fragment.OneTabFragment;
import com.demo.tiktok_likes_new.network.Constants;
import com.google.android.material.navigation.NavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.orhanobut.hawk.Hawk;


import static com.demo.tiktok_likes_new.activity.TestActivity.cookies_tag;
import static com.demo.tiktok_likes_new.util.Common.DEFAULT_TAB;

public class MainActivity extends BaseAbstractActivity {

    private BottomNavigationViewEx mBottomNavigation;
    private AppBarConfiguration mAppBarConfiguration;
    private TextView mTextBalanceStatus;
    private String blns = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            NavigationView navigationView = findViewById(R.id.NavigationView);

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.setDrawerIndicatorEnabled(true);
            toggle.syncState();

            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                    R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                    .setDrawerLayout(drawer)
                    .build();

            mBottomNavigation = findViewById(R.id.bottom_nav);
            mBottomNavigation.setTextVisibility(false);

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

            App.initDataStorage.setAppInitListener(appInitListener);
        }
    }

    public void setUpBalance(@NonNull String balance) {
        if (mTextBalanceStatus != null && !balance.isEmpty()) {
            blns = balance;
            mTextBalanceStatus.setText(String.format(getString(R.string.you_balance), balance));
            mTextBalanceStatus.setVisibility(View.VISIBLE);
        }
    }

    public String getBlns() {
        return blns;
    }

    public TwoTabFragment twoTabFragment;

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
                    twoTabFragment = TwoTabFragment.newInstance();
                    return twoTabFragment;
                case 2:
                    return ThreeTabFragment.newInstance();
                case 3:
                    return FourTabFragment.newInstance();
                default:
                    return OneTabFragment.newInstance();
            }
        }
    }

    private AppInitListener appInitListener = () -> {
        if (App.initDataStorage.getApiOneStepResponse() != null)
            runOnUiThread(() -> {
                setUpBalance(App.initDataStorage.getApiOneStepResponse().getBalance_lfs());

                CostPagerAdapter pagerAdapter = new CostPagerAdapter(getSupportFragmentManager());
                ViewPager viewPager = findViewById(R.id.view_pager);
                viewPager.setOffscreenPageLimit(4);
                viewPager.setAdapter(pagerAdapter);
                viewPager.setCurrentItem(DEFAULT_TAB);
                mBottomNavigation.setCurrentItem(DEFAULT_TAB);
                mBottomNavigation.setupWithViewPager(viewPager);

                findViewById(R.id.loading_screen).setVisibility(View.GONE);
            });

            //setUpBalance(App.initDataStorage.getApiOneStepResponse().getBalance_lfs());
    };

    public static interface AppInitListener{
        void onAppInit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpBalance(String.valueOf(App.initDataStorage.getBfgl()));
    }

    public static void start(final Context context) {
        final Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
