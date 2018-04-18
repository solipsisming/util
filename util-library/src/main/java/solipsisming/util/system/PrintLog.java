package solipsisming.util.system;

import android.os.StrictMode;
import android.util.Log;

import java.util.Arrays;

import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * 日志打印</p>
 * 创建于 2015-5-29 19:14:38
 *
 * @author 洪东明
 * @version 1.0
 */
public class PrintLog {
    /**
     * 是否为开发模式
     */
    public static boolean develop = true;

    /**
     * 日志的tag为默认包名
     */
    public static String TAG = PrintLog.class.getName();

    /**
     * 禁止创建对象
     */
    private PrintLog() {
        throw new UnacceptableInstanceError();
    }

    /**
     * 打印日志(i)
     *
     * @param tag 标记
     * @param msg 打印消息
     */
    public static void logI(String tag, Object msg) {
        if (develop) {
            if (msg == null)
                msg = "null";
            if (tag == null)
                tag = TAG;

            String text = msg.toString();

            //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
            //  把4*1024的MAX字节打印长度改为2001字符数
            int max_str_length = 2001 - tag.length();
            //大于4000时
            while (text.length() > max_str_length) {
                logI(tag, text.substring(0, max_str_length));
                text = text.substring(max_str_length);
            }
            Log.i(tag, text); //剩余部分
        }
    }

    /**
     * 打印日志(i)
     */
    public static void logI() {
        logI(TAG, "I--------------------------------------");
    }

    /**
     * 打印日志(i)
     *
     * @param tag 标记
     * @param os  打印消息
     */
    public static void logI(String tag, Object[] os) {
        logI(tag, Arrays.deepToString(os));
    }

    /**
     * 打印日志(i)
     *
     * @param o 打印消息
     */
    public static void logI(Object o) {
        logI(TAG, o);
    }

    /**
     * 打印日志(i)
     *
     * @param s 打印消息
     */
    public static void logI(short s) {
        logI(TAG, s);
    }

    /**
     * 打印日志(i)
     *
     * @param b 打印消息
     */
    public static void logI(byte b) {
        logI(TAG, b);
    }

    /**
     * 打印日志(i)
     *
     * @param c 打印消息
     */
    public static void logI(char c) {
        logI(TAG, c);
    }

    /**
     * 打印日志(i)
     *
     * @param i 打印消息
     */
    public static void logI(int i) {
        logI(TAG, i);
    }

    /**
     * 打印日志(i)
     *
     * @param l 打印消息
     */
    public static void logI(long l) {
        logI(TAG, l);
    }

    /**
     * 打印日志(i)
     *
     * @param f 打印消息
     */
    public static void logI(float f) {
        logI(TAG, f);
    }

    /**
     * 打印日志(i)
     *
     * @param d 打印消息
     */
    public static void logI(double d) {
        logI(TAG, d);
    }

    /**
     * 打印日志(i)
     *
     * @param b 打印消息
     */
    public static void logI(boolean b) {
        logI(TAG, b);
    }

    /**
     * 打印日志(i)
     *
     * @param s 打印消息
     */
    public static void logI(String s) {
        logI(TAG, s);
    }

    /**
     * 打印日志(i)
     *
     * @param os 打印消息
     */
    public static void logVarI(Object... os) {
        logI(TAG, Arrays.deepToString(os));
    }

    /**
     * 打印日志(i)
     *
     * @param tag 标记
     * @param os  打印消息
     */
    public static void logVarI(String tag, Object... os) {
        logI(tag, Arrays.deepToString(os));
    }

    /**
     * 打印日志(i)
     *
     * @param os 打印消息
     */
    public static void logI(Object[] os) {
        logI(TAG, Arrays.deepToString(os));
    }

    /**
     * 打印日志(i)
     *
     * @param bs 打印消息
     */
    public static void logI(byte[] bs) {
        logI(TAG, Arrays.toString(bs));
    }

    /**
     * 打印日志(i)
     *
     * @param tag 标记
     * @param bs  打印消息
     */
    public static void logI(String tag, byte[] bs) {
        logI(tag, Arrays.toString(bs));
    }

