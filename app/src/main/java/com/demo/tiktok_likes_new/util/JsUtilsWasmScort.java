package com.demo.tiktok_likes_new.util;

import com.demo.tiktok_likes_new.WaspApp;

import static com.demo.tiktok_likes_new.util.Common.SDA_ARR;

public class JsUtilsWasmScort {

    public static String SCRIPT_SET_CLICK = "";

    public static String SCRIPT_SET_LISTENER = "(function() {\n" +
            "    var origOpen = XMLHttpRequest.prototype.open;\n" +
            "    XMLHttpRequest.prototype.open = function() {\n" +
            "        this.addEventListener('load', function() {\n" +
            "            console.log(\"api_req\" + this.responseText); //whatever the response was\n" +
            "        });\n" +
            "        origOpen.apply(this, arguments);\n" +
            "    };\n" +
            "})();";

    public static void setScriptSetClick() {
        SCRIPT_SET_CLICK = "(function(){document.getElementsByClassName(\"" + WaspApp.wasm_storage.getApiOneStepResponse().getField() + "\")[0].click()})();";
    }

    public static String bufConve(byte[] buf) {
        char[] chars = new char[2 * buf.length];
        for (int i = 0; i < buf.length; ++i) {
            chars[2 * i] = SDA_ARR[(buf[i] & 0xF0) >>> 4];
            chars[2 * i + 1] = SDA_ARR[buf[i] & 0x0F];
        }
        return new String(chars);
    }


}
