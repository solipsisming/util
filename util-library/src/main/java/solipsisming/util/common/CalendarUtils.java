package solipsisming.util.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * 日期工具</p>
 * 创建于  2015-6-1 20:54:28
 *
 * @author 洪东明
 * @version 1.0
 */
public final class CalendarUtils {

    /**
     * 禁止创建对象
     */
    private CalendarUtils() {
        throw new UnacceptableInstanceError();
    }

    /**
     * 年字符串格式
     */
    public static final String FORMAT_YEAR = "yyyy";
    /**
     * 月字符串格式
     */
    public static final String FORMAT_MONTH = "MM";
    /**
     * 日字符串格式
     */
    public static final String FORMAT_DAY = "dd";
    /**
     * 星期字符串格式
     */
    public static final String FORMAT_WEEK1 = "EEEE";
    /**
     * 周字符串格式
     */
    public static final String FORMAT_WEEK2 = "EE";
    /**
     * 小时字符串格式(24小时)=01,13
     */
    public static final String FORMAT_HOUR24_2UNIT = "HH";
    /**
     * 小时字符串格式(24小时)=1,13
     */
    public static final String FORMAT_HOUR24_1UNIT = "H";
    /**
     * 小时字符串格式(12小时)01,1
     */
    public static final String FORMAT_HOUR12_2UNIT = "hh";
    /**
     * 小时字符串格式(12小时)1,1
     */
    public static final String FORMAT_HOUR12_1UNIT = "h";
    /**
     * 分钟字符串格式
     */
    public static final String FORMAT_MINUTE = "mm";
    /**
     * 秒字符串格式
     */
    public static final String FORMAT_SECOND = "ss";
    /**
     * 年月日字符串格式
     */
    public static final String FORMAT_YEAR_MONTH_DAY = FORMAT_YEAR + ":" + FORMAT_MONTH + ":" + FORMAT_DAY;
    /**
     * 时分秒字符串格式
     */
    public static final String FORMAT_HOUR_MINUTE_SECOND = FORMAT_HOUR24_2UNIT + ":" + FORMAT_MINUTE + ":" + FORMAT_SECOND;
    /**
     * 年月日时分秒字符串格式
     */
    public static final String FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = FORMAT_YEAR_MONTH_DAY + " " + FORMAT_HOUR_MINUTE_SECOND;


    private static final int MINUTE = 60;// 分钟(秒)
    private static final int HOUR = MINUTE * MINUTE;// 小时(秒)
    private static final int DAY = 24 * HOUR;// 天(秒)
    private static final int MONTH = 30 * DAY;// 月(秒)
    private static final int YEAR = 365 * DAY;// 年(秒)

    private static final SimpleDateFormat format = (SimpleDateFormat) DateFormat.getDateTimeInstance();//日期格式

    /**
     * 获取当前日期时间(带格式)
     *
     * @param pattern 格式
     * @return 日期时间
     */
    public static String getDateCustomerFormat(String pattern) {
        Date date = new Date(System.currentTimeMillis());
        format.applyPattern(pattern);
        return format.format(date);
    }

    /**
     * 获取当前日期时间(带格式)
     *
     * @param time    数字时间
     * @param pattern 格式
     * @return 日期时间
     */
    public static String getDateCustomerFormat(long time, String pattern) {
        Date date = new Date(time);
        format.applyPattern(pattern);
        return format.format(date);
    }

    /**
     * 判断上午还是下午
     *
     * @return AM/PM
     */
    public static String getTimeMeridian() {
        Calendar calendar = Calendar.getInstance();
        String s = "AM";
        if (calendar.get(Calendar.AM_PM) == Calendar.PM)
            s = "PM";
        return s;
    }

    /**
     * 时间差
     *
     * @return 日期时间
     */
    public static String getPastAgo(long original) {
        String gap = "";
        long secondGap = (System.currentTimeMillis() - original) / 1000;
        if (secondGap > YEAR)
            gap = secondGap / YEAR + "年前";
        else if (secondGap > MONTH)
            gap = secondGap / MONTH + "月前";
        else if (secondGap > DAY)
            gap = secondGap / DAY + "天前";
        else if (secondGap > HOUR)
            gap = secondGap / HOUR + "小时前";
        else if (secondGap > MINUTE)
            gap = secondGap / MINUTE + "分钟前";
        else
            gap = "刚刚";
        return gap;
    }

    /**
     * 获取年份
     *
     * @return 年
     */
    public static int getYear() {
        return get(Calendar.YEAR);
    }

    /**
     * 获取月份
     *
     * @return 月
     */
    public static int getMonth() {
        return get(Calendar.MONTH) + 1;
    }

    /**
     * 获取号
     *
     * @return 号
     */
    public static int getDay() {
        return get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取小时(24)
     *
     * @return 小时
     */
    public static int getHour24() {
        return get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取小时
     *
     * @return 小时(12)
     */
    public static int getHour12() {
        return get(Calendar.HOUR);
    }

    /**
     * 获取分钟
     *
     * @return 分钟
     */
    public static int getMinute() {
        return get(Calendar.MINUTE);
    }

    /**
     * 获取秒
     *
     * @return 秒
     */
    public static int getSecond() {
        return get(Calendar.SECOND);
    }

    /**
     * 获取星期
     *
     * @return 星期
     */
    public static int getWeek() {
        int res = get(Calendar.DAY_OF_WEEK);
        if (res == 1)
            return 7;
        return get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 将数字转换成年份
     *
     * @param time 时间数字
     * @return 年份字符串
     */
    public static String convertToYearString(long time) {
        getDateCustomerFormat(FORMAT_YEAR + "-" + FORMAT_MONTH + "-" + FORMAT_DAY);
        return format.format(new Date(time));
    }

    /**
     * 将数字转换成时间
     *
     * @param time 时间数字
     * @return 时间字符串
     */
    public static String convertToDayString(long time) {
        getDateCustomerFormat(FORMAT_HOUR24_2UNIT + ":" + FORMAT_MINUTE + ":" + FORMAT_SECOND);
        return format.format(new Date(time));
    }

    /**
     * 将数字转换成年份时间
     *
     * @param time 时间数字
     * @return 年份时间字符串
     */
    public static String convertToYearDayString(long time) {
        getDateCustomerFormat(FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
        return format.format(new Date(time));
    }

    /**
     * 获取类型
     *
     * @return 指定类型
     */
    private static int get(int type) {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(type);
    }

    /**
     * 获取前n天日期、后n天日期
     *
     * @param distanceDay 前几天 如获取前7天日期则传-7即可；如果后7天则传7
     * @return 前几天
     */
    public static String getOldDate(int distanceDay, String pattern) {
        format.applyPattern(pattern);
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) + distanceDay);
        Date endDate = null;
        try {
            endDate = format.parse(format.format(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format.format(endDate);
    }
}