    /**
     * 打印日志(i)
     *
     * @param is 打印消息
     */
    public static void logI(int[] is) {
        logI(TAG, Arrays.toString(is));
    }

    /**
     * 打印日志(i)
     *
     * @param tag 标记
     * @param is  打印消息
     */
    public static void logI(String tag, int[] is) {
        logI(tag, Arrays.toString(is));
    }

    /**
     * 打印日志(i)
     *
     * @param cs 打印消息
     */
    public static void logI(char[] cs) {
        logI(TAG, Arrays.toString(cs));
    }

    /**
     * 打印日志(i)
     *
     * @param tag 标记
     * @param cs  打印消息
     */
    public static void logI(String tag, char[] cs) {
        logI(tag, Arrays.toString(cs));
    }

    /**
     * 打印日志(i)
     *
     * @param ls 打印消息
     */
    public static void logI(long[] ls) {
        logI(TAG, Arrays.toString(ls));
    }

    /**
     * 打印日志(i)
     *
     * @param tag 标记
     * @param ls  打印消息
     */
    public static void logI(String tag, long[] ls) {
        logI(tag, Arrays.toString(ls));
    }

    /**
     * 打印日志(i)
     *
     * @param bs 打印消息
     */
    public static void logI(boolean[] bs) {
        logI(TAG, Arrays.toString(bs));
    }

    /**
     * 打印日志(i)
     *
     * @param tag 标记
     * @param bs  打印消息
     */
    public static void logI(String tag, boolean[] bs) {
        logI(tag, Arrays.toString(bs));
    }

    /**
     * 打印日志(i)
     *
     * @param fs 打印消息
     */
    public static void logI(float[] fs) {
        logI(TAG, Arrays.toString(fs));
    }

    /**
     * 打印日志(i)
     *
     * @param tag 标记
     * @param fs  打印消息
     */
    public static void logI(String tag, float[] fs) {
        logI(tag, Arrays.toString(fs));
    }

    /**
     * 打印日志(i)
     *
     * @param ds 打印消息
     */
    public static void logI(double[] ds) {
        logI(TAG, Arrays.toString(ds));
    }

    /**
     * 打印日志(i)
     *
     * @param tag 标记
     * @param ds  打印消息
     */
    public static void logI(String tag, double[] ds) {
        logI(tag, Arrays.toString(ds));
    }

    /**
     * 打印日志(i)
     *
     * @param ds 打印消息
     */
    public static void logI(short[] ds) {
        logI(TAG, Arrays.toString(ds));
    }

    /**
     * 打印日志(i)
     *
     * @param tag 标记
     * @param ds  打印消息
     */
    public static void logI(String tag, short[] ds) {
        logI(tag, Arrays.toString(ds));
    }

    /**
     * 打印日志(i)
     *
     * @param tag 标记
     * @param msg 打印消息
     */
    public static void logV(String tag, Object msg) {
        if (develop) {
            if (msg == null)
                msg = "null";
            if (tag == null)
                tag = TAG;

            String text = msg.toString();

            //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
            //  把4*1024的MAX字节打印长度改为2001字符数
            int max_str_length = 2001 - tag.length();
            //大于4000时
            while (text.length() > max_str_length) {
                logV(tag, text.substring(0, max_str_length));
                text = text.substring(max_str_length);
            }
            Log.v(tag, text); //剩余部分
        }
    }

    /**
     * 打印日志(V)
     */
    public static void logV() {
        logV(TAG, "V--------------------------------------");
    }

    /**
     * 打印日志(i)
     *
     * @param tag 标记
     * @param os  打印消息
     */
    public static void logV(String tag, Object[] os) {
        logV(tag, Arrays.deepToString(os));
    }

    /**
     * 打印日志(v)
     *
     * @param o 打印消息
     */
    public static void logV(Object o) {
        logV(TAG, o);
    }

    /**
     * 打印日志(v)
     *
     * @param s 打印消息
     */
    public static void logV(short s) {
        logV(TAG, s);
    }

    /**
     * 打印日志(v)
     *
     * @param b 打印消息
     */
    public static void logV(byte b) {
        logV(TAG, b);
    }

