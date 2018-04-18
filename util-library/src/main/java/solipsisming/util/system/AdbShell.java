package solipsisming.util.system;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * Android adb Shell执行指令工具</p>
 * 创建于 2015-6-12 23:33:30
 *
 * @author 洪东明
 * @version 1.0
 */
public class AdbShell {
    /**
     * 禁止实例对象
     */
    private AdbShell() {
        throw new UnacceptableInstanceError();
    }

    private static final String COMMAND_SU = "su";//已越狱
    private static final String COMMAND_SH = "sh";//未越狱
    private static final String COMMAND_EXIT = "exit\n";//退出
    private static final String COMMAND_LINE_END = "\n";//换行
    private static final String BLANK = " ";//空格

    /**
     * 安装成功
     */
    public static final int INSTALL_SUCCEEDED = 1;
    /**
     * 已经安装过
     */
    public static final int INSTALL_FAILED_ALREADY_EXISTS = -1;
    /**
     * 无效的apk安装包
     */
    public static final int INSTALL_FAILED_INVALID_APK = -2;
    /**
     * 无效的安装路径
     */
    public static final int INSTALL_FAILED_INVALID_URI = -3;
    /**
     * 安装到内存失败
     */
    public static final int INSTALL_FAILED_INTERNAL_ERROR = -110;
    /**
     * 安装失败
     */
    public static final int INSTALL_FAILED_OTHER = -1000000;
    /**
     * 删除成功
     */
    public static final int DELETE_SUCCEEDED = 1;
    /**
     * 删除内存应用失败
     */
    public static final int DELETE_FAILED_INTERNAL_ERROR = -1;
    /**
     * 无效的应用包名
     */
    public static final int DELETE_FAILED_INVALID_PACKAGE = -3;
    /**
     * 没权限删除
     */
    public static final int DELETE_FAILED_PERMISSION_DENIED = -4;

    /**
     * 安装包的存放位置<b>（自动，内存，外存）</b>
     */
    public enum Position {
        /**
         * 自动
         */
        AUTO(""),
        /**
         * 外存
         */
        INTERNAL("-f"),
        /**
         * 内存
         */
        EXTERNAL("-s");

        private String pos;

        Position(String pos) {
            this.pos = pos;
        }
    }

    /**
     * 判断包名为apkPackage的APP是否已经安装
     *
     * @param context     当前应用
     * @param packageName 包名
     * @return 是否已经安装过
     */
    public static boolean isInstall(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> pakages = pm.getInstalledPackages
                (PackageManager.GET_UNINSTALLED_PACKAGES);
        for (PackageInfo info : pakages)
            if (info.packageName.contains(packageName))
                return true;
        return false;
    }

    /**
     * 安装apk
     *
     * @param context  当前应用
     * @param filePath 安装路径
     * @param position 安装位置
     * @return 是否安装成功
     * @throws IOException          关闭失败，创建流失败
     * @throws InterruptedException 执行异常
     */
    public static int install(Context context, String filePath, Position position)
            throws IOException, InterruptedException {
        File file = new File(filePath);
        if (file.length() <= 0 || !file.exists() || !file.isFile()) {
            return INSTALL_FAILED_INVALID_URI;
        }
        if (checkRootPermission() || isSystemApplication(context)) {//是否root过或者是系统应用
            boolean isSystem = isSystemApplication(context);
            return installSilent(filePath, position, isSystem);
        }
        return installNormal(context, filePath) ? INSTALL_SUCCEEDED : INSTALL_FAILED_INVALID_URI;
    }

    /**
     * 无操作安装
     *
     * @param filePath 安装路径
     * @param position 安装位置
     * @return 安装结果
     * @throws IOException          关闭失败，创建流失败
     * @throws InterruptedException 执行异常
     */
    private static int installSilent(String filePath,
                                     Position position, boolean isSystem)
            throws IOException, InterruptedException {
        PrintLog.logE("installSilent...");
        StringBuilder command = concatCommand(filePath, position);//拼装指令
        PrintLog.logE("command: " + command);
        CommandResult commandResult = execCommand(!isSystem, true, command.toString());
        String success = commandResult.successMsg;
        if (success != null && success.toLowerCase().contains("success")) {//安装成功
            return INSTALL_SUCCEEDED;
        }
        String error = commandResult.errorMsg;
        if (error == null) {//安装失败
            return INSTALL_FAILED_OTHER;
        }
        if (error.contains("INSTALL_FAILED_ALREADY_EXISTS")) {
            return INSTALL_FAILED_ALREADY_EXISTS;
        }
        if (error.contains("INSTALL_FAILED_INVALID_APK")) {
            return INSTALL_FAILED_INVALID_APK;
        }
        if (error.contains("INSTALL_FAILED_INVALID_URI")) {
            return INSTALL_FAILED_INVALID_URI;
        }
        if (error.contains("INSTALL_FAILED_INTERNAL_ERROR")) {
            return INSTALL_FAILED_INTERNAL_ERROR;
        }
        return INSTALL_FAILED_OTHER;
    }

