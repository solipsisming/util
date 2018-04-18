package solipsisming.util.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * 序列化工具</p>
 * 创建于 2015-6-18 20:32:28
 *
 * @author 洪东明
 * @version 1.0
 */
public class SerializableUtils {

    /**
     * 禁止实例对象
     */
    private SerializableUtils() {
        throw new UnacceptableInstanceError();
    }

    /**
     * 反序列化对象
     *
     * @param filePath 文件路径
     * @param <T>      对象
     * @return 对象
     * @throws IOException            关闭异常
     * @throws ClassNotFoundException 找不到该类
     * @see EasyIO 简单流工具
     */
    public static <T> T deserialization(String filePath) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(filePath));
            return (T) ois.readObject();
        } finally {
            EasyIO.close(ois);
        }
    }

    /**
     * 序列化对象
     *
     * @param filePath 文件路径
     * @param t        对象
     * @param <T>      对象
     * @throws IOException 关闭异常
     * @see EasyIO 简单流工具
     */
    public static <T> void serialization(String filePath, T t) throws IOException {
        ObjectOutputStream out = null;
        try {
            Class<?>[] clas = t.getClass().getInterfaces();
            for (Class cl : clas) {
                if (cl.getClass().isAssignableFrom(Serializable.class)) {
                    out = new ObjectOutputStream(new FileOutputStream(filePath));
                    out.writeObject(t);
                    return;
                }
            }
            throw new IOException("the object not impl Serializable");
        } finally {
            EasyIO.close(out);
        }
    }
}