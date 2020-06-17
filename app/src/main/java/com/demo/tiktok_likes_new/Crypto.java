package com.demo.tiktok_likes_new;

import android.content.res.Configuration;
import android.util.Log;

import com.demo.tiktok_likes_new.net.NetConfigure;
import com.scottyab.aescrypt.AESCrypt;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import static com.demo.tiktok_likes_new.util.KeysCr.CPT_KEY;


public class Crypto {

    private static final String TAG = Crypto.class.getSimpleName();
    //public static final String EAS_KEY = "2B2dfg3g5dfgihLBdfgw==";

    public static void encrypt() {
        try {
            /*String str1 = AESCrypt.encrypt(EAS_KEY,"Используйте эти хэштеги для получения подписчиков");
            Log.i(TAG, "onCreate: " +  str1);
            Log.i(TAG, "onCreate: " + AESCrypt.decrypt(EAS_KEY, str1));*/
        } catch (Exception e) {
            e.printStackTrace();
        }


        String languageToLoad = "ru"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        NetConfigure.CONTEXT.getResources().updateConfiguration(config,
                NetConfigure.CONTEXT.getResources().getDisplayMetrics());


        Map<String, Integer> map = new LinkedHashMap<>();
       /* map.put("new_likes", R.string.new_likes);
        map.put("new_comments", R.string.new_comments);
        map.put("top_users_at_likes_menu", R.string.top_users_at_likes_menu);
        map.put("top_users_at_comments_menu", R.string.top_users_at_comments_menu);
        map.put("top_at_tagged_menu", R.string.top_at_tagged_menu);

        map.put("today", R.string.today);
        map.put("yesterday", R.string.yesterday);
        map.put("this_week", R.string.this_week);
        map.put("this_month", R.string.this_month);
        map.put("last_week", R.string.last_week);
        map.put("last_month", R.string.last_month);
        map.put("this_year", R.string.this_year);*/

        /*map.put("menu", R.string.menu);
        map.put("srt", R.string.srt);
        map.put("fgs", R.string.fgs);
        map.put("sdsf", R.string.sdsf);
        map.put("you_balance", R.string.you_balance);
        map.put("later", R.string.later);
        map.put("thank_you", R.string.thank_you);
        map.put("up_msg", R.string.up_msg);
        map.put("up", R.string.up);
        map.put("continu", R.string.continu);
        map.put("msg", R.string.msg);
        map.put("msg2", R.string.msg2);
        map.put("msg3", R.string.msg3);
        map.put("msg4", R.string.msg4);
        map.put("msg5", R.string.msg5);
        map.put("msg6", R.string.msg6);
        map.put("msg7", R.string.msg7);
        map.put("msg8", R.string.msg8);*/

        for (Map.Entry<String, Integer> set : map.entrySet()) {
            String df = NetConfigure.CONTEXT.getResources().getString(set.getValue());
            Log.i(TAG, df);

        }

        Log.i(TAG, " ");
        Log.i(TAG, " ");
        Log.i(TAG, "APP NAME -- " + NetConfigure.CONTEXT.getResources().getString(R.string.wasm_scort_app));

        for (Map.Entry<String, Integer> set : map.entrySet()) {
            String df = NetConfigure.CONTEXT.getResources().getString(set.getValue());
            try {
                Log.i(TAG, "<string name=\"" + set.getKey() + "\">" + AESCrypt.encrypt(CPT_KEY, df) + "</string>");
            } catch (Exception ex) {
                //do smth
            }
        }

        /*Field[] fields = R.string.class.getFields();
        for (final Field field : fields) {
            String name = field.getName(); //name of string
            try {
                int id = field.getInt(R.string.class); //id of string
                Log.i(TAG, " " + name + " = " + AESCrypt.encrypt(EAS_KEY,App.instance.getResources().getString(id)));
            } catch (Exception ex) {
                //do smth
            }
        }*/
    }
}
