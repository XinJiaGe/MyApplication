package com.lixinjia.myapplication.encryption;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * 作者：李忻佳
 * 日期：2017/12/6/006.
 * 说明：用于aes加解密
 */

public class AESUtils {
    public static final String DEFAULT_KEY = "0123456789ABCDEF";

    public static String decrypt(String content, String key) {
        String result = null;
        byte[] decryptResult = null;
        try {
            byte[] contentBytes = Base64.decode(content, 0);
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            decryptResult = cipher.doFinal(contentBytes);
            if (decryptResult != null) {
                result = new String(decryptResult, "UTF-8");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
    //解密
    public static String decrypt(String content) {
        return decrypt(content, DEFAULT_KEY);
    }
    //加密
    public static String encrypt(String content) {
        byte[] encryptResult = null;
        String result = null;
        encrypt(content, DEFAULT_KEY);
        if (encryptResult != null) {
            result = Base64.encodeToString(encryptResult, 0);
        }
        return result;
    }
    //加密
    public static byte[] encrypt(byte[] contentBytes) {
        return encrypt(contentBytes, DEFAULT_KEY);
    }

    public static byte[] encrypt(String content, String key) {
        byte[] contentBytes = new byte[0];
        try {
            contentBytes = content.getBytes("UTF-8");
            return encrypt(contentBytes,key);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static byte[] encrypt(String content, byte[] key) {
        byte[] contentBytes = new byte[0];
        try {
            contentBytes = content.getBytes("UTF-8");
            return encrypt(contentBytes,key);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static byte[] encrypt(byte[] contentBytes, String key) {
        byte[] encryptResult = null;
        String result = null;
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            encryptResult = cipher.doFinal(contentBytes);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return encryptResult;
    }
    public static byte[] encrypt(byte[] contentBytes, byte[] key) {
        byte[] encryptResult = null;
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            encryptResult = cipher.doFinal(contentBytes);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return encryptResult;
    }

}
