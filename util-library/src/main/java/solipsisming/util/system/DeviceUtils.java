package solipsisming.util.system;


import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.location.LocationManager;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Window;

import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * 设备信息</p>
 * 创建于 2018-3-19 15:35:47
 *
 * @author 洪东明
 * @version 1.0
 */
public class DeviceUtils {

    /**
     * 禁止实例
     */
    private DeviceUtils() {
        throw new UnacceptableInstanceError();
    }

    private static final int ZERO = 0;
    /**
     * 手机屏幕高度
     */
    private static int height;
    /**
     * 手机屏幕宽度
     */
    private static int width;
    /**
     * 手机密度
     */
    private static float density;
    /**
     * 手机密度比
     */
    private static int densityDpi;
    /**
     * 手机伸缩密度
     */
    private static float scaledDensity;
    /**
     * 手机屏幕状态栏的高度
     */
    private static int statusHeight;
    /**
     * 获取actionbar高度
     */
    private static int actionBarHeight;

    private static final String CHINA_MOBILE = "中国移动";
    private static final String CHINA_UNICOM = "中国联通";
    private static final String CHINA_TELECOM = "中国电信";


    /**
     * 获取设备的高度
     *
     * @param context 当前应用
     * @return 高度
     */
    public static int getDeviceHeight(Context context) {
        if (height == ZERO)
            eval(context);
        return height;
    }

    /**
     * 获取设备的宽度
     *
     * @param context 当前应用
     * @return 宽度
     */
    public static int getDeviceWidth(Context context) {
        if (width == ZERO)
            eval(context);
        return width;
    }

    /**
     * 获取设备的密度
     *
     * @param context 当前应用
     * @return 密度
     */
    public static float getDeviceDensity(Context context) {
        if (density == ZERO)
            eval(context);
        return density;
    }

    /**
     * 获取设备的密度比
     *
     * @param context 当前应用
     * @return 密度比
     */
    public static float getDeviceDensityDpi(Context context) {
        if (densityDpi == ZERO)
            eval(context);
        return densityDpi;
    }

    /**
     * 获取设备的伸缩密度
     *
     * @param context 当前应用
     * @return 伸缩密度
     */
    public static float getDeviceScaledDensity(Context context) {
        if (scaledDensity == ZERO)
            eval(context);
        return scaledDensity;
    }

    /**
     * 获取设备信息
     *
     * @param context 当前应用
     */
    private static void eval(Context context) {
        DisplayMetrics metric = context.getResources().getDisplayMetrics();
        height = metric.heightPixels;//高度
        width = metric.widthPixels;//宽度
        density = metric.density;//密度
        densityDpi = metric.densityDpi;//密度比
        scaledDensity = metric.scaledDensity;//伸缩密度
    }

    /**
     * 获得状态栏的高度
     *
     * @param activity 当前应用
     * @return 状态栏高度
     */
    public static int getStatusHeight(Context activity) {
        //获取status_bar_height资源的ID
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
            //根据资源ID获取响应的尺寸值
            statusHeight = activity.getResources().getDimensionPixelSize(resourceId);
        return statusHeight;
    }

    /**
     * 获得标题栏的高度
     *
     * @param activity activity
     * @return 标题栏高度
     */
    public static int getTitleHeight(Activity activity) {
        //标题栏的高度：用contentViewTop-状态栏的高度=标题栏高度
        return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop() - getStatusHeight(activity);
    }

    /**
     * 获取actionbar高度
     *
     * @param activity 当前activity
     * @return actionbar高度
     */
    public static int getActionBarHeight(Activity activity) {
        ActionBar actionBar = activity.getActionBar();
        return actionBar != null ? actionBar.getHeight() : 0;
    }

    /**
     * GPS是否开启
     *
     * @return 是否可用
     */
    public static boolean isGpsUseful(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 判断是否锁屏
     *
     * @return 是否可用
     */
    public static boolean isScreenLocked(Context context) {
        KeyguardManager mKeyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        return mKeyguardManager != null && mKeyguardManager.inKeyguardRestrictedInputMode();
    }

    /**
     * 判断手机是否有sim卡
     *
     * @return 是否可用
     */
    public static boolean isSimCardUseful(Context context) {
        TelephonyManager mgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return mgr != null && TelephonyManager.SIM_STATE_READY == mgr.getSimState();
    }

    /**
     * 获取手机的运营商
     *
     * @return 中国移动/中国联通/中国电信
     */
    public static String getOperator(Context context) {
        TelephonyManager telManager = (TelephonyManager) context.getSystemService(Activity.TELEPHONY_SERVICE);
        if (telManager != null) {
            String operator = telManager.getSimOperator();
            if (operator != null) {
                if (operator.equals("46000") || operator.equals("46002")) {
                    return CHINA_MOBILE;
                } else if (operator.equals("46001")) {
                    return CHINA_UNICOM;
                } else if (operator.equals("46003")) {
                    return CHINA_TELECOM;
                }
            }
        }
        return null;
    }

    /**
     * 获取设备ID，如androidID或IDFA
     */
    @SuppressLint("HardwareIds")
    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取IMEI(International Mobile EquipmentIdentity)是移动设备国际身份码的缩写
     */
    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null)
            telephonyManager.getDeviceId();
        return null;
    }

    /**
     * 判断sd卡是否可用
     *
     * @return 可用/不可用
     */
    public static boolean isSdCardUseful() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}