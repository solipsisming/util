package solipsisming.util.io;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import solipsisming.util.exception.UnacceptableInstanceError;
import solipsisming.util.system.PrintLog;

/**
 * parcel工具</p>
 * 创建于 2015-6-30 23:08:01
 *
 * @author 洪东明
 * @version 1.0
 */
public class ParcelUtils {

    /**
     * 禁止实例对象
     */
    private ParcelUtils() {
        throw new UnacceptableInstanceError();
    }

    /**
     * 读取布尔值
     *
     * @param in 包裹
     * @return boolean
     */
    public synchronized static boolean readBoolean(Parcel in) {
        return in.readInt() == 1;
    }

    /**
     * 写入布尔值
     *
     * @param b   boolean
     * @param out 包裹
     */
    public synchronized static void writeBoolean(boolean b, Parcel out) {
        out.writeInt(b ? 1 : 0);
    }

    /**
     * 读取map
     *
     * @param in     包裹
     * @param loader 类加载器
     * @param <K>    键
     * @param <V>    值
     * @return map 数据
     */
    @SuppressWarnings("unchecked")
    public static <K extends Parcelable, V extends Parcelable> Map<K, V> readMap(Parcel in, ClassLoader loader) {
        if (loader == null) {
            PrintLog.logE("args loader is null");
            return null;
        }

        int size = in.readInt();
        if (size == -1) {
            PrintLog.logE("parcel's size is =-1");
            return null;
        }

        HashMap<K, V> map = new HashMap<K, V>();
        for (int i = 0; i < size; i++) {
            map.put((K) in.readParcelable(loader), (V) in.readParcelable(loader));
        }
        return map;
    }

    /**
     * 写入map
     *
     * @param map  map值
     * @param out  包裹
     * @param flag 标志
     * @param <K>  键
     * @param <V>  值
     */
    public static <K extends Parcelable, V extends Parcelable> void writeMap(Map<K, V> map, Parcel out, int flag) {
        if (map == null) {
            PrintLog.logE("args map is null");
            return;
        }

        out.writeInt(map.size());

        for (Entry<K, V> entry : map.entrySet()) {
            out.writeParcelable(entry.getKey(), flag);
            out.writeParcelable(entry.getValue(), flag);
        }
    }
}