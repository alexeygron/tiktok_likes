package com.appls.tokall.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class KeysCr {

    public static final String KEY_CRP = "gh348gh9384gh29";
    public static final String PASS_CRP = "d7gh7324gh394bhdfjkvnnmxcdsf1";

    public static final String CPT_KEY = "dfg234dEDT2yuj5thgsdf";

    public static final String uiid = "uiid";
    public static final String uniqueId = "uniqueId";

    IvParameterSpec ivKey1;
    Cipher cipher;
    SecretKeySpec specKey2;

    public static String getKeyCrp() {
        return KEY_CRP;
    }

    public static String getPassCrp() {
        return PASS_CRP;
    }
}
