package com.demo.tiktok_likes_new.network.parser;

import org.json.JSONException;

public interface IParser<T> {

    T parse(String json)
            throws JSONException;
}
