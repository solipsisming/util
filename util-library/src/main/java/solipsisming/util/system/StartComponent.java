package solipsisming.util.system;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * intent跳转工具</p>
 * 创建于 2015-5-29 21:14:51
 *
 * @author 洪东明
 * @version 1.0
 */
public class StartComponent {

    /**
     * 禁止创建对象
     */
    private StartComponent() {
        throw new UnacceptableInstanceError();
    }

    /**
     * 启动activity
     *
     * @param activity 当前activity
     * @param clazz    启动的class
     * @param isFinish 是否退出activity
     */
    public static void startActivity(Activity activity, Class<? extends Activity> clazz, boolean isFinish) {
        startActivity(activity, clazz, -1, -1, null, isFinish);
    }

    /**
     * 启动activity
     *
     * @param activity 当前activity
     * @param clazz    启动的class
     * @param before   关闭动画
     * @param after    启动动画
     * @param isFinish 是否退出activity
     */
    public static void startActivity(Activity activity, Class<? extends Activity> clazz,
                                     int before, int after, boolean isFinish) {
        startActivity(activity, clazz, before, after, null, isFinish);
    }

    /**
     * 启动activity
     *
     * @param activity 当前activity
     * @param clazz    启动的class
     * @param bundle   数据集
     * @param isFinish 是否退出activity
     */
    public static void startActivity(Activity activity, Class<? extends Activity> clazz,
                                     Bundle bundle, boolean isFinish) {
        startActivity(activity, clazz, -1, -1, bundle, isFinish);
    }

    /**
     * 启动activity
     *
     * @param activity 当前activity
     * @param clazz    启动的class
     * @param before   关闭动画
     * @param after    启动动画
     * @param bundle   数据集
     * @param isFinish 是否退出activity
     */
    public static void startActivity(Activity activity, Class<? extends Activity> clazz,
                                     int before, int after, Bundle bundle, boolean isFinish) {
        if (activity == null || clazz == null)
            throw new NullPointerException("StartComponent.startActivity(args activity/class is null)");

        Intent intent = new Intent(activity, clazz);
        if (bundle != null && bundle.size() > 0)
            intent.putExtras(bundle);
        activity.startActivity(intent);
        if (before != -1 && after != -1) //切换activity动画效果
            activity.overridePendingTransition(before, after);
        if (isFinish)
            activity.finish();
    }
}