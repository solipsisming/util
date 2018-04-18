package solipsisming.util.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * 将字符串转换为UTF-8编码</p>
 * 创建于 2015-11-30 22:36:42
 *
 * @author 洪东明
 * @version 1.0
 */
public class EncodingUtils {

    /**
     * 禁止实例化
     */
    private EncodingUtils() {
        throw new UnacceptableInstanceError();
    }

    /**
     * 将指定字符串转换为utf-8编码
     *
     * @param source 字符串
     * @return utf-8字符串
     */
    public static String encodingUTF8(String source) {
        if (source == null || source.isEmpty() || source.trim().equals(""))
            return "";
        try {
            source = URLEncoder.encode(source, "UTF-8");
            return source;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}