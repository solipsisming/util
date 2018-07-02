package solipsisming.util.system;

import android.content.Context;
import android.os.Environment;

import java.io.File;

import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * 应用目录</p>
 * 创建于 2018-3-19 15:28:25
 *
 * @author 洪东明
 * @version 1.0
 */
public class  DirectoryUtils {

    /**
     * assets目录
     */
    public static final String ASSETS = "file:///android_asset/";

    /**
     * 禁止实例化
     */
    private DirectoryUtils() {
        throw new UnacceptableInstanceError();
    }

    /**
     * 获取当前应用的缓存目录
     *
     * @param context 当前应用
     * @return 当前应用的缓存目录
     */
    public static File getCacheDir(Context context) {
        return context.getCacheDir();
    }

    /**
     * 获取当前应用的文件目录
     *
     * @param context 当前应用
     * @return 当前应用的文件目录
     */
    public static File getFilesDir(Context context) {
        return context.getFilesDir();
    }

    /**
     * 获取当前应用的目录
     *
     * @param context 当前应用
     * @return 当前应用的目录
     */
    public static String getDataDir(Context context) {
        return context.getApplicationInfo().dataDir;
    }

    /**
     * 获取当前应用lib的目录
     *
     * @param context 当前应用
     * @return 当前应用lib的目录
     */
    public static String getNativeLibraryDir(Context context) {
        return context.getApplicationInfo().nativeLibraryDir;
    }

    /**
     * sd卡的缓存目录  保存的文件会随着app卸载而删除
     *
     * @param context 当前应用
     * @return sd卡的缓存目录
     */
    public static File getExternalCacheDir(Context context) {
        return context.getExternalCacheDir();
    }

    /**
     * sd卡的文件目录 保存的文件会随着app卸载而删除
     *
     * @param context 当前应用
     * @return sd卡的文件目录
     */
    public static File getExternalFilesDir(Context context) {
        return context.getExternalFilesDir("");
    }

    /**
     * sd卡的obb目录 保存的文件会随着app卸载而删除
     *
     * @param context 当前应用
     * @return sd卡的obb目录
     */
    public static File getObbDir(Context context) {
        return context.getObbDir();
    }

    /**
     * 安装包的路径
     *
     * @param context 当前应用
     * @return 安装包的路径
     */
    public static String getPackageCodePath(Context context) {
        return context.getPackageCodePath();
    }

    /**
     * 安装包的路径
     *
     * @param context 当前应用
     * @return 安装包的路径
     */
    public static String getPackageResourcePath(Context context) {
        return context.getPackageResourcePath();
    }

    /**
     * /mnt/sdcard
     *
     * @return 外部存储卡路径
     */
    public static File getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory();
    }

    /**
     * /data
     *
     * @param context 当前应用
     * @return 外部data目录
     */
    public static File getDataDirectory(Context context) {
        return Environment.getDataDirectory();
    }

    /**
     * /cache
     *
     * @param context 当前应用
     * @return 外部缓存目录
     */
    public static File getDownloadCacheDirectory(Context context) {
        return Environment.getDownloadCacheDirectory();
    }

    /**
     * /system
     *
     * @param context 当前应用
     * @return 外部系统目录
     */
    public static File getRootDirectory(Context context) {
        return Environment.getRootDirectory();
    }

    /**
     * 获取当前应用的数据库的目录
     *
     * @param context      当前应用
     * @param databaseName 数据库名字
     * @return 返回数据库的文件路径
     */
    public File getAppDatabasesDir(Context context, String databaseName) {
        return context.getDatabasePath(databaseName);//获取当前应用的数据库的目录;
    }

    /**
     * 获取当前应用的配置文件目录
     *
     * @param context    当前应用
     * @param configName 配置的名字
     * @return app_name配置文件的路径
     */
    public File getAppConfigDir(Context context, String configName) {
        return context.getDir(configName, Context.MODE_PRIVATE);//获取当前应用的配置文件目录[app_name];
    }
}