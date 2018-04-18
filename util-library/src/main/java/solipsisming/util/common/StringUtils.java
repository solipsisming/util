package solipsisming.util.common;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * 字符串工具</p>
 * 创建于 2015-6-12 22:51:21
 *
 * @author 洪东明
 * @version 1.0
 */
public class StringUtils {

    /**
     * 禁止实例对象
     */
    private StringUtils() {
        throw new UnacceptableInstanceError();
    }

    /**
     * 字符串是否不为空
     *
     * @param str 字符串
     * @return 结果null or not null
     */
    public static boolean isEmpty(CharSequence str) {
        return (str == null || str.length() == 0);
    }

    /**
     * 字符串是否不为空(去除两边空格)
     *
     * @param str 字符串
     * @return 结果是否为空
     */
    public static boolean isTrimEmpty(String str) {
        return (str == null || str.trim().length() == 0);
    }

    /**
     * 将字符串置为utf-8编码
     *
     * @param str 字符串
     * @return utf-8串
     * @throws UnsupportedEncodingException 编码找不到或不支持
     */
    public static String utf8Encode(String str) throws UnsupportedEncodingException {
        if (!isEmpty(str) && str.getBytes().length != str.length())
            return URLEncoder.encode(str, "UTF-8");
        return null;
    }
}