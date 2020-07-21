package com.appls.tokall.util;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.appls.tokall.net.NetConfigure;
import com.scottyab.aescrypt.AESCrypt;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static com.appls.tokall.net.NetConfigure.dfgf;
import static com.appls.tokall.util.Common.MD5;
import static com.appls.tokall.util.Const.wasm_AES_KEY;
import static com.appls.tokall.util.Const.wasm_ASE_KEY;
import static com.appls.tokall.util.JsUtilsWasmScort.bufConve;

public class WasmScortUtilsCr extends KeysCr {

    private byte[] shaStartDe(String code) {
        byte[] decrypted = new byte[0];
        try {
            cipher.init(Cipher.DECRYPT_MODE, specKey2, ivKey1);
            decrypted = cipher.doFinal(SDdahTodss(code));
            if (decrypted.length > 0) {
                int trim = 0;

                for (int i = decrypted.length - 1; i >= 0; i--) if (decrypted[i] == 0) trim++;
                if (trim > 0) {
                    byte[] newArray = new byte[decrypted.length - trim];
                    System.arraycopy(decrypted, 0, newArray, 0, decrypted.length - trim);
                    decrypted = newArray;
                }
            }
        } catch (Exception ignored) {
        }
        return decrypted;
    }

    private byte[] shiStartEn(String text) {
        byte[] encrypted = new byte[0];
        try {
            cipher.init(Cipher.ENCRYPT_MODE, specKey2, ivKey1);
            encrypted = cipher.doFinal(dfgf(text).getBytes());
        } catch (Exception ignored) {
        }
        return encrypted;
    }


    private static Map<Integer, String> strMap = new HashMap<>();

    public String sdhtEdefvb(@Nullable String string) {
        try {
            return bufConve(shiStartEn(string));
        } catch (Exception e) {
            return e.toString();

        }

    }

    private static byte[] SDdahTodss(String str) {
        if (str == null) {
            return null;
        } else if (str.length() < 2) {
            return null;
        } else {
            int len = str.length() / 2;
            byte[] buffer = new byte[len];
            for (int i = 0; i < len; i++) {
                buffer[i] = (byte) Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
            }
            return buffer;
        }
    }

    public WasmScortUtilsCr(String androidId, String s, String s1) {
        String iv = (MD5(androidId + s).substring(0, 16));
        String secretKey = MD5(androidId + s1);
        ivKey1 = new IvParameterSpec(iv.getBytes());
        specKey2 = new SecretKeySpec(secretKey.getBytes(), wasm_AES_KEY);
        try {
            cipher = Cipher.getInstance(wasm_ASE_KEY);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    public static String getStrObj(@StringRes int resId) {
        try {
            if (strMap.containsKey(resId)) {
                //Log.i("ResourceHandler", "from strMap ");
                return strMap.get(resId);
            } else {
                String str = AESCrypt.decrypt(CPT_KEY, NetConfigure.CONTEXT.getString(resId));
                //Log.i("ResourceHandler", "new ");
                strMap.put(resId, str);
                return str;
            }
        } catch (Exception ignored) {

        }
        return NetConfigure.CONTEXT.getString(resId);
    }

    public static String getStrObj(String str) {
        try {
            return AESCrypt.decrypt(CPT_KEY, str);
        } catch (Exception ignored) {

        }
        return "";
    }

    @Nullable
    public String ShaiDesc(@Nullable String string) {
        if (string == null)
            return null;
        else if (string.startsWith("{")) {
            return string;
        } else {
            try {
                return new String(shaStartDe(string));
            } catch (Exception e) {
                e.printStackTrace();
                return e.toString();
            }
        }
    }

}