    /**
     * 打印日志(v)
     *
     * @param c 打印消息
     */
    public static void logV(char c) {
        logV(TAG, c);
    }

    /**
     * 打印日志(v)
     *
     * @param i 打印消息
     */
    public static void logV(int i) {
        logV(TAG, i);
    }

    /**
     * 打印日志(v)
     *
     * @param l 打印消息
     */
    public static void logV(long l) {
        logV(TAG, l);
    }

    /**
     * 打印日志(v)
     *
     * @param f 打印消息
     */
    public static void logV(float f) {
        logV(TAG, f);
    }

    /**
     * 打印日志(v)
     *
     * @param d 打印消息
     */
    public static void logV(double d) {
        logV(TAG, d);
    }

    /**
     * 打印日志(v)
     *
     * @param b 打印消息
     */
    public static void logV(boolean b) {
        logV(TAG, b);
    }

    /**
     * 打印日志(v)
     *
     * @param s 打印消息
     */
    public static void logV(String s) {
        logV(TAG, s);
    }

    /**
     * 打印日志(v)
     *
     * @param os 打印消息
     */
    public static void logV(Object[] os) {
        logV(TAG, Arrays.deepToString(os));
    }

    /**
     * 打印日志(v)
     *
     * @param os 打印消息
     */
    public static void logVarV(Object... os) {
        logV(TAG, Arrays.deepToString(os));
    }

    /**
     * 打印日志(v)
     *
     * @param tag 标记
     * @param os  打印消息
     */
    public static void logVarV(String tag, Object... os) {
        logV(tag, Arrays.deepToString(os));
    }

    /**
     * 打印日志(v)
     *
     * @param bs 打印消息
     */
    public static void logV(byte[] bs) {
        logV(TAG, Arrays.toString(bs));
    }

    /**
     * 打印日志(v)
     *
     * @param tag 标记
     * @param bs  打印消息
     */
    public static void logV(String tag, byte[] bs) {
        logV(tag, Arrays.toString(bs));
    }

    /**
     * 打印日志(v)
     *
     * @param is 打印消息
     */
    public static void logV(int[] is) {
        logV(TAG, Arrays.toString(is));
    }

    /**
     * 打印日志(v)
     *
     * @param tag 标记
     * @param is  打印消息
     */
    public static void logV(String tag, int[] is) {
        logV(tag, Arrays.toString(is));
    }

    /**
     * 打印日志(v)
     *
     * @param cs 打印消息
     */
    public static void logV(char[] cs) {
        logV(TAG, Arrays.toString(cs));
    }

    /**
     * 打印日志(v)
     *
     * @param tag 标记
     * @param cs  打印消息
     */
    public static void logV(String tag, char[] cs) {
        logV(tag, Arrays.toString(cs));
    }

    /**
     * 打印日志(v)
     *
     * @param ls 打印消息
     */
    public static void logV(long[] ls) {
        logV(TAG, Arrays.toString(ls));
    }

    /**
     * 打印日志(v)
     *
     * @param tag 标记
     * @param ls  打印消息
     */
    public static void logV(String tag, long[] ls) {
        logV(tag, Arrays.toString(ls));
    }


    /**
     * 打印日志(v)
     *
     * @param bs 打印消息
     */
    public static void logV(boolean[] bs) {
        logV(TAG, Arrays.toString(bs));
    }

    /**
     * 打印日志(v)
     *
     * @param tag 标记
     * @param bs  打印消息
     */
    public static void logV(String tag, boolean[] bs) {
        logV(tag, Arrays.toString(bs));
    }


    /**
     * 打印日志(v)
     *
     * @param fs 打印消息
     */
    public static void logV(float[] fs) {
        logV(TAG, Arrays.toString(fs));
    }

    /**
     * 打印日志(v)
     *
     * @param tag 标记
     * @param fs  打印消息
     */
    public static void logV(String tag, float[] fs) {
        logV(tag, Arrays.toString(fs));
    }

    /**
     * 打印日志(v)
     *
     * @param ds 打印消息
     */
    public static void logV(double[] ds) {
        logV(TAG, Arrays.toString(ds));
    }

