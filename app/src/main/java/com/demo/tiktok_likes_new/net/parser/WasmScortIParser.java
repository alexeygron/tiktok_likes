package com.demo.tiktok_likes_new.net.parser;

import org.json.JSONException;

public interface WasmScortIParser<T> {

    T parse(String json)
            throws JSONException;
}
