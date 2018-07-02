package solipsisming.util.crypto;

import java.security.MessageDigest;

import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * sha加密算法</p>
 * 创建于 2018-7-2 10:42:13
 *
 * @author 洪东明
 * @version 1.0
 */
public class SHACrypto {

    private static final String KEY_SHA = "SHA";

    /**
     * 禁止创建对象
     */
    private SHACrypto() {
        throw new UnacceptableInstanceError();
    }

    /**
     * SHA加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryptSHA(byte[] data) throws Exception {
        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
        sha.update(data);
        return sha.digest();
    }
}