package com.demo.tiktok_likes_new.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.viewpager.widget.ViewPager;

import com.demo.tiktok_likes_new.R;
import com.demo.tiktok_likes_new.fragment.EmptyFragment;
import com.demo.tiktok_likes_new.fragment.UserPhotosFragment;
import com.demo.tiktok_likes_new.network.Constants;
import com.google.android.material.navigation.NavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.orhanobut.hawk.Hawk;

import static com.demo.tiktok_likes_new.activity.TestActivity.cookies_tag;

public class MainActivity extends BaseAbstractActivity {

    private BottomNavigationViewEx mBottomNavigation;
    private AppBarConfiguration mAppBarConfiguration;

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

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            NavigationView navigationView = findViewById(R.id.nav_view);

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

            MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
            ViewPager viewPager = findViewById(R.id.view_pager);
            viewPager.setOffscreenPageLimit(4);
            viewPager.setAdapter(pagerAdapter);
            mBottomNavigation.setupWithViewPager(viewPager);
        }
    }

    public static class MyPagerAdapter extends FragmentStatePagerAdapter {

        private static int COUNT_ITEMS = 4;

        MyPagerAdapter(@NonNull FragmentManager fm) {
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
                    return UserPhotosFragment.newInstance();
                case 1:
                    return new EmptyFragment();
                case 2:
                    return new EmptyFragment();
                case 3:
                    return new EmptyFragment();
                default:
                    return new EmptyFragment();
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }
    }

    public static void start(final Context context) {
        final Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
