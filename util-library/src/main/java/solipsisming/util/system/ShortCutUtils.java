package solipsisming.util.system;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * 创建快捷方式</p>
 * <p/>
 * 创建于2015-11-10 20:29:52
 *
 * @author 洪东明
 * @version 1.0
 */
public class ShortCutUtils {

    public static final String ACTION_ADD_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";

    /**
     * 禁止实例化
     */
    private ShortCutUtils() {
        throw new UnacceptableInstanceError();
    }

    /**
     * 添加桌面快捷方式
     *
     * @param context 当前应用
     * @param name    图标名字
     * @param imageId 图标图片
     * @param clazz   启动的activity
     */
    public static void addShortcut(Context context, String name,
                                   int imageId, Class<? extends Activity> clazz) {
        Intent intent = new Intent(ACTION_ADD_SHORTCUT);
        intent.putExtra("duplicate", false);// 不允许重复创建,经测试不是根据快捷方式的名字判断重复的
        // 应该是根据快链的Intent来判断是否重复的,即Intent.EXTRA_SHORTCUT_INTENT字段的value
        // 但是名称不同时，虽然有的手机系统会显示Toast提示重复，仍然会建立快链
        // 屏幕上没有空间时会提示
        // 注意：重复创建的行为MIUI和三星手机上不太一样，小米上似乎不能重复创建快捷方式
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name); // 名字
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(context, imageId)); // 图标
        Intent launcherIntent = new Intent(Intent.ACTION_MAIN);  // 设置关联程序
        launcherIntent.setClass(context, clazz);
        launcherIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launcherIntent);
        context.sendBroadcast(intent); // 发送广播
    }
}