    /**
     * 打印日志(v)
     *
     * @param tag 标记
     * @param ds  打印消息
     */
    public static void logV(String tag, double[] ds) {
        logV(tag, Arrays.toString(ds));
    }

    /**
     * 打印日志(v)
     *
     * @param ds 打印消息
     */
    public static void logV(short[] ds) {
        logV(TAG, Arrays.toString(ds));
    }

    /**
     * 打印日志(v)
     *
     * @param tag 标记
     * @param ds  打印消息
     */
    public static void logV(String tag, short[] ds) {
        logV(tag, Arrays.toString(ds));
    }


    /**
     * 打印日志(d)
     *
     * @param tag 标记
     * @param msg 打印消息
     */
    public static void logD(String tag, Object msg) {
        if (develop) {
            if (msg == null)
                msg = "null";
            if (tag == null)
                tag = TAG;

            String text = msg.toString();

            //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
            //  把4*1024的MAX字节打印长度改为2001字符数
            int max_str_length = 2001 - tag.length();
            //大于4000时
            while (text.length() > max_str_length) {
                logD(tag, text.substring(0, max_str_length));
                text = text.substring(max_str_length);
            }
            Log.d(tag, text); //剩余部分
        }
    }

    /**
     * 打印日志(d)
     */
    public static void logD() {
        logD(TAG, "D--------------------------------------");
    }

    /**
     * 打印日志(i)
     *
     * @param tag 标记
     * @param os  打印消息
     */
    public static void logD(String tag, Object[] os) {
        logD(tag, Arrays.deepToString(os));
    }

    /**
     * 打印日志(d)
     *
     * @param o 打印消息
     */
    public static void logD(Object o) {
        logD(TAG, o);
    }

    /**
     * 打印日志(d)
     *
     * @param s 打印消息
     */
    public static void logD(short s) {
        logD(TAG, s);
    }

    /**
     * 打印日志(d)
     *
     * @param b 打印消息
     */
    public static void logD(byte b) {
        logD(TAG, b);
    }

    /**
     * 打印日志(d)
     *
     * @param c 打印消息
     */
    public static void logD(char c) {
        logD(TAG, c);
    }

    /**
     * 打印日志(d)
     *
     * @param i 打印消息
     */
    public static void logD(int i) {
        logD(TAG, i);
    }

    /**
     * 打印日志(d)
     *
     * @param l 打印消息
     */
    public static void logD(long l) {
        logD(TAG, l);
    }

    /**
     * 打印日志(d)
     *
     * @param f 打印消息
     */
    public static void logD(float f) {
        logD(TAG, f);
    }

    /**
     * 打印日志(d)
     *
     * @param d 打印消息
     */
    public static void logD(double d) {
        logD(TAG, d);
    }

    /**
     * 打印日志(d)
     *
     * @param b 打印消息
     */
    public static void logD(boolean b) {
        logD(TAG, b);
    }

    /**
     * 打印日志(d)
     *
     * @param s 打印消息
     */
    public static void logD(String s) {
        logD(TAG, s);
    }

    /**
     * 打印日志(d)
     *
     * @param os 打印消息
     */
    public static void logD(Object[] os) {
        logD(TAG, Arrays.deepToString(os));
    }

    /**
     * 打印日志(d)
     *
     * @param os 打印消息
     */
    public static void logVarD(Object... os) {
        logD(TAG, Arrays.deepToString(os));
    }

    /**
     * 打印日志(d)
     *
     * @param tag 标记
     * @param os  打印消息
     */
    public static void logVarD(String tag, Object... os) {
        logD(tag, Arrays.deepToString(os));
    }

    /**
     * 打印日志(d)
     *
     * @param bs 打印消息
     */
    public static void logD(byte[] bs) {
        logD(TAG, Arrays.toString(bs));
    }

    /**
     * 打印日志(d)
     *
     * @param tag 标记
     * @param bs  打印消息
     */
    public static void logD(String tag, byte[] bs) {
        logD(tag, Arrays.toString(bs));
    }

