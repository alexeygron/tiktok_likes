package com.appls.tokall.net.parser;

import org.json.JSONException;

public interface WasmScortIParser<T> {

    T parse(String json)
            throws JSONException;
}
