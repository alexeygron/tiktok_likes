package com.demo.tiktok_likes_new.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static com.demo.tiktok_likes_new.util.KeyPass.KEY_CRP;
import static com.demo.tiktok_likes_new.util.KeyPass.PASS_CRP;

public class Crypto {

    private static Cipher cipher;
    private static IvParameterSpec ivParameterSpec;
    private static SecretKeySpec secretKeySpec;

    public static void init(String deviceId) {
        String iv = (md5(deviceId + "ng93u4hg02glf").substring(0, 16));
        String secretKey = md5(deviceId + "h0854034jgwrjgwpi4ey0u4");
        ivParameterSpec = new IvParameterSpec(iv.getBytes());
        secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
        try {
            cipher = Cipher.getInstance("AES/CBC/ZeroBytePadding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {

        }
    }

    private static char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static byte[] encrypt(String text) {
        byte[] encrypted = null;

        try {
            cipher.init(
                    Cipher.ENCRYPT_MODE,
                    secretKeySpec,
                    ivParameterSpec)
            ;

            encrypted = cipher.doFinal(padString(text).getBytes());
        } catch (Exception e) {

        }

        return encrypted;
    }

    private static byte[] decrypt(String code)  {
        byte[] decrypted = null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            decrypted = cipher.doFinal(hexToBytes(code));
            //Remove trailing zeroes
            if (decrypted.length > 0) {
                int trim = 0;
                for (int i = decrypted.length - 1; i >= 0; i--) if (decrypted[i] == 0) trim++;

                if (trim > 0) {
                    byte[] newArray = new byte[decrypted.length - trim];
                    System.arraycopy(decrypted, 0, newArray, 0, decrypted.length - trim);
                    decrypted = newArray;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return decrypted;
    }

    public static String bytesToHex(byte[] buf) {
        char[] chars = new char[2 * buf.length];
        for (int i = 0; i < buf.length; ++i) {
            chars[2 * i] = HEX_CHARS[(buf[i] & 0xF0) >>> 4];
            chars[2 * i + 1] = HEX_CHARS[buf[i] & 0x0F];
        }
        return new String(chars);
    }

    private static byte[] hexToBytes(String str) {
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

    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                StringBuilder h = new StringBuilder(Integer.toHexString(0xFF & aMessageDigest));
                while (h.length() < 2)
                    h.insert(0, "0");
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {

        }
        return "";
    }

    public static String decryptStr(String string) {
        if (string == null)
            return null;
        else if (string.startsWith("{")) {
            return string;
        } else {
            try {
                return new String(decrypt(string));
            } catch (Exception e) {

                return e.toString();
            }
        }
    }

    public static String encryptStr(String string) {
        try {
            return bytesToHex(encrypt(string));
        } catch (Exception e) {

            return e.toString();
        }
    }

    public static String padString(String source) {
        char paddingChar = 0;
        int size = 16;
        int x = source.length() % size;
        int padLength = size - x;
        StringBuilder sourceBuilder = new StringBuilder(source);
        for (int i = 0; i < padLength; i++) {

            sourceBuilder.append(paddingChar);
        }

        source = sourceBuilder.toString();
        return source;
    }
}