package solipsisming.util.io;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import solipsisming.util.common.StringUtils;
import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * json工具类</p>
 * 创建于 2015-6-12 23:13:09
 *
 * @author 洪东明
 * @version 1.0
 */
public class JsonUtils {

    /**
     * 防止开发程序员创建
     */
    private JsonUtils() {
        throw new UnacceptableInstanceError();
    }

    private static final Gson gson = new Gson();//json解析器

    /**
     * 将json格式转换为对象
     *
     * @param json  json数据
     * @param clazz 对象class
     * @param <T>   对象的泛型
     * @return 对象
     * @see StringUtils 字符串工具
     */
    public synchronized static <T> T jsonToObj(String json, Class<T> clazz) {
        if (StringUtils.isTrimEmpty(json)) {
            throw new NullPointerException("parameter json is null ");
        }
        return gson.fromJson(json, clazz);
    }

    /**
     * 将json格式转换为对象集合
     *
     * @param json json数据
     * @param t    类字节
     * @param <T>  对象的泛型
     * @return 对象集合
     */
    public static <T> List<T> jsonToObjList(String json, Class<T> t) {
        if (StringUtils.isTrimEmpty(json)) {
            throw new NullPointerException("parameter json is null ");
        }
        List<T> list = new ArrayList<>();
        JsonParser parser = new JsonParser();
        JsonArray jsonarray = parser.parse(json).getAsJsonArray();
        for (JsonElement element : jsonarray) {
            list.add(gson.fromJson(element, t));
        }
        return list;
    }

    /**
     * 将对象转换为json格式
     *
     * @param t   对象
     * @param <T> 对象的泛型
     * @return json数据
     */
    public synchronized static <T> String objToJson(T t) {
        return gson.toJson(t);
    }

    /**
     * 将对象集合转换为json格式
     *
     * @param json json数据
     * @param <T>  对象的泛型
     * @return 对象集合
     * @see StringUtils 字符串工具
     */
    public synchronized static <T> List<T> jsonToObjList(String json) {
        if (StringUtils.isTrimEmpty(json)) {
            throw new NullPointerException("parameter json is null ");
        }
        List<T> ts = gson.fromJson(json, new TypeToken<List<T>>() {
        }.getType());
        return ts;
    }

    /**
     * 将json格式转换为对象集合
     *
     * @param ts  对象集合
     * @param <T> 对象的泛型
     * @return json数据
     */
    public synchronized static <T> String objListToJson(List<T> ts) {
        return gson.toJson(ts);
    }
}