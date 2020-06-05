package com.demo.tiktok_likes_new.network.request;

import org.json.JSONException;

public interface IParser<T> {

    T parse(String json)
            throws JSONException;
}
