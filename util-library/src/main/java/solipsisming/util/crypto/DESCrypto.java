package solipsisming.util.crypto;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * des加密算法</p>
 * 创建于 2018-7-2 10:42:13
 *
 * @author 洪东明
 * @version 1.0
 */
public class DESCrypto {

    private static final String ALGORITHM = "DES";

    /**
     * 禁止创建对象
     */
    private DESCrypto() {
        throw new UnacceptableInstanceError();
    }

    /**
     * 加密
     */
    public static byte[] encode(byte[] data, String key) throws Exception {
        Key k = toKey(BASE64Crypto.decode(key));
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, k);
        return cipher.doFinal(data);
    }

    /**
     * 解密
     */
    public static byte[] decode(byte[] data, String key) throws Exception {
        Key k = toKey(BASE64Crypto.decode(key));
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, k);
        return cipher.doFinal(data);
    }

    /**
     * 转换密钥
     */
    private static Key toKey(byte[] key) throws Exception {
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        // 当使用其他对称加密算法时，如AES、Blowfish等算法时，用下述代码替换上述三行代码
        // SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);
        return keyFactory.generateSecret(dks);
    }

    /**
     * 生成密钥
     */
    public static String initKey() throws Exception {
        return initKey(null);
    }

    /**
     * 生成密钥
     */
    public static String initKey(String seed) throws Exception {
        SecureRandom secureRandom;
        if (seed != null) {
            secureRandom = new SecureRandom(BASE64Crypto.decode(seed));
        } else {
            secureRandom = new SecureRandom();
        }
        KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);
        kg.init(secureRandom);
        SecretKey secretKey = kg.generateKey();
        return BASE64Crypto.encodeToString(secretKey.getEncoded());
    }
}