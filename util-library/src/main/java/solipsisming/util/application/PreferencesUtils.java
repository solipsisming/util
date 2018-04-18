package solipsisming.util.application;

import android.content.Context;
import android.content.SharedPreferences.Editor;

import java.util.Set;

import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * 偏好工具</p>
 * 创建于 2015-5-29 22:58:30
 *
 * @author 洪东明
 * @version 1.0
 */
public class PreferencesUtils {

    /**
     * 禁止创建对象
     */
    private PreferencesUtils() {
        throw new UnacceptableInstanceError();
    }

    /**
     * 设置偏好(boolean)
     * 默认private模式
     *
     * @param context    当前应用
     * @param preference 偏好
     * @param key        键
     * @param value      值
     */
    public static void putBooleanValue(Context context, String preference,
                                       String key, boolean value) {
        putBooleanValue(context, preference, key, value, Context.MODE_PRIVATE);
    }

    /**
     * 设置偏好(boolean)
     *
     * @param context    当前应用
     * @param preference 偏好
     * @param key        键
     * @param value      值
     * @param mode       写入模式
     */
    public static void putBooleanValue(Context context, String preference,
                                       String key, boolean value, int mode) {
        Editor editor = fetchEditor(context, preference, mode);
        editor.putBoolean(key, value).apply();
    }

    /**
     * 设置偏好(long)
     * 默认private模式
     *
     * @param context    当前应用
     * @param preference 偏好
     * @param key        键
     * @param value      值
     */
    public static void putLongValue(Context context, String preference,
                                    String key, long value) {
        putLongValue(context, preference, key, value, Context.MODE_PRIVATE);
    }

    /**
     * 设置偏好(long)
     *
     * @param context    当前应用
     * @param preference 偏好
     * @param key        键
     * @param value      值
     * @param mode       写入模式
     */
    public static void putLongValue(Context context, String preference,
                                    String key, long value, int mode) {
        Editor editor = fetchEditor(context, preference, mode);
        editor.putLong(key, value).apply();
    }

    /**
     * 设置偏好(String)
     * 默认private模式
     *
     * @param context    当前应用
     * @param preference 偏好
     * @param key        键
     * @param value      值
     */
    public static void putStringValue(Context context, String preference,
                                      String key, String value) {
        putStringValue(context, preference, key, value, Context.MODE_PRIVATE);
    }

    /**
     * 设置偏好(String)
     *
     * @param context    当前应用
     * @param preference 偏好
     * @param key        键
     * @param value      值
     * @param mode       写入模式
     */
    public static void putStringValue(Context context, String preference,
                                      String key, String value, int mode) {
        Editor editor = fetchEditor(context, preference, mode);
        editor.putString(key, value).apply();
    }

    /**
     * 设置偏好(int)
     * 默认private模式
     *
     * @param context    当前应用
     * @param preference 偏好
     * @param key        键
     * @param value      值
     */
    public static void putIntValue(Context context, String preference,
                                   String key, int value) {
        putIntValue(context, preference, key, value, Context.MODE_PRIVATE);
    }

    /**
     * 设置偏好(int)
     *
     * @param context    当前应用
     * @param preference 偏好
     * @param key        键
     * @param value      值
     * @param mode       写入模式
     */
    public static void putIntValue(Context context, String preference,
                                   String key, int value, int mode) {
        Editor editor = fetchEditor(context, preference, mode);
        editor.putInt(key, value).apply();
    }

    /**
     * 设置偏好(float)
     * 默认private模式
     *
     * @param context    当前应用
     * @param preference 偏好
     * @param key        键
     * @param value      值
     */
    public static void putFloatValue(Context context, String preference,
                                     String key, float value) {
        putFloatValue(context, preference, key, value, Context.MODE_PRIVATE);
    }

