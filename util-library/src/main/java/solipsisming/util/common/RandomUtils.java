package solipsisming.util.common;

import java.util.Random;

import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * 随机工具</br>
 * 创建于 2015-6-30 20:48:57
 *
 * @author 洪东明
 * @version 1.0
 */
public class RandomUtils {
    /**
     * 数字大小写字母
     */
    public static final String NUMBERS_AND_LETTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * 数字
     */
    public static final String NUMBERS = "0123456789";
    /**
     * 大小写字母
     */
    public static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * 大写字母
     */
    public static final String UPPER_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * 小写字母
     */
    public static final String LOWER_LETTERS = "abcdefghijklmnopqrstuvwxyz";

    private RandomUtils() {
        throw new UnacceptableInstanceError();
    }

    /**
     * 获取随机字母或数字
     *
     * @param length 随机的个数
     * @return 随机后的字符串
     */
    public static String getRandomNumbersAndLetters(int length) {
        if (length <= 0)
            return null;
        return getRandom(NUMBERS_AND_LETTERS.toCharArray(), length);
    }

    /**
     * 获取随机数字
     *
     * @param length 随机的个数
     * @return 随机后的字符串
     */
    public static String getRandomNumbers(int length) {
        if (length <= 0)
            return null;
        return getRandom(NUMBERS.toCharArray(), length);
    }

    /**
     * 获取随机字母
     *
     * @param length 随机的个数
     * @return 随机后的字符串
     */
    public static String getRandomLetters(int length) {
        if (length <= 0)
            return null;
        return getRandom(LETTERS.toCharArray(), length);
    }

    /**
     * 获取随机大写字母
     *
     * @param length 随机的个数
     * @return 随机后的字符串
     */
    public static String getRandomUpperLetters(int length) {
        if (length <= 0)
            return null;
        return getRandom(UPPER_LETTERS.toCharArray(), length);
    }

    /**
     * 获取随机小写字母
     *
     * @param length 随机的个数
     * @return 随机后的字符串
     */
    public static String getRandomLowerLetters(int length) {
        if (length <= 0)
            return null;
        return getRandom(LOWER_LETTERS.toCharArray(), length);
    }

    /**
     * 要随机的源字符串
     *
     * @param source 源字符串
     * @param length 随机的个数
     * @return 随机后的字符串
     * @see StringUtils 字符串工具
     */
    public static String getRandom(String source, int length) {
        return StringUtils.isEmpty(source) ? null : getRandom(source.toCharArray(), length);
    }

    /**
     * @param sourceChar 字符数组
     * @param length     随机的个数
     * @return 随机后的字符串
     */
    public static String getRandom(char[] sourceChar, int length) {
        StringBuilder str = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(sourceChar.length);
            str.append(sourceChar[index]);
        }
        return str.toString();
    }
}