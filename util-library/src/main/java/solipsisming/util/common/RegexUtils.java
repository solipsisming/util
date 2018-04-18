package solipsisming.util.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * 正则表达式校验</p>
 * 创建于 2015-6-12 19:51:32
 *
 * @author 洪东明
 * @version 1.0
 */
public class RegexUtils {
    /**
     * 禁止实例对象
     */
    private RegexUtils() {
        throw new UnacceptableInstanceError();
    }

    /**
     * 身份证证件号码
     */
    public static final String ID_CODE15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
    /**
     * 身份证证件号码
     */
    public static final String ID_CODE18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))" +
            "(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
    /**
     * 固定电话编码
     */
    public static final String FIX_PHONE_NUMBER = "(\\\\(?(010|021|022|023|024|025|026|027|028|029|852|)\\\\)" +
            "?-?\\\\d{8}(\\\\-?[0-9]{1,4})?)|(\\\\(?(0[3-9][0-9]{2})\\\\)?-?\\\\d{7,8}(\\\\-?[0-9]{1,4})?)";
    /**
     * 邮政编码
     */
    public static final String POST_CODE = "\\d{6}";
    /**
     * 邮箱
     */
    public static final String EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
    /**
     * 手机号码
     */
    public static final String MOBILE_PHONE_NUMBER = "^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";
    /**
     * 中文
     */
    public static final String CHINESE = "^[\\u4e00-\\u9fa5]+$";

    /**
     * 证件号码是否正确
     *
     * @param s 身份证
     * @return 匹配/不匹配
     */
    public static boolean isIDCode(String s) {
        Matcher m = getPattern(ID_CODE15).matcher(s);
        boolean res = m.matches();
        if (!res) {//15位身份证校验失败,校验18位
            m = getPattern(ID_CODE18).matcher(s);
            return m.matches();
        }
        return res;
    }

    /**
     * email是否正确
     *
     * @param s email
     * @return 匹配/不匹配
     */
    public static boolean isEmail(String s) {
        Matcher m = getPattern(EMAIL).matcher(s);
        return m.matches();
    }

    /**
     * 是否为中文
     *
     * @param s email
     * @return 匹配/不匹配
     */
    public static boolean isChinese(String s) {
        Matcher m = getPattern(CHINESE).matcher(s);
        return m.matches();
    }

    /**
     * 固话电话是否正确
     *
     * @param s 固定电话
     * @return 匹配/不匹配
     */
    public static boolean isPhoneNumber(String s) {
        Matcher m = getPattern(MOBILE_PHONE_NUMBER).matcher(s);
        return m.matches();

    }

    /**
     * 邮政编码是否正确
     *
     * @param s 邮政编码
     * @return 匹配/不匹配
     */
    public static boolean isPostCode(String s) {
        Matcher m = getPattern(POST_CODE).matcher(s);
        return m.matches();

    }

    /**
     * 手机号码否正确
     *
     * @param s 手机号
     * @return 匹配/不匹配
     */
    public static boolean isMobileNumber(String s) {
        Matcher m = getPattern(FIX_PHONE_NUMBER).matcher(s);
        return m.matches();
    }

    /**
     * 获取校验规则
     *
     * @param pattern 校验规则
     * @return 校验规则
     */
    private static Pattern getPattern(String pattern) {
        return Pattern.compile(pattern);
    }
}