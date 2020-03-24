package com.danqiu.myapplication.utils;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AesUtils {
    private final String MiddleKey = "123456";//就是所谓的密钥，加密和解密双方都需要,生成key
    private Key key;

    /**
     * 生成加密key
     * @return
     */
    public Key getKey() {
        if (key == null) {
            try {
                // 生成KEY ,AES 要求密钥长度为 128
                KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
                SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
                secureRandom.setSeed(MiddleKey.getBytes());
                keyGenerator.init(128,secureRandom);
                SecretKey secretKey = keyGenerator.generateKey();
                byte[] byteKey = secretKey.getEncoded();

                // 转换KEY
                key = new SecretKeySpec(byteKey, "AES");
                return key;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return key;
        }
    }

    /**
     * 加密
     * @param password
     * @return
     */
    public String encrypt(String password) {
        try {
            // 加密
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, getKey());
            byte[] result = cipher.doFinal(password.getBytes());
            // return Hex.encodeHexString(result);
            return  Base64.getEncoder().encodeToString(result);//通过Base64转码返回
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 解密
     * @param encryptedPassword
     * @return
     */
    public String decrypt(String encryptedPassword) {
        try {
            // 解密
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, getKey());
            // byte[] result = cipher.doFinal(encryptedPassword.getBytes());

            byte[] result = cipher.doFinal( Base64.getDecoder().decode(encryptedPassword) );
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }





}
