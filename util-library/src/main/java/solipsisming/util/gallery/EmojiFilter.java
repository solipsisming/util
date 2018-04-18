package solipsisming.util.gallery;

import solipsisming.util.common.StringUtils;
import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * emoji表情</p>
 * 创建于 2015-6-12 21:47:53
 *
 * @author 洪东明
 * @version 1.0
 */
public class EmojiFilter {

    /**
     * 禁止创建对象
     */
    private EmojiFilter() {
        throw new UnacceptableInstanceError();
    }

    /**
     * 判断是否有emoji表情
     *
     * @param source 字符串
     * @return 是否有emoji表情
     * @see solipsisming.util.common.StringUtils 字符串工具
     */
    public static boolean containsEmoji(String source) {
        if (StringUtils.isEmpty(source)) {
            return false;
        }
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (isEmojiCharacter(codePoint)) {//判断到了这里表明，确认有表情字符
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否为emoji字符
     *
     * @param codePoint 字符
     * @return 是或不是
     */
    private static boolean isEmojiCharacter(char codePoint) {
        if (codePoint >= 0xd800 && codePoint <= 0xdbff) {
            int uc = ((codePoint - 0xd800) * 0x400) + (codePoint - 0xdc00) + 0x10000;
            if (0x1d000 <= uc && uc <= 0x1f77f) {
                return true;
            }
        } else if (codePoint == 0x20e3) {
            return true;
        } else {
            if (codePoint >= 0x2100 && codePoint <= 0x27ff && codePoint != 0x263b) {
                return true;
            } else if (codePoint >= 0x2B05 && codePoint <= 0x2b07) {
                return true;
            } else if (codePoint >= 0x2934 && codePoint <= 0x2935) {
                return true;
            } else if (codePoint >= 0x3297 && codePoint <= 0x3299) {
                return true;
            } else if (codePoint == 0xa9
                    || codePoint == 0xae
                    || codePoint == 0x303d
                    || codePoint == 0x3030
                    || codePoint == 0x2b55
                    || codePoint == 0x2b1c
                    || codePoint == 0x2b1b
                    || codePoint == 0x2b50
                    || codePoint == 0x231a) {
                return true;
            }
        }
        return false;
    }

    /**
     * 过滤emoji或者其他非文字类型的字符
     *
     * @param source 字符串
     * @return 是否含有其他非文字字符
     */
    public static String filterEmoji(String source) {
        if (!containsEmoji(source)) {//不包含emoji表情
            return source;
        }
        //不包含，直接返回
        StringBuilder buf = null;
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (buf == null) {
                buf = new StringBuilder(source.length());
            }
            if (isEmojiCharacter(codePoint)) {
                buf.append("[表情]");
                i++;
            } else {
                buf.append(codePoint);
            }
        }
        return buf.toString();
    }
}