    /**
     * 打印日志(d)
     *
     * @param is 打印消息
     */
    public static void logD(int[] is) {
        logD(TAG, Arrays.toString(is));
    }

    /**
     * 打印日志(d)
     *
     * @param tag 标记
     * @param is  打印消息
     */
    public static void logD(String tag, int[] is) {
        logD(tag, Arrays.toString(is));
    }

    /**
     * 打印日志(d)
     *
     * @param cs 打印消息
     */
    public static void logD(char[] cs) {
        logD(TAG, Arrays.toString(cs));
    }

    /**
     * 打印日志(d)
     *
     * @param tag 标记
     * @param cs  打印消息
     */
    public static void logD(String tag, char[] cs) {
        logD(tag, Arrays.toString(cs));
    }

    /**
     * 打印日志(d)
     *
     * @param ls 打印消息
     */
    public static void logD(long[] ls) {
        logD(TAG, Arrays.toString(ls));
    }

    /**
     * 打印日志(d)
     *
     * @param tag 标记
     * @param ls  打印消息
     */
    public static void logD(String tag, long[] ls) {
        logD(tag, Arrays.toString(ls));
    }

    /**
     * 打印日志(d)
     *
     * @param bs 打印消息
     */
    public static void logD(boolean[] bs) {
        logD(TAG, Arrays.toString(bs));
    }

    /**
     * 打印日志(d)
     *
     * @param tag 标记
     * @param bs  打印消息
     */
    public static void logD(String tag, boolean[] bs) {
        logD(tag, Arrays.toString(bs));
    }

    /**
     * 打印日志(d)
     *
     * @param fs 打印消息
     */
    public static void logD(float[] fs) {
        logD(TAG, Arrays.toString(fs));
    }

    /**
     * 打印日志(d)
     *
     * @param tag 标记
     * @param fs  打印消息
     */
    public static void logD(String tag, float[] fs) {
        logD(tag, Arrays.toString(fs));
    }

    /**
     * 打印日志(d)
     *
     * @param ds 打印消息
     */
    public static void logD(double[] ds) {
        logD(TAG, Arrays.toString(ds));
    }

    /**
     * 打印日志(d)
     *
     * @param tag 标记
     * @param ds  打印消息
     */
    public static void logD(String tag, double[] ds) {
        logD(tag, Arrays.toString(ds));
    }

    /**
     * 打印日志(d)
     *
     * @param ds 打印消息
     */
    public static void logD(short[] ds) {
        logD(TAG, Arrays.toString(ds));
    }

    /**
     * 打印日志(d)
     *
     * @param tag 标记
     * @param ds  打印消息
     */
    public static void logD(String tag, short[] ds) {
        logD(tag, Arrays.toString(ds));
    }


    /**
     * 打印日志(e)
     *
     * @param tag 标记
     * @param msg 打印消息
     */
    public static void logE(String tag, Object msg) {
        if (develop) {
            if (msg == null)
                msg = "null";
            if (tag == null)
                tag = TAG;

            String text = msg.toString();

            //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
            //  把4*1024的MAX字节打印长度改为2001字符数
            int max_str_length = 2001 - tag.length();
            //大于4000时
            while (text.length() > max_str_length) {
                logE(tag, text.substring(0, max_str_length));
                text = text.substring(max_str_length);
            }
            Log.e(tag, text); //剩余部分
        }
    }

    /**
     * 打印日志(e)
     */
    public static void logE() {
        logE(TAG, "E--------------------------------------");
    }


    /**
     * 打印日志(i)
     *
     * @param tag 标记
     * @param os  打印消息
     */
    public static void logE(String tag, Object[] os) {
        logE(tag, Arrays.deepToString(os));
    }

    /**
     * 打印日志(e)
     *
     * @param o 打印消息
     */
    public static void logE(Object o) {
        logE(TAG, o);
    }

    /**
     * 打印日志(e)
     *
     * @param s 打印消息
     */
    public static void logE(short s) {
        logE(TAG, s);
    }

    /**
     * 打印日志(e)
     *
     * @param b 打印消息
     */
    public static void logE(byte b) {
        logE(TAG, b);
    }

