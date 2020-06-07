package com.demo.tiktok_likes_new.util;

public class JsUtils {

    public static String SCRIPT_SET_CLICK = "(function(){document.getElementsByClassName(\"tiktok-toolbar-like\")[0].click()})();";

    public static String SCRIPT_SET_LISTENER = "(function() {\n" +
            "    var origOpen = XMLHttpRequest.prototype.open;\n" +
            "    XMLHttpRequest.prototype.open = function() {\n" +
            "        this.addEventListener('load', function() {\n" +
            "            console.log(\"api_req\" + this.responseText); //whatever the response was\n" +
            "        });\n" +
            "        origOpen.apply(this, arguments);\n" +
            "    };\n" +
            "})();";


}
