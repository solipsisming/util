package solipsisming.util.system;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * 打开关闭键盘</p>
 *
 * @author 洪东明
 */
public class KeyboardUtils {


    /**
     * 禁止实例对象
     */
    private KeyboardUtils() {
        throw new UnacceptableInstanceError();
    }

    /**
     * 如果已经打开就关闭，如果关闭就打开
     *
     * @param context 当前应用
     */
    public static void autoDetectKeyboard(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null)
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 打卡软键盘
     *
     * @param context 当前应用
     * @param v       控件
     */
    public static void openKeyboard(Context context, View v) {
        if (!v.isFocused())//没有获取到焦点
            v.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null)
            inputMethodManager.showSoftInput(v, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 关闭软键盘
     *
     * @param context 当前应用
     * @param v       控件
     */
    public static void closeKeyboard1(Context context, View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null)
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static void closeKeyboard2(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null)
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 关闭软键盘
     *
     * @param activity 当前activity
     */
    public static void closeKeyboard3(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null)
            inputMethodManager.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
    }

    /**
     * 关闭键盘
     *
     * @param activity 当前activity
     */
    public static void closeKeyboard4(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            View v = activity.getCurrentFocus();
            if (v != null) {
                IBinder iBinder = v.getWindowToken();
                inputMethodManager.hideSoftInputFromWindow(iBinder, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}