package com.demo.tiktok_likes_new;

import android.app.Application;

import com.orhanobut.hawk.Hawk;

public class App extends Application {

    public static App instance;


    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Hawk.init(this).build();
    }
}