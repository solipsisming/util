package solipsisming.util.system;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * 权限工具类
 * 创建于 2018-3-19 17:19:53
 *
 * @author 洪东明
 * @version 1.0
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class PermissionUtils {

    /**
     * 禁止实例化
     */
    private PermissionUtils() {
        throw new UnacceptableInstanceError();
    }

    private static int sRequestCode = -1;
    private static OnPermissionListener sListener;

    public interface OnPermissionListener {
        /**
         * 授权成功
         */
        void onPermissionGranted();

        /**
         * 请求权限失败
         *
         * @param deniedPermissions 被拒绝的权限集合
         * @param alwaysDenied      拒绝后是否提示
         */
        void onPermissionDenied(String[] deniedPermissions, boolean alwaysDenied);
    }

    /**
     * 再次请求授权
     *
     * @param context     当前应用
     * @param permissions 权限
     * @param requestCode 请求码
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static void requestPermissionsAgain(@NonNull Context context,
                                               @NonNull String[] permissions,
                                               @NonNull int requestCode) {
        if (context instanceof Activity) {
            ((Activity) context).requestPermissions(permissions, requestCode);
        } else {
            throw new IllegalArgumentException("Context must be an Activity");
        }
    }

    /**
     * 请求权限
     *
     * @param context     当前应用
     * @param requestCode 请求码
     * @param permissions 权限
     * @param listener    回调函数
     */
    public static void requestPermissions(@NonNull Context context,
                                          @NonNull int requestCode,
                                          @NonNull String[] permissions,
                                          OnPermissionListener listener) {
        sRequestCode = requestCode;
        sListener = listener;
        String[] deniedPermissions = getDeniedPermissions(context, permissions);
        if (deniedPermissions.length > 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissionsAgain(context, permissions, requestCode);
        } else {
            if (sListener != null) {
                sListener.onPermissionGranted();
            }
        }
    }

    /**
     * 请求权限结果，对应Activity中onRequestPermissionsResult()方法。
     *
     * @param context     当前应用
     * @param permissions 权限
     * @param requestCode 请求码
     */
    public static void onRequestPermissionsResult(@NonNull Activity context,
                                                  int requestCode,
                                                  @NonNull String[] permissions) {
        if (sRequestCode != -1 && requestCode == sRequestCode) {
            if (sListener != null) {
                String[] deniedPermissions = getDeniedPermissions(context, permissions);
                if (deniedPermissions.length > 0) {
                    boolean alwaysDenied = hasAlwaysDeniedPermission(context, permissions);
                    sListener.onPermissionDenied(deniedPermissions, alwaysDenied);
                } else {
                    sListener.onPermissionGranted();
                }
            }
        }
    }

    /**
     * 获取请求权限中需要授权的权限
     *
     * @param context     当前应用
     * @param permissions 权限
     */
    private static String[] getDeniedPermissions(@NonNull Context context,
                                                 @NonNull String[] permissions) {
        List<String> deniedPermissions = new ArrayList();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
                deniedPermissions.add(permission);
            }
        }
        return deniedPermissions.toArray(new String[deniedPermissions.size()]);
    }

    /**
     * 是否彻底拒绝了某项权限
     *
     * @param context           当前应用
     * @param deniedPermissions 权限
     */
    private static boolean hasAlwaysDeniedPermission(@NonNull Context context,
                                                     @NonNull String... deniedPermissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false;
        }
        boolean rationale;
        for (String permission : deniedPermissions) {
            rationale = ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission);
            if (!rationale) {
                return true;
            }
        }
        return false;
    }

    /**
     * 清除回调函数，请求码
     */
    public static void clear() {
        sListener = null;
        sRequestCode = -1;
    }
}