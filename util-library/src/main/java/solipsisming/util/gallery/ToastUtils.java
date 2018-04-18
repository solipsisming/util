package solipsisming.util.gallery;

import android.content.Context;
import android.widget.Toast;

import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * Toast显示</p>
 * 创建于 2015-5-29 20:18:55
 *
 * @author 洪东明
 * @version 1.0
 */
public class ToastUtils {

    /**
     * 禁止创建对象
     */
    private ToastUtils() {
        throw new UnacceptableInstanceError();
    }

    /**
     * toast 显示（短）
     *
     * @param context 当前应用
     * @param msg     消息
     */
    public static void displayShort(Context context, CharSequence msg) {
        Toast.makeText(context, String.valueOf(msg), Toast.LENGTH_SHORT).show();
    }

    /**
     * toast 显示（长）
     *
     * @param context 当前应用
     * @param msg     消息
     */
    public static void displayLong(Context context, CharSequence msg) {
        Toast.makeText(context, String.valueOf(msg), Toast.LENGTH_LONG).show();
    }
}