    /**
     * 打印日志(e)
     *
     * @param c 打印消息
     */
    public static void logE(char c) {
        logE(TAG, c);
    }

    /**
     * 打印日志(e)
     *
     * @param i 打印消息
     */
    public static void logE(int i) {
        logE(TAG, i);
    }

    /**
     * 打印日志(e)
     *
     * @param l 打印消息
     */
    public static void logE(long l) {
        logE(TAG, l);
    }

    /**
     * 打印日志(e)
     *
     * @param f 打印消息
     */
    public static void logE(float f) {
        logE(TAG, f);
    }

    /**
     * 打印日志(e)
     *
     * @param d 打印消息
     */
    public static void logE(double d) {
        logE(TAG, d);
    }

    /**
     * 打印日志(e)
     *
     * @param b 打印消息
     */
    public static void logE(boolean b) {
        logE(TAG, b);
    }

    /**
     * 打印日志(e)
     *
     * @param s 打印消息
     */
    public static void logE(String s) {
        logE(TAG, s);
    }

    /**
     * 打印日志(e)
     *
     * @param os 打印消息
     */
    public static void logE(Object[] os) {
        logE(TAG, Arrays.deepToString(os));
    }

    /**
     * 打印日志(e)
     *
     * @param os 打印消息
     */
    public static void logVarE(Object... os) {
        logE(TAG, Arrays.deepToString(os));
    }

    /**
     * 打印日志(e)
     *
     * @param tag 标记
     * @param os  打印消息
     */
    public static void logVarE(String tag, Object... os) {
        logE(tag, Arrays.deepToString(os));
    }

    /**
     * 打印日志(e)
     *
     * @param bs 打印消息
     */
    public static void logE(byte[] bs) {
        logE(TAG, Arrays.toString(bs));
    }

    /**
     * 打印日志(e)
     *
     * @param tag 标记
     * @param bs  打印消息
     */
    public static void logE(String tag, byte[] bs) {
        logE(tag, Arrays.toString(bs));
    }

    /**
     * 打印日志(e)
     *
     * @param is 打印消息
     */
    public static void logE(int[] is) {
        logE(TAG, Arrays.toString(is));
    }

    /**
     * 打印日志(e)
     *
     * @param tag 标记
     * @param is  打印消息
     */
    public static void logE(String tag, int[] is) {
        logE(tag, Arrays.toString(is));
    }

    /**
     * 打印日志(e)
     *
     * @param cs 打印消息
     */
    public static void logE(char[] cs) {
        logE(TAG, Arrays.toString(cs));
    }

    /**
     * 打印日志(e)
     *
     * @param tag 标记
     * @param cs  打印消息
     */
    public static void logE(String tag, char[] cs) {
        logE(tag, Arrays.toString(cs));
    }

    /**
     * 打印日志(e)
     *
     * @param ls 打印消息
     */
    public static void logE(long[] ls) {
        logE(TAG, Arrays.toString(ls));
    }

    /**
     * 打印日志(e)
     *
     * @param tag 标记
     * @param ls  打印消息
     */
    public static void logE(String tag, long[] ls) {
        logE(tag, Arrays.toString(ls));
    }

    /**
     * 打印日志(e)
     *
     * @param bs 打印消息
     */
    public static void logE(boolean[] bs) {
        logE(TAG, Arrays.toString(bs));
    }

    /**
     * 打印日志(e)
     *
     * @param tag 标记
     * @param bs  打印消息
     */
    public static void logE(String tag, boolean[] bs) {
        logE(tag, Arrays.toString(bs));
    }


    /**
     * 打印日志(e)
     *
     * @param fs 打印消息
     */
    public static void logE(float[] fs) {
        logE(TAG, Arrays.toString(fs));
    }

    /**
     * 打印日志(e)
     *
     * @param tag 标记
     * @param fs  打印消息
     */
    public static void logE(String tag, float[] fs) {
        logE(tag, Arrays.toString(fs));
    }


    /**
     * 打印日志(e)
     *
     * @param ds 打印消息
     */
    public static void logE(double[] ds) {
        logE(TAG, Arrays.toString(ds));
    }

