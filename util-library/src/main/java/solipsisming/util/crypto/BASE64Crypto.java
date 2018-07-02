package solipsisming.util.crypto;


import android.util.Base64;

import java.io.UnsupportedEncodingException;

import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * base64加密算法</p>
 * 创建于 2018-7-2 10:42:13
 *
 * @author 洪东明
 * @version 1.0
 */
public class BASE64Crypto {

    private static final String ENCODE = "UTF-8";

    /**
     * 禁止创建对象
     */
    private BASE64Crypto() {
        throw new UnacceptableInstanceError();
    }

    /**
     * BASE64加密
     */
    public static byte[] encode(String key) throws UnsupportedEncodingException {
        return encode(key.getBytes(ENCODE));
    }

    /**
     * BASE64加密
     */
    public static byte[] encode(byte[] keyByte) throws UnsupportedEncodingException {
        return Base64.encode(keyByte, Base64.DEFAULT);
    }

    /**
     * BASE64加密
     */
    public static String encodeToString(String key) throws UnsupportedEncodingException {
        return encodeToString(key.getBytes(ENCODE));
    }

    /**
     * BASE64加密
     */
    public static String encodeToString(byte[] keyByte) throws UnsupportedEncodingException {
        return Base64.encodeToString(keyByte, Base64.DEFAULT);
    }

    /**
     * BASE64解密
     */
    public static byte[] decode(String key) throws UnsupportedEncodingException {
        return decode(key.getBytes(ENCODE));
    }

    /**
     * BASE64解密
     */
    public static byte[] decode(byte[] keyByte) throws UnsupportedEncodingException {
        return Base64.decode(keyByte, Base64.DEFAULT);
    }

    /**
     * BASE64加密
     */
    public static String decodeToString(String key) throws UnsupportedEncodingException {
        return decodeToString(key.getBytes(ENCODE));
    }

    /**
     * BASE64加密
     */
    public static String decodeToString(byte[] keyByte) throws UnsupportedEncodingException {
        return new String(Base64.decode(keyByte, Base64.DEFAULT), ENCODE);
    }
}