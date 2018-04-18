package solipsisming.util.collections;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.ResourceBundle;

import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * map集合工具</p>
 * 创建于 2015-07-11 10:14:36
 *
 * @author 洪东明
 * @version 1.0
 */
public class MapUtils {

    private MapUtils() {
        throw new UnacceptableInstanceError();
    }

    /**
     * 将map集合转化为配置集合
     *
     * @param map map集合
     * @param <K> 键
     * @param <V> 值
     * @return 配置集合
     */
    public static <K, V> Properties toProperties(final Map<K, V> map) {
        final Properties answer = new Properties();
        if (map != null) {
            for (final Entry<K, V> entry2 : map.entrySet()) {
                final Object key = entry2.getKey();
                final Object value = entry2.getValue();
                answer.put(key, value);
            }
        }
        return answer;
    }

    /**
     * 将resourceBundle转换为map集合
     * <p/>
     * ResourceBundle类通常是用于针对不同的语言来使用的属性文件
     *
     * @param resourceBundle 属性文件
     * @return map集合
     */
    public static Map<String, Object> toMap(final ResourceBundle resourceBundle) {
        final Enumeration<String> enumeration = resourceBundle.getKeys();
        final Map<String, Object> map = new HashMap<String, Object>();

        while (enumeration.hasMoreElements()) {
            final String key = enumeration.nextElement();
            final Object value = resourceBundle.getObject(key);
            map.put(key, value);
        }

        return map;
    }

    /**
     * 线程安全的集合
     *
     * @param map map集合
     * @param <K> 键
     * @param <V> 值
     * @return 线程安全的集合
     */
    public static <K, V> Map<K, V> synchronizedMap(final Map<K, V> map) {
        return Collections.synchronizedMap(map);
    }

    /**
     * 不可修改的集合
     *
     * @param map map集合
     * @param <K> 键
     * @param <V> 值
     * @return 不可修改的集合
     */
    public static <K, V> Map<K, V> unmodifiableMap(final Map<? extends K, ? extends V> map) {
        return Collections.unmodifiableMap(map);
    }
}