package solipsisming.util.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import solipsisming.util.exception.FileNotExistsException;
import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * 压缩工具</p>
 * 创建于 2015-6-17 19:58:21
 *
 * @author 洪东明
 * @version 1.0
 * @deprecated 压缩很耗内存和机身，不适合放在移动设备中执行
 */
@Deprecated
public class ZipUtils {

    private static final int BUFF = 1024 * 1024 * 5;//缓存大小

    private ZipUtils() {
        throw new UnacceptableInstanceError();
    }

    public static void UnZipFolder(String source, String target) throws IOException {
        File sourceFile = new File(source);
        if (!sourceFile.exists()) {
            throw new FileNotExistsException();
        }
        File targetFile = new File(target);
        UnZipFolder(sourceFile, targetFile);
    }

    /**
     * 解压文件
     *
     * @param source 源文件
     * @param target 目的
     * @throws IOException 解压异常，关闭异常
     * @see cn.com.corporate.katrina.util.io.EasyIO 简单流工具
     */
    private static void UnZipFolder(File source, File target) throws IOException {
        ZipInputStream inZip = null;
        try {
            inZip = new ZipInputStream(new FileInputStream(source));
            ZipEntry zipEntry;
            String zipFile;
            while ((zipEntry = inZip.getNextEntry()) != null) {//读取下一条条目
                zipFile = zipEntry.getName();
                StringBuilder sb = new StringBuilder();
                sb.append(target).append(File.separator);
                if (zipEntry.isDirectory()) {//目录
                    sb.append(zipFile.substring(0, zipFile.length() - 1));
                    File folder = new File(sb.toString());
                    folder.mkdirs();
                } else {//文件
                    sb.append(zipFile);
                    File file = new File(sb.toString());
                    File parent = file.getParentFile();
                    if (!parent.exists())//判断路径是否存在
                        parent.mkdirs();
                    file.createNewFile();
                    EasyIO.write(file, inZip);
                }
            }
        } finally {
            if (inZip != null)
                inZip.close();
        }
    }

    /**
     * 压缩文件
     *
     * @param source 源文件
     * @param target 目的文件
     * @throws Exception 压缩异常，关闭异常
     * @see cn.com.corporate.katrina.util.io.EasyIO 简单流工具
     */
    public static void zipFolder(String source, String target) throws Exception {
        if (source == null)
            throw new NullPointerException("args source is null");

        if (target == null)
            throw new NullPointerException("args target is null");

        ZipOutputStream outZip = null;
        try {
            outZip = new ZipOutputStream(new FileOutputStream(target));
            File file = new File(source);
            ZipFiles(file.getParent() + File.separator, file.getName(), outZip);
            outZip.finish();
        } finally {
            EasyIO.close(outZip);
        }
    }

    /**
     * 压缩目录或文件
     *
     * @param folder   目录
     * @param fileName 文件名
     * @param outZip   压缩流
     * @throws Exception 压缩异常，关闭异常
     * @see cn.com.corporate.katrina.util.io.EasyIO 简单流工具
     */
    private static void ZipFiles(String folder, String fileName, ZipOutputStream outZip) throws Exception {
        File file = new File(folder + fileName);
        if (file.isFile()) {// 是文件
            FileInputStream fis = null;
            try {
                ZipEntry zipEntry = new ZipEntry(fileName);// 创建条目
                outZip.putNextEntry(zipEntry);// 读取条目
                fis = new FileInputStream(file);
                int len;
                byte[] buffer = new byte[BUFF];
                while ((len = fis.read(buffer)) != -1)
                    outZip.write(buffer, 0, len);
            } finally {
                EasyIO.close(fis);
            }
        } else {// 是目录
            String files[] = file.list();
            if (files.length <= 0) {
                ZipEntry zipEntry = new ZipEntry(fileName + File.separator);
                outZip.putNextEntry(zipEntry);
            }
            for (String f : files) {
                ZipFiles(folder, fileName + File.separator + f, outZip);
            }
        }
    }

    /**
     * 解压特定的文件
     *
     * @param source zip压缩包
     * @param target 文件
     * @return 特定的文件流
     * @throws Exception 解压异常和关闭异常
     * @see cn.com.corporate.katrina.util.io.EasyIO 简单流工具
     */
    public static InputStream upZip(String source, String target) throws Exception {
        if (source == null)
            throw new NullPointerException("args source is null");

        if (target == null)
            throw new NullPointerException("args target is null");

        ZipFile zf = null;
        try {
            zf = new ZipFile(source);
            ZipEntry zipEntry = zf.getEntry(target);//获取指定条目
            return zf.getInputStream(zipEntry);
        } finally {
            EasyIO.close(zf);
        }
    }

    /**
     * 遍历压缩的文件
     *
     * @param zipFile       压缩包
     * @param containFolder 是否包含目录
     * @param containFile   是否包含文件
     * @return 返回遍历结果
     * @throws IOException 关闭异常，遍历异常
     * @see cn.com.corporate.katrina.util.io.EasyIO 简单流工具
     */
    public static List<File> getZipFileList(String zipFile, boolean containFolder, boolean containFile) throws IOException {
        if (zipFile == null)
            throw new NullPointerException("args zipFile is null");

        ArrayList<File> fileList = new ArrayList<File>();
        ZipInputStream inZip = null;
        try {
            inZip = new ZipInputStream(new FileInputStream(zipFile));
            ZipEntry zipEntry;
            String fileName;
            while ((zipEntry = inZip.getNextEntry()) != null) {//遍历压缩包
                fileName = zipEntry.getName();//获取文件名
                if (containFolder) { //是否包含目录
                    if (zipEntry.isDirectory()) {
                        fileName = fileName.substring(0, fileName.length() - 1);
                        File folder = new File(fileName);
                        fileList.add(folder);
                    }
                }
                if (containFile) {//是否包含文件
                    if (!zipEntry.isDirectory()) {
                        File file = new File(fileName);
                        fileList.add(file);
                    }
                }
            }
        } finally {
            EasyIO.close(inZip);
        }
        return fileList;
    }
}