    /**
     * 打印日志(e)
     *
     * @param tag 标记
     * @param ds  打印消息
     */
    public static void logE(String tag, double[] ds) {
        logE(tag, Arrays.toString(ds));
    }

    /**
     * 打印日志(e)
     *
     * @param ds 打印消息
     */
    public static void logE(short[] ds) {
        logE(TAG, Arrays.toString(ds));
    }

    /**
     * 打印日志(e)
     *
     * @param tag 标记
     * @param ds  打印消息
     */
    public static void logE(String tag, short[] ds) {
        logE(tag, Arrays.toString(ds));
    }


    /**
     * 打印日志(w)
     *
     * @param tag 标记
     * @param msg 打印消息
     */
    public static void logW(String tag, Object msg) {
        if (develop) {
            if (msg == null)
                msg = "null";
            if (tag == null)
                tag = TAG;

            String text = msg.toString();

            //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
            //  把4*1024的MAX字节打印长度改为2001字符数
            int max_str_length = 2001 - tag.length();
            //大于4000时
            while (text.length() > max_str_length) {
                logW(tag, text.substring(0, max_str_length));
                text = text.substring(max_str_length);
            }
            Log.w(tag, text); //剩余部分
        }
    }

    /**
     * 打印日志(w)
     */
    public static void logW() {
        logW(TAG, "W--------------------------------------");
    }

    /**
     * 打印日志(i)
     *
     * @param tag 标记
     * @param os  打印消息
     */
    public static void logW(String tag, Object[] os) {
        logW(tag, Arrays.deepToString(os));
    }

    /**
     * 打印日志(w)
     *
     * @param o 打印消息
     */
    public static void logW(Object o) {
        logW(TAG, o);
    }

    /**
     * 打印日志(w)
     *
     * @param s 打印消息
     */
    public static void logW(short s) {
        logW(TAG, s);
    }

    /**
     * 打印日志(w)
     *
     * @param b 打印消息
     */
    public static void logW(byte b) {
        logW(TAG, b);
    }

    /**
     * 打印日志(w)
     *
     * @param c 打印消息
     */
    public static void logW(char c) {
        logW(TAG, c);
    }

    /**
     * 打印日志(w)
     *
     * @param i 打印消息
     */
    public static void logW(int i) {
        logW(TAG, i);
    }

    /**
     * 打印日志(w)
     *
     * @param l 打印消息
     */
    public static void logW(long l) {
        logW(TAG, l);
    }

    /**
     * 打印日志(w)
     *
     * @param f 打印消息
     */
    public static void logW(float f) {
        logW(TAG, f);
    }

    /**
     * 打印日志(w)
     *
     * @param d 打印消息
     */
    public static void logW(double d) {
        logW(TAG, d);
    }

    /**
     * 打印日志(w)
     *
     * @param b 打印消息
     */
    public static void logW(boolean b) {
        logW(TAG, b);
    }

    /**
     * 打印日志(w)
     *
     * @param s 打印消息
     */
    public static void logW(String s) {
        logW(TAG, s);
    }

    /**
     * 打印日志(w)
     *
     * @param os 打印消息
     */
    public static void logW(Object[] os) {
        logW(TAG, Arrays.deepToString(os));
    }

    /**
     * 打印日志(w)
     *
     * @param os 打印消息
     */
    public static void logVarW(Object... os) {
        logW(TAG, Arrays.deepToString(os));
    }

    /**
     * 打印日志(w)
     *
     * @param tag 标记
     * @param os  打印消息
     */
    public static void logVarW(String tag, Object... os) {
        logW(tag, Arrays.deepToString(os));
    }

    /**
     * 打印日志(w)
     *
     * @param bs 打印消息
     */
    public static void logW(byte[] bs) {
        logW(TAG, Arrays.toString(bs));
    }

    /**
     * 打印日志(w)
     *
     * @param tag 标记
     * @param bs  打印消息
     */
    public static void logW(String tag, byte[] bs) {
        logW(tag, Arrays.toString(bs));
    }


    /**
     * 打印日志(w)
     *
     * @param is 打印消息
     */
    public static void logW(int[] is) {
        logW(TAG, Arrays.toString(is));
    }

