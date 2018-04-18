package solipsisming.util.system;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;

import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * 调整屏幕亮度</p>
 * 创建于 2015-5-29 22:48:22
 *
 * @author 洪东明
 * @version 1.0
 */
public class BrightnessUtils {

    /**
     * 禁止创建对象
     */
    private BrightnessUtils() {
        throw new UnacceptableInstanceError();
    }

    /**
     * 设置当前屏幕亮度的模式
     * </p>
     * <ul>
     * <li>SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度</li>
     * <li>SCREEN_BRIGHTNESS_MODE_MANUAL=0  为手动调节屏幕亮度</li>
     * </ul>
     *
     * @param context     当前应用
     * @param brightModel 亮度模式
     */
    public static void setBrightnessMode(Context context, int brightModel) {
        if (brightModel > 1 || brightModel < 0) {
            PrintLog.logW("BrightnessUtil.setBrightnessMode(brightModel " +
                    "must be 0(manual) or 1(auto))");
            return;
        }
        Settings.System.putInt(context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE, brightModel);
    }

    /**
     * 获取设备的亮度模式
     *
     * @param context 当前应用
     * @return 亮度模式
     */
    public static int getBrightnessMode(Context context) {
        try {
            return Settings.System.getInt(context.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }


    /**
     * 判断是否是自动调节亮度模式
     *
     * @param activity 当前应用
     * @return 是否是自动调节亮度模式
     */
    public static boolean isAutoBrightness(Activity activity) {
        boolean autoBrightness = false;
        ContentResolver contentResolver = activity.getContentResolver();
        try {
            autoBrightness = Settings.System.getInt(contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return autoBrightness;
    }

    /**
     * 开启亮度自动亮度模式
     *
     * @param activity 当前应用
     */
    public static void startAutoBrightness(Activity activity) {
        Settings.System.putInt(activity.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
        Uri uri = Settings.System.getUriFor("screen_brightness");
        activity.getContentResolver().notifyChange(uri, null);
    }


    /**
     * 停止自动亮度模式
     *
     * @param activity 当前应用
     */
    public static void stopAutoBrightness(Activity activity) {
        Settings.System.putInt(activity.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        Uri uri = Settings.System.getUriFor("screen_brightness");
        activity.getContentResolver().notifyChange(uri, null);
    }

    /**
     * 设置当前屏幕亮度值(系统)  0--255
     *
     * @param context     当前应用
     * @param brightValue 亮度值
     */
    public static void setSystemBrightness(Context context, int brightValue) {
        Settings.System.putInt(context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS, brightValue);
    }

    /**
     * 保存当前的屏幕亮度值，并使之生效
     *
     * @param activity    当前activity
     * @param brightValue 亮度值
     */
    public static void setBrightness(Activity activity, int brightValue) {
        if (activity == null) {
            PrintLog.logW("BrightnessUtil.setBrightness(args activity is null)");
            return;
        }
        if (brightValue <= 0)
            brightValue = 1;
        if (brightValue > 255f)
            brightValue = 255;
        Window localWindow = activity.getWindow();
        WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
        localLayoutParams.screenBrightness = brightValue / 255f;
        localWindow.setAttributes(localLayoutParams);
    }

    /**
     * 获得当前屏幕亮度值(当前应用) 值得范围:<b>0-255</b>
     *
     * @param context 当前应用
     * @return 亮度值
     */
    public static int getBrightness(Context context) {
        try {
            return Settings.System.getInt(context.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }
}