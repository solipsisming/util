package solipsisming.util.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import solipsisming.util.exception.UnacceptableInstanceError;
import solipsisming.util.system.PrintLog;

/**
 * md5加密算法</p>
 * 创建于 2018-7-2 10:42:13
 *
 * @author 洪东明
 * @version 1.0
 */
public class MD5Crypto {

    /*
     *禁止创建对象
     */
    private MD5Crypto() {
        throw new UnacceptableInstanceError();
    }

    private static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static MessageDigest messagedigest;

    static {
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            PrintLog.logErr(e);
        }
    }

    /**
     * 获取一个md5字符串
     *
     * @param str 需要加密的md5串
     * @return 加密后的md5串
     */
    public static String getMD5String(String str) {
        byte[] bytes = str.getBytes();
        messagedigest.update(bytes);
        bytes = messagedigest.digest();
        return bufferToHex(bytes, 0, bytes.length);
    }

    /**
     * 将字节装换为string
     *
     * @param bytes 字节数组
     * @param m     截取起点
     * @param n     截取终点
     * @return string串
     */
    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    /**
     * 追加字节
     *
     * @param bt           字节
     * @param stringbuffer 字符串
     */
    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }
}