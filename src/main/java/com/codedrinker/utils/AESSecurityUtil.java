package com.codedrinker.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * Created by codedrinker on 18/08/2017.
 */
public class AESSecurityUtil {
    private static final String KEY_ALGORITHM = "AES";
    private static final int KEY_SIZE = 128;

    public static Key getKey() throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        kg.init(KEY_SIZE);
        SecretKey secretKey = kg.generateKey();
        return secretKey;
    }

    public static Key codeToKey(String key) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(key);
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
        return secretKey;
    }

    public static byte[] decrypt(byte[] data, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] ivBytes = new byte[16];
        IvParameterSpec iv = new IvParameterSpec(ivBytes);
        cipher.init(2, key, iv);
        return cipher.doFinal(data);
    }

    public static String decrypt(String data, String key) throws Exception {
        byte[] bytes = ParseUtil.parseBase642Byte(data);
        byte[] decrypt = decrypt(bytes, codeToKey(key));
        return new String(decrypt, "UTF-8");
    }

    public static byte[] encrypt(byte[] data, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] ivBytes = new byte[16];
        IvParameterSpec iv = new IvParameterSpec(ivBytes);
        cipher.init(1, key, iv);
        return cipher.doFinal(data);
    }

    public static String encrypt(String data, String code) throws Exception {
        Key key = codeToKey(code);
        byte[] encryptBytes = encrypt(data.getBytes("UTF-8"), key);
        return ParseUtil.parseByte2Base64(encryptBytes);
    }
}
