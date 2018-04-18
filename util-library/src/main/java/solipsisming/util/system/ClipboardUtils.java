package solipsisming.util.system;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * 剪贴板工具
 * <p/>
 * 创建于 2015-7-9 20:21:09
 *
 * @author 洪东明
 * @version 1.0
 */
public class ClipboardUtils {

    /**
     * 禁止创建对象
     */
    private ClipboardUtils() {
        throw new UnacceptableInstanceError();
    }

    /**
     * 将文本复制到件铁棒
     *
     * @param context 当前应用
     * @param text    文本
     */
    public static void copy(Context context, String text) {
        ClipboardManager myClipboard = (ClipboardManager)
                context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData myClip = ClipData.newPlainText("text", text);
        myClipboard.setPrimaryClip(myClip);
    }

    /**
     * 获取剪贴板的文本
     *
     * @param context 当前应用
     * @return 文本信息
     */
    public static String past(Context context) {
        ClipboardManager myClipboard = (ClipboardManager)
                context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (myClipboard.hasPrimaryClip()) {
            ClipData cd = myClipboard.getPrimaryClip();
            ClipData.Item item = cd.getItemAt(0);
            return item.getText().toString();
        }
        return null;
    }
}