package solipsisming.util.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * 反射工具类</p>
 * 创建于 2015-12-29 19:50:10
 *
 * @author 洪东明
 * @version 1.0
 */
public class ReflectUtils {

    /**
     * 禁止创建对象
     */
    private ReflectUtils() {
        throw new UnacceptableInstanceError();
    }

    /**
     * 获取类的所有字段
     *
     * @param t   类型
     * @param <T> 泛型
     * @return 字段数组
     */
    public static <T> Field[] getFields(T t) {
        return getClass(t).getDeclaredFields();
    }

    /**
     * 获取类的指定字段
     *
     * @param t     类型
     * @param field 字段名
     * @param <T>   泛型
     * @return 字段
     */
    public static <T> Field getField(T t, String field) {
        try {
            return getClass(t).getDeclaredField(field);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取类的所有方法
     *
     * @param t   类型
     * @param <T> 泛型
     * @return 方法数组
     */
    public static <T> Method[] getMethods(T t) {
        return getClass(t).getDeclaredMethods();
    }

    /**
     * 获取类的指定方法
     *
     * @param t      类型
     * @param method 方法名
     * @param cs     类类型
     * @param <T>    泛型
     * @return 类的方法
     */
    public static <T> Method getMethod(T t, String method, Class cs) {
        try {
            return getClass(t).getDeclaredMethod(method, cs);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取类的所有构造方法
     *
     * @param t   类型
     * @param <T> 泛型
     * @return 构造方法数组
     */
    public static <T> Constructor[] getConstructors(T t) {
        return getClass(t).getConstructors();
    }

    /**
     * 获取类的构造方法
     *
     * @param t   类型
     * @param cs  类类型
     * @param <T> 泛型
     * @return 构造方法
     */
    public static <T> Constructor getConstructor(T t, Class cs) {
        try {
            getClass(t).getConstructor(cs);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取类类型
     *
     * @param t   类型
     * @param <T> 泛型
     * @return 类类型
     */
    public static <T> Class<T> getClass(T t) {
        return (Class<T>) t.getClass();
    }
}