    /**
     * 设置偏好(float)
     *
     * @param context    当前应用
     * @param preference 偏好
     * @param key        键
     * @param value      值
     * @param mode       写入模式
     */
    public static void putFloatValue(Context context, String preference,
                                     String key, float value, int mode) {
        Editor editor = fetchEditor(context, preference, mode);
        editor.putFloat(key, value).apply();
    }

    /**
     * 设置偏好(Set)
     * 默认private模式
     *
     * @param context    当前应用
     * @param preference 偏好
     * @param key        键
     * @param value      值
     */
    public static void putSetValue(Context context, String preference,
                                   String key, Set<String> value) {
        putSetValue(context, preference, key, value, Context.MODE_PRIVATE);
    }

    /**
     * 设置偏好(Set)
     *
     * @param context    当前应用
     * @param preference 偏好
     * @param key        键
     * @param value      值
     * @param mode       写入模式
     */
    public static void putSetValue(Context context, String preference,
                                   String key, Set<String> value, int mode) {
        Editor editor = fetchEditor(context, preference, mode);
        editor.putStringSet(key, value).apply();
    }

    /**
     * 获取偏好(String)
     *
     * @param context      当前应用
     * @param preference   偏好
     * @param key          键
     * @param defaultValue 默认值
     * @return String值
     */
    public static String getStringValue(Context context, String preference,
                                        String key, String defaultValue) {
        return context.getSharedPreferences(preference, Context.MODE_PRIVATE)
                .getString(key, defaultValue);
    }

    /**
     * 获取偏好(boolean)
     *
     * @param context      当前应用
     * @param preference   偏好
     * @param key          键
     * @param defaultValue 默认值
     * @return boolean值
     */
    public static boolean getBooleanValue(Context context, String preference,
                                          String key, boolean defaultValue) {
        return context.getSharedPreferences(preference, Context.MODE_PRIVATE)
                .getBoolean(key, defaultValue);
    }

    /**
     * 获取偏好(long)
     *
     * @param context      当前应用
     * @param preference   偏好
     * @param key          键
     * @param defaultValue 默认值
     * @return long值
     */
    public static long getLongValue(Context context, String preference,
                                    String key, long defaultValue) {
        return context.getSharedPreferences(preference, Context.MODE_PRIVATE)
                .getLong(key, defaultValue);
    }

    /**
     * 获取偏好(int)
     *
     * @param context      当前应用
     * @param preference   偏好
     * @param key          键
     * @param defaultValue 默认值
     * @return int值
     */
    public static int getIntValue(Context context, String preference,
                                  String key, int defaultValue) {
        return context.getSharedPreferences(preference, Context.MODE_PRIVATE)
                .getInt(key, defaultValue);
    }

    /**
     * 获取偏好(float)
     *
     * @param context      当前应用
     * @param preference   偏好
     * @param key          键
     * @param defaultValue 默认值
     * @return float值
     */
    public static float getFloatValue(Context context, String preference,
                                      String key, float defaultValue) {
        return context.getSharedPreferences(preference, Context.MODE_PRIVATE)
                .getFloat(key, defaultValue);
    }

    /**
     * 获取偏好(Set)
     *
     * @param context      当前应用
     * @param preference   偏好
     * @param key          键
     * @param defaultValue 默认值
     * @return set集合
     */
    public static Set<String> getSetValue(Context context, String preference,
                                          String key, Set<String> defaultValue) {
        return context.getSharedPreferences(preference, Context.MODE_PRIVATE)
                .getStringSet(key, defaultValue);
    }

    /**
     * 获取编辑
     *
     * @param context    当前应用
     * @param preference 偏好
     * @param mode       模式
     * @return 编辑
     */
    private static Editor fetchEditor(Context context, String preference, int mode) {
        return context.getSharedPreferences(preference, mode).edit();
    }

    /**
     * 清空偏好
     *
     * @param context    当前应用
     * @param preference 偏好
     */
    public static void clearPreferences(Context context, String preference) {
        Editor editor = fetchEditor(context, preference, Context.MODE_PRIVATE);
        editor.clear().apply();
    }
}