    /**
     * 拼装指令
     *
     * @param filePath 路径
     * @param position 安装位置
     * @return 拼装后的指令
     */
    private static StringBuilder concatCommand(String filePath, Position position) {
        StringBuilder command = new StringBuilder();
        command.append("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install").append(BLANK);
        if (position != null) {//指定位置
            command.append(" ").append("-r").append(BLANK);
            switch (position) {
                case INTERNAL://内存
                    command.append(position.pos).append(BLANK);
                    break;
                case EXTERNAL://外存
                    command.append(position.pos).append(BLANK);
                    break;
                case AUTO://默认
                    break;
                default:
                    break;
            }
        }
        command.append(filePath.replace(BLANK, "\\" + BLANK));
        return command;
    }

    /**
     * 正常安装
     *
     * @param context  当前应用
     * @param filePath 安装路径
     * @return 是否安装成功
     */
    private static boolean installNormal(Context context, String filePath) {
        PrintLog.logE("installNormal...");
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + filePath),
                "application/vnd.android.package-archive");
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        return true;
    }

    /**
     * 卸载
     *
     * @param context  当前应用
     * @param keepData 是否保留数据
     * @return 卸载结果
     * @throws IOException          关闭失败，创建流失败
     * @throws InterruptedException 执行异常
     */
    public static int uninstall(Context context,
                                String packageName, boolean keepData)
            throws IOException, InterruptedException {
        if (checkRootPermission() || isSystemApplication(context)) {
            boolean isSystem = isSystemApplication(context);
            return uninstallSilent(packageName, keepData, isSystem);
        }
        return uninstallNormal(context, packageName) ?
                DELETE_SUCCEEDED : DELETE_FAILED_INVALID_PACKAGE;
    }

    /**
     * 无操作卸载
     *
     * @param context 当前应用
     * @return 卸载结果
     */
    private static boolean uninstallNormal(Context context, String packageName) {
        Intent i = new Intent(Intent.ACTION_DELETE, Uri.parse(new StringBuilder(32)
                .append("package:")
                .append(packageName).toString()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        return true;
    }

    /**
     * 正常卸载
     *
     * @param isKeepData 是否保留数据
     * @param isSystem   是否为系统应用
     * @return 卸载结果
     * @throws IOException          关闭失败，创建流失败
     * @throws InterruptedException 执行异常
     */
    private static int uninstallSilent(String packageName,
                                       boolean isKeepData, boolean isSystem)
            throws IOException, InterruptedException {
        StringBuilder command = new StringBuilder().
                append("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm uninstall")
                .append(isKeepData ? BLANK + "-k " : BLANK)
                .append(packageName.replace(BLANK, "\\" + BLANK));
        CommandResult commandResult =
                AdbShell.execCommand(!isSystem, true, command.toString());
        String success = commandResult.successMsg;
        if (success != null && success.toLowerCase().contains("success")) {
            return DELETE_SUCCEEDED;
        }
        String error = commandResult.errorMsg;
        if (error == null) {
            return DELETE_FAILED_INTERNAL_ERROR;
        }
        if (error.contains("Permission denied")) {
            return DELETE_FAILED_PERMISSION_DENIED;
        }
        return DELETE_FAILED_INTERNAL_ERROR;
    }

    /**
     * 判断是否为系统应用
     *
     * @param context 当前应用
     * @return 是/不是
     */
    private static boolean isSystemApplication(Context context) {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null)
            return false;
        try {
            PackageInfo packageInfo = packageManager
                    .getPackageInfo(context.getPackageName(), 0);//获取包信息
            ApplicationInfo app = packageManager.getApplicationInfo(packageInfo.packageName, 0);
            return (app != null && (app.flags & ApplicationInfo.FLAG_SYSTEM) > 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断该手机是否越狱过
     *
     * @return 越狱过/没有越狱
     * @throws InterruptedException 中断异常
     * @throws IOException          关闭失败，创建流失败
     */
    public static boolean checkRootPermission() throws IOException, InterruptedException {
        return execCommand(true, false, "echo root").result == 0;
    }

    /**
     * 执行指令（指令list集合）
     *
     * @param isRoot          是否越狱过
     * @param isNeedResultMsg 是否需要结果信息
     * @param commands        指令集合
     * @return 执行结果
     * @throws InterruptedException 中断异常
     * @throws IOException          关闭失败，创建流失败
     */
    public static CommandResult execCommand
    (boolean isRoot, boolean isNeedResultMsg, List<String> commands)
            throws IOException, InterruptedException {
        return execCommand(isRoot, isNeedResultMsg, commands.toArray(new String[]{}));
    }

    /**
     * 执行指令（指令数组）
     *
     * @param isRoot          是否越狱过
     * @param isNeedResultMsg 是否需要结果信息
     * @param commands        指令
     * @return 执行结果
     * @throws InterruptedException 中断异常
     * @throws IOException          关闭失败，创建流失败
     */
    public static CommandResult execCommand
    (boolean isRoot, boolean isNeedResultMsg, String... commands)
            throws IOException, InterruptedException {
        int result = -1;
        if (commands == null || commands.length == 0) {//指令不合法
            PrintLog.logE("AdbShell.execCommand(commands is null or commands.length==0)");
            return new CommandResult(result, null, null);
        }
        DataOutputStream dos = null;
        Process process = null;

        try {//执行指令可能出现异常
            process = Runtime.getRuntime().exec(isRoot ? COMMAND_SU : COMMAND_SH);//执行指令
            dos = new DataOutputStream(process.getOutputStream());//指令输入流
            for (String command : commands) {
                if (command != null) {
                    dos.write(command.getBytes());//写入指令
                    dos.writeBytes(COMMAND_LINE_END);//换行
                    dos.flush();
                }
            }
            dos.writeBytes(COMMAND_EXIT);//写入退出指令
            dos.flush();
        } finally {//可能执行期间抛出异常
            try {
                if (dos != null)
                    dos.close();
            } catch (IOException e) {
                if (process != null)
                    process.destroy();
                throw e;
            }
        }

        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = null;
        StringBuilder errorMsg = null;

        result = process.waitFor();//执行结果

        if (isNeedResultMsg) {//是否需要执行结果信息
            String s;
            try {
                successResult = new BufferedReader
                        (new InputStreamReader(process.getInputStream()));//获取成功结果流
                successMsg = new StringBuilder();
                while ((s = successResult.readLine()) != null)
                    successMsg.append(s);//成功信息
            } finally {//可能执行期间抛出异常
                try {
                    if (successResult != null)
                        successResult.close();
                } catch (IOException e) {
                    if (process != null)
                        process.destroy();
                    throw e;
                }
            }

            try {
                errorResult = new BufferedReader
                        (new InputStreamReader(process.getErrorStream()));//获取失败结果流
                errorMsg = new StringBuilder();
                while ((s = errorResult.readLine()) != null)
                    errorMsg.append(s);//错误信息
            } finally {//可能执行期间抛出异常
                try {
                    if (errorResult != null)
                        errorResult.close();
                } catch (IOException e) {
                    if (process != null)
                        process.destroy();
                    throw e;
                }
            }
        }

        if (process != null)
            process.destroy();

        CommandResult cr = new CommandResult(result);//包装执行结果
        if (successMsg != null)
            cr.successMsg = successMsg.toString();
        if (errorMsg != null)
            cr.errorMsg = errorMsg.toString();
        return cr;
    }

    /**
     * 指令执行结果信息</br>
     */
    public static class CommandResult {

        /**
         * 执行结果
         */
        public int result;
        /**
         * 成功信息
         */
        public String successMsg = null;
        /**
         * 错误信息
         */
        public String errorMsg = null;

        CommandResult(int result) {
            this.result = result;
        }

        CommandResult(int result, String successMsg, String errorMsg) {
            this.result = result;
            this.successMsg = successMsg;
            this.errorMsg = errorMsg;
        }
    }
}