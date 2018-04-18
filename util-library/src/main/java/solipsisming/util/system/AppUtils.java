package solipsisming.util.system;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * 当前app信息</p>
 * 创建于 2018-3-19 17:07:26
 *
 * @author 洪东明
 * @version 1.0
 */
public class AppUtils {

    /**
     * 禁止实例对象
     */
    private AppUtils() {
        throw new UnacceptableInstanceError();
    }

    /**
     * 应用版本号
     */
    private static int versionCode;
    /**
     * 应用版本名称
     */
    private static String versionName;
    /**
     * 应用包名
     */
    private static String packageName;
    /**
     * 应用配置信息
     */
    private static Bundle metadata;
    /**
     * 应用最后更新时间
     */
    private static long lastUpdateTime;
    /**
     * 当前包的uid
     */
    private static int uid;

    /**
     * 应用版本号
     *
     * @return 应用版本号
     */
    public static int getVersionCode(Context context) {
        if (versionCode == 0)
            eval(context);
        return versionCode;
    }

    /**
     * 应用版本名称
     *
     * @return 应用版本名称
     */
    public static String getVersionName(Context context) {
        if (versionName == null)
            eval(context);
        return versionName;
    }

    /**
     * 应用包名
     *
     * @return 应用包名
     */
    public static String getPackageName(Context context) {
        if (packageName == null)
            eval(context);
        return packageName;
    }

    /**
     * 应用配置信息
     *
     * @return 应用配置信息
     */
    public static Bundle getMetadata(Context context) {
        if (metadata == null)
            eval(context);
        return metadata;
    }

    /**
     * 应用最后更新时间
     *
     * @return 应用最后更新时间
     */
    public static long getLastUpdateTime(Context context) {
        if (lastUpdateTime == 0)
            eval(context);
        return lastUpdateTime;
    }

    /**
     * 当前包的uid
     *
     * @return 当前包的uid
     */
    public static int getUid(Context context) {
        if (uid == 0)
            eval(context);
        return uid;
    }

    @SuppressLint("WrongConstant")
    private static void eval(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);//获取包信息
            versionCode = packageInfo.versionCode;//获取版本号
            versionName = packageInfo.versionName;//获取版本名称
            packageName = packageInfo.packageName;//获取包名
            lastUpdateTime = packageInfo.lastUpdateTime;//获取最后更新时间
            metadata = packageManager.getApplicationInfo
                    (packageName, PackageManager.GET_META_DATA).metaData; //获取应用配置信息
            uid = packageManager.getApplicationInfo
                    (packageName, PackageManager.GET_ACTIVITIES).uid; //获取应用uid
        } catch (PackageManager.NameNotFoundException e) {
            PrintLog.logErr(e);
        }
    }

    /**
     * 判断指定包名的app是否正在运行
     *
     * @param context     当前应用
     * @param packageName 要判断的包名
     * @return 在运行或没有运行
     */
    public static boolean isRunning(Context context, String packageName) {
        ActivityManager am = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(100);
        int size = tasks.size();
        for (int i = 0; i < size; i++) {
            ActivityManager.RunningTaskInfo info = tasks.get(i);
            if (info.topActivity.getPackageName().equals(packageName) &&
                    info.baseActivity.getPackageName().equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取当前应用的栈顶任务
     *
     * @param context 当前应用
     * @return 栈顶任务包名
     */
    public static String getTopActivity(Context context) {
        ActivityManager manager = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = manager.getRunningTasks(100);
        int size = tasks.size();
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;//获取包信息
        try {
            packageInfo = packageManager
                    .getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (packageInfo == null)
            return null;

        String packageName = packageInfo.packageName;

        for (int i = 0; i < size; i++) {
            ActivityManager.RunningTaskInfo info = tasks.get(i);
            if (info.topActivity.getPackageName().equals(packageName)
                    && info.baseActivity.getPackageName().equals(packageName)) {
                return info.topActivity.getClassName();
            }
        }
        return null;
    }

    /**
     * 判断当前界面是否是桌面
     *
     * @param context 当前应用
     * @return 当前界面是否是桌面
     */
    private boolean isHome(Context context) {
        ActivityManager mActivityManager = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);
        return getHomes(context).contains(rti.get(0).topActivity.getPackageName());
    }

    /**
     * 获得属于桌面的应用的应用包名称
     *
     * @param context 当前应用
     * @return 返回包含所有包名的字符串列表
     */
    private List<String> getHomes(Context context) {
        List<String> names = new ArrayList<String>();
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo ri : resolveInfo)
            names.add(ri.activityInfo.packageName);
        return names;
    }

    /**
     * 获取当前页面截图
     *
     * @param activity 当前activity
     * @return 截取屏幕后的位图
     */
    public static Bitmap takeScreenShot(Activity activity) {
        View view = activity.getWindow().getDecorView();
        if (!view.isDrawingCacheEnabled())
            view.setDrawingCacheEnabled(true);
        return view.getDrawingCache();
    }

    /**
     * 获取手机可以改的内存大小
     *
     * @param context 当前应用
     * @return 可用内存大小
     */
    public long getAvailableMemory(Context context) {
        ActivityManager am = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem;
    }

    /**
     * 获取手机总内存大小
     *
     * @param context 当前应用
     * @return 手机总内存大小
     */
    public long getTotalMemory(Context context) {
        if (Build.VERSION.SDK_INT >= 16) {
            ActivityManager am = (ActivityManager)
                    context.getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            am.getMemoryInfo(memoryInfo);
            return memoryInfo.totalMem;
        }
        return -1;
    }
}