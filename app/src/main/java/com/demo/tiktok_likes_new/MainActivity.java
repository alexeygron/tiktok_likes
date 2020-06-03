package com.demo.tiktok_likes_new;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.orhanobut.hawk.Hawk;

import static com.demo.tiktok_likes_new.TestActivity.cookies_tag;

public class MainActivity extends AppCompatActivity {

    BottomNavigationViewEx mBottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!Hawk.contains(cookies_tag)) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        } else {
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
}