    /**
     * 打印日志(w)
     *
     * @param tag 标记
     * @param is  打印消息
     */
    public static void logW(String tag, int[] is) {
        logW(tag, Arrays.toString(is));
    }

    /**
     * 打印日志(w)
     *
     * @param cs 打印消息
     */
    public static void logW(char[] cs) {
        logW(TAG, Arrays.toString(cs));
    }

    /**
     * 打印日志(w)
     *
     * @param tag 标记
     * @param cs  打印消息
     */
    public static void logW(String tag, char[] cs) {
        logW(tag, Arrays.toString(cs));
    }

    /**
     * 打印日志(w)
     *
     * @param ls 打印消息
     */
    public static void logW(long[] ls) {
        logW(TAG, Arrays.toString(ls));
    }

    /**
     * 打印日志(w)
     *
     * @param tag 标记
     * @param ls  打印消息
     */
    public static void logW(String tag, long[] ls) {
        logW(tag, Arrays.toString(ls));
    }

    /**
     * 打印日志(w)
     *
     * @param bs 打印消息
     */
    public static void logW(boolean[] bs) {
        logW(TAG, Arrays.toString(bs));
    }

    /**
     * 打印日志(w)
     *
     * @param tag 标记
     * @param bs  打印消息
     */
    public static void logW(String tag, boolean[] bs) {
        logW(tag, Arrays.toString(bs));
    }

    /**
     * 打印日志(w)
     *
     * @param fs 打印消息
     */
    public static void logW(float[] fs) {
        logW(TAG, Arrays.toString(fs));
    }

    /**
     * 打印日志(w)
     *
     * @param tag 标记
     * @param fs  打印消息
     */
    public static void logW(String tag, float[] fs) {
        logW(tag, Arrays.toString(fs));
    }


    /**
     * 打印日志(w)
     *
     * @param ds 打印消息
     */
    public static void logW(double[] ds) {
        logW(TAG, Arrays.toString(ds));
    }

    /**
     * 打印日志(w)
     *
     * @param tag 标记
     * @param ds  打印消息
     */
    public static void logW(String tag, double[] ds) {
        logW(tag, Arrays.toString(ds));
    }

    /**
     * 打印日志(w)
     *
     * @param ds 打印消息
     */
    public static void logW(short[] ds) {
        logW(TAG, Arrays.toString(ds));
    }

    /**
     * 打印日志(w)
     *
     * @param tag 标记
     * @param ds  打印消息
     */
    public static void logW(String tag, short[] ds) {
        logW(tag, Arrays.toString(ds));
    }

    /**
     * 打印错误日志
     */
    public static void logErr() {
        logW(TAG, "Err--------------------------------------");
    }

    /**
     * 打印错误日志
     *
     * @param tr 异常
     */
    public static void logErr(Throwable... tr) {
        logErr(TAG, tr);
    }

    /**
     * 打印错误日志
     *
     * @param tag 标记
     * @param tr  异常
     */
    public static void logErr(String tag, Throwable... tr) {
        for (Throwable throwable : tr)
            logErr(tag, throwable);
    }

    /**
     * 打印错误日志
     *
     * @param tag 标记
     * @param tr  异常
     */
    public static void logErr(String tag, Throwable tr) {
        logE(tag, Log.getStackTraceString(tr));
    }

    /**
     * 打开应用性能检测
     * 线程策略（ThreadPolicy）不同的是，虚拟机策略（VmPolicy）不能通过一个对话框提供警告。
     */
    public static void addMonitor() {
        if (develop) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() //构造StrictMode
                    .detectDiskReads() //当发生磁盘读操作时输出
                    .detectDiskWrites()//当发生磁盘写操作时输出
                    .detectNetwork()  //访问网络时输出，这里可以替换为detectAll() 就包括了磁盘读写和网络I/O
                    .penaltyLog()  //以日志的方式输出
                    .build());

            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects() //探测SQLite数据库操作
                    .penaltyLog() //以日志的方式输出
                    .penaltyDeath()//一旦StrictMode消息被写到LogCat后应用就会崩溃。
                    .build());
        }
    }
}