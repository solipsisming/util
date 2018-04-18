package solipsisming.util.io;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import solipsisming.util.common.StringUtils;
import solipsisming.util.exception.FileExistsException;
import solipsisming.util.exception.FileNotExistsException;
import solipsisming.util.exception.UnacceptableInstanceError;
import solipsisming.util.system.DeviceUtils;

/**
 * 文件工具</p>
 * 创建于 2015-5-29 19:16:23
 *
 * @author 洪东明
 * @version 1.0
 */
public class FileUtils {

    /**
     * kb
     */
    public static final int KB = 1024;
    /**
     * mb
     */
    public static final int MB = KB * KB;
    /**
     * g
     */
    public static final int G = MB * KB;
    private static final long FILE_COPY_BUFFER_SIZE = MB * 5;//复制的缓存大小

    /**
     * 防止开发程序员创建
     */
    private FileUtils() {
        throw new UnacceptableInstanceError();
    }

    /**
     * 创建文件夹或目录
     *
     * @param file 目录或文件夹(String)
     * @return 创建结果
     */
    public static boolean createDir(String file) {
        return createDir(new File(file));
    }

    /**
     * 创建文件夹或目录
     *
     * @param file 目录或文件夹(File)
     * @return 创建结果
     */
    public static boolean createDir(File file) {
        boolean result = false;
        if (!file.exists())
            result = file.mkdirs();
        return result;
    }

    /**
     * 删除文件
     *
     * @param file 文件名或目录(string)
     * @return 是否删除成功
     */
    public static boolean delete(String file) {
        File f = new File(file);
        if (!f.exists()) {// 文件不存在
            return true;
        }
        return delete(f);
    }

    /**
     * 删除文件
     *
     * @param file 文件名或目录(File)
     * @return 是否删除成功
     */
    private static boolean delete(File file) {

        if (file.isFile()) {// 删除文件
            file.delete();
            return true;
        }

        File[] files = file.listFiles();
        if (files != null && files.length > 0) {//存在子目录
            for (File f : files) {// 递归删除
                delete(f);
            }
        }
        file.delete();//空文件夹
        return true;
    }

    /**
     * 过滤指定扩展名的文件
     *
     * @param file 文件目录(string)
     * @param ext  扩展名
     * @return 指定扩展名文件
     * @see StringUtils 字符串工具
     */
    public static List<String> search(String file, String ext) {
        if (StringUtils.isTrimEmpty(ext)) {
            throw new NullPointerException("args ext is null");
        }

        File f = new File(file);
        if (!f.exists()) {//文件不存在
            throw new FileNotExistsException(f);
        }

        return doSearch(f, ext);
    }

    /**
     * 过滤指定扩展名的文件
     *
     * @param file 文件目录(file)
     * @param ext  扩展名
     * @return 指定扩展名文件
     */
    private static List<String> doSearch(File file, String ext) {
        ArrayList<String> files = new ArrayList<String>();
        if (file.isFile()) {//当前为一个文件
            if (file.getName().endsWith(ext)) {// 是文件判断后缀名
                files.add(file.getAbsolutePath());
            }
            return files;
        }

        File[] fs = file.listFiles();// 遍历文件夹
        if (fs != null && fs.length > 0) {//遍历子目录
            for (int i = 0; i < fs.length; i++) {
                files.addAll(doSearch(fs[i], ext));
            }
        }
        return files;
    }

    /**
     * 计算文件大小，包括目录
     *
     * @param file 文件名(string)
     * @return 文件大小
     */
    public static long caculateFileSize(String file) {
        File f = new File(file);
        if (!f.exists()) {// 文件不存在
            throw new FileNotExistsException(f);
        }
        return caculateFileSize(f);
    }

    /**
     * 计算文件大小，包括目录
     *
     * @param file 文件名(file)
     * @return 文件大小
     */
    private static long caculateFileSize(File file) {
        if (file.isFile())// 判断是否为文件
            return file.length();

        File[] files = file.listFiles();
        long totalSize = 0;
        if (files != null || files.length > 0) {// 存在子目录
            for (final File f : files) {// 遍历目录
                totalSize += caculateFileSize(f);
            }
        }
        return totalSize;
    }

    /**
     * 单位转换
     * (如果可用大小和总共大小十分接近会引起偏差  15.9g(总共)-32m(已用)=15.9g(可用))
     *
     * @param bytes 字节数
     * @return 对应的合适单位
     * @deprecated 计算存在误差
     */
    @Deprecated
    public static String convertUnit(long bytes) {
        if (bytes <= 0)
            return null;
        if (bytes < KB && bytes > 0)
            return bytes + "B";
        String str = "";
        if (bytes / G >= 1) {
            str += (float) bytes / G;
            str = str.substring(0, str.indexOf('.') + 2) + "G";
        } else if (bytes / MB >= 1) {
            str += (float) bytes / MB;
            str = str.substring(0, str.indexOf('.') + 2) + "MB";
        } else if (bytes / KB >= 1) {
            str += (float) bytes / KB;
            str = str.substring(0, str.indexOf('.') + 2) + "KB";
        }
        return str;
    }

    /**
     * 单位转换
     *
     * @param context 当前应用
     * @param bytes   字节数
     * @return 对应的合适单位
     */
    public static String convertUnit(Context context, long bytes) {
        return Formatter.formatFileSize(context, bytes);
    }

    /**
     * 获取文件的扩展名
     *
     * @param file 文件名(String)
     * @return 文件的 扩展名
     */
    public static String getFileExtension(String file) throws IOException {
        return getFileExtension(new File(file));
    }

    /**
     * 获取文件的扩展名
     *
     * @param file 文件名(file)
     * @return 文件的 扩展名
     */
    public static String getFileExtension(File file) {
        if (!file.exists()) {
            throw new FileNotExistsException(file);
        }

        if (file.isDirectory()) {
            throw new RuntimeException("Source '" + file + "' exists but is a directory");
        }

        String fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length() + 1);
    }

    /**
     * 复制文件到目的
     *
     * @param srcFile  源文件
     * @param destFile 目的
     * @throws IOException 复制异常
     */
    public static void copyFile(File srcFile, File destFile) throws IOException {
        if (srcFile == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (destFile == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!srcFile.exists()) {//源文件不存在
            throw new FileNotFoundException("Source '" + srcFile + "' does not exist");
        }

        if (srcFile.isDirectory()) {//源资源是目录
            throw new IOException("Source '" + srcFile + "' exists but is a directory");
        }

        if (srcFile.getCanonicalPath().equals(destFile.getCanonicalPath())) {//源文件和目录路径相同
            throw new IOException("Source '" + srcFile + "' and destination '" + destFile + "' are the same");
        }

        File parentFile = destFile.getParentFile();
        if (parentFile != null) {//父路径存在
            if (!parentFile.mkdirs() && !parentFile.isDirectory()) {
                throw new IOException("Destination '" + parentFile + "' directory cannot be created");
            }
        }

        if (destFile.exists() && !destFile.canWrite()) {//只读文件
            throw new IOException("Destination '" + destFile + "' exists but is read-only");
        }

        doCopyFile(srcFile, destFile);
    }

    /**
     * 开始拷贝文件
     *
     * @param srcFile  源文件
     * @param destFile 目的
     * @throws IOException 拷贝异常
     * @see solipsisming.util.io 简单流工具
     */
    private static void doCopyFile(File srcFile, File destFile) throws IOException {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel input = null;
        FileChannel output = null;
        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);
            input = fis.getChannel();
            output = fos.getChannel();
            long size = input.size();
            long pos = 0;
            long count;
            while (pos < size) {
                count = size - pos > FILE_COPY_BUFFER_SIZE ? FILE_COPY_BUFFER_SIZE : size - pos;
                pos += output.transferFrom(input, pos, count);
            }
        } finally {
            EasyIO.close(output);
            EasyIO.close(fos);
            EasyIO.close(input);
            EasyIO.close(fis);
        }

        if (srcFile.length() != destFile.length()) {//复制失败
            throw new IOException("Failed to copy full contents from '" +
                    srcFile + "' to '" + destFile + "'");
        }
    }

    /**
     * 复制目录到目录
     *
     * @param srcDir  源目录
     * @param destDir 目的目录
     * @throws IOException 复制异常
     */
    public static void copyDirectory(File srcDir, File destDir) throws IOException {
        if (srcDir == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (destDir == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!srcDir.exists()) {
            throw new FileNotFoundException("Source '" + srcDir + "' does not exist");
        }
        if (!srcDir.isDirectory()) {
            throw new IOException("Source '" + srcDir + "' exists but is not a directory");
        }
        if (srcDir.getCanonicalPath().equals(destDir.getCanonicalPath())) {
            throw new IOException("Source '" + srcDir + "' and destination '" + destDir + "' are the same");
        }

        List<String> exclusionList = null;
        if (destDir.getCanonicalPath().startsWith(srcDir.getCanonicalPath())) {//目的目录存在源目录的文件
            File[] srcFiles = srcDir.listFiles();
            if (srcFiles != null && srcFiles.length > 0) {
                exclusionList = new ArrayList<String>(srcFiles.length);
                for (File srcFile : srcFiles) {
                    File copiedFile = new File(destDir, srcFile.getName());
                    exclusionList.add(copiedFile.getCanonicalPath());
                }
            }
        }
        doCopyDirectory(srcDir, new File(destDir, srcDir.getName()), exclusionList);
    }

    /**
     * 开始复制目录
     *
     * @param srcDir        源目录
     * @param destDir       目的目录
     * @param exclusionList 排除不用复制的文件
     * @throws IOException 复制异常
     */
    private static void doCopyDirectory(File srcDir, File destDir, List<String> exclusionList) throws IOException {
        File[] srcFiles = srcDir.listFiles();
        if (srcFiles == null) {
            throw new IOException("Failed to list contents of " + srcDir);
        }

        if (destDir.exists()) {
            if (destDir.isDirectory() == false) {
                throw new IOException("Destination '" + destDir + "' exists but is not a directory");
            }
        } else {
            if (!destDir.mkdirs() && !destDir.isDirectory()) {
                throw new IOException("Destination '" + destDir + "' directory cannot be created");
            }
        }
        if (destDir.canWrite() == false) {
            throw new IOException("Destination '" + destDir + "' cannot be written to");
        }

        for (File srcFile : srcFiles) {
            File dstFile = new File(destDir, srcFile.getName());
            if (exclusionList == null || !exclusionList.contains(srcFile.getCanonicalPath())) {
                if (srcFile.isDirectory()) {
                    doCopyDirectory(srcFile, dstFile, exclusionList);
                } else {
                    doCopyFile(srcFile, dstFile);
                }
            }
        }
    }

    /**
     * 移动目录
     *
     * @param srcDir  源目录
     * @param destDir 目的目录
     * @throws IOException 移动异常
     */
    public static void moveDirectory(File srcDir, File destDir) throws IOException {
        if (srcDir == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (destDir == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!srcDir.exists()) {
            throw new FileNotFoundException("Source '" + srcDir + "' does not exist");
        }
        if (!srcDir.isDirectory()) {
            throw new IOException("Source '" + srcDir + "' is not a directory");
        }

        if (destDir.exists()) {
            throw new FileExistsException("Destination '" + destDir + "' already exists");
        }

        boolean rename = srcDir.renameTo(destDir);
        if (!rename) {
            if (destDir.getCanonicalPath().startsWith(srcDir.getCanonicalPath())) {
                throw new IOException("Cannot move directory: " + srcDir + " to a subdirectory of itself: " + destDir);
            }
            copyDirectory(srcDir, destDir);
            delete(srcDir);
            if (srcDir.exists()) {
                throw new IOException("Failed to delete original directory '" + srcDir +
                        "' after copy to '" + destDir + "'");
            }
        }
    }

    /**
     * 移动文件
     *
     * @param srcFile  源文件
     * @param destFile 目的目录
     * @throws IOException 移动异常
     */
    public static void moveFile(File srcFile, File destFile) throws IOException {
        if (srcFile == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (destFile == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!srcFile.exists()) {
            throw new FileNotFoundException("Source '" + srcFile + "' does not exist");
        }
        if (srcFile.isDirectory()) {
            throw new IOException("Source '" + srcFile + "' is a directory");
        }
        if (destFile.exists()) {
            throw new FileExistsException("Destination '" + destFile + "' already exists");
        }
        if (destFile.isDirectory()) {
            throw new IOException("Destination '" + destFile + "' is a directory");
        }

        boolean rename = srcFile.renameTo(destFile);
        if (!rename) {
            copyFile(srcFile, destFile);
            if (!srcFile.delete()) {
                delete(destFile);
                throw new IOException("Failed to delete original file '" + srcFile +
                        "' after copy to '" + destFile + "'");
            }
        }
    }

    /**
     * 求指定路径的根目录空间状况
     *
     * @param file 文件路径
     * @return 空间存储情况
     */
    public static Storage calculateRootStorage(File file) {
        if (!file.exists()) {
            throw new FileNotExistsException();
        }

        StatFs stat = new StatFs(file.getPath());
        long totalBytes;//总共空间
        long availableBytes;//可用空间
        Storage storage = new Storage();

        if (Build.VERSION.SDK_INT >= 18) {//api18以上支持
            availableBytes = stat.getAvailableBytes();//获取可用空间的大小
            totalBytes = stat.getTotalBytes();//获取空间的大小
            long freeBytes = stat.getFreeBytes();//获取可用空间的大小(包括预留的一般程序无法使用的块) 即将过时
            long availableBlocksLong = stat.getAvailableBlocksLong();//获取可用块的数量
            long freeBlocksLong = stat.getFreeBlocksLong();//获取可用块的大小(包括预留的一般程序无法使用的块) 即将过时
            long blockCountLong = stat.getBlockCountLong();//获取所有块的数量
            long blockSizeLong = stat.getBlockSizeLong();//获取块的大小
        } else {//api18以下支持
            long availableBlocks = stat.getAvailableBlocks();//获取可用块的数量
            long blockSize = stat.getBlockSize();//获取块的大小
            long totalBlocks = stat.getBlockCount();//获取所有块的数量
            int freeBlocks = stat.getFreeBlocks();//获取可用块的数量(包括预留的一般程序无法使用的块)
            totalBytes = totalBlocks * blockSize;//计算总空间大小
            availableBytes = availableBlocks * blockSize;//计算可用空间大小;
        }
        storage.totalBytes = totalBytes;
        storage.availableBytes = availableBytes;
        storage.usedBytes = (totalBytes - availableBytes);
        return storage;
    }

    /**
     * 计算sdcard的存储状况
     *
     * @return sdcard存储状况
     */
    public static Storage calculateSdcardStorage() {
        if (DeviceUtils.isSdCardUseful()) {
            return calculateRootStorage(Environment.getExternalStorageDirectory());
        }
        return null;
    }

    /**
     * 空间信息内部类
     */
    public static class Storage {
        /**
         * 防止开发程序员创建
         */
        Storage() {
        }

        private long availableBytes;//可用空间大小
        private long totalBytes;//总空间大小
        private long usedBytes;//已用空间大小

        public long getAvailableBytes() {
            return availableBytes;
        }

        public long getTotalBytes() {
            return totalBytes;
        }

        public long getUsedBytes() {
            return usedBytes;
        }

    }
}