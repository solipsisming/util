package solipsisming.util.io;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.net.HttpURLConnection;

import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * 关闭流工具类</p>
 * 创建于 2018-3-19 14:12:36
 *
 * @author 洪东明
 * @version 1.0
 */
public class CloseUtils {

    /**
     * 禁止实例化
     */
    private CloseUtils() {
        throw new UnacceptableInstanceError();
    }

    /**
     * 关闭游标
     *
     * @param cursor 游标
     */
    public static void closeCursor(Cursor cursor) {
        if (cursor != null)
            cursor.close();
    }

    /**
     * 关闭数据库
     *
     * @param database 数据库
     */
    public static void closeSQLiteDatabase(SQLiteDatabase database) {
        if (database != null)
            database.close();
    }

    /**
     * 关闭连接
     *
     * @param connection 连接
     */
    public static void closeConnection(HttpURLConnection connection) {
        if (connection != null)
            connection.disconnect();
    }
}