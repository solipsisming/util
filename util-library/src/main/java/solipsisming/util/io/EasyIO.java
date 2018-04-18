package solipsisming.util.io;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.Socket;

import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * io工具</p>
 * 创建于 2015-5-29 21:06:16
 *
 * @author 洪东明
 * @version 1.0
 */
public class EasyIO {

    private static final int DEFAULT_BUFFER_SIZE = 1024;//缓冲大小
    private static final String DEFAULT_ENCODING = "UTF-8";//编码
    private static final int EOF = -1;

    /**
     * 禁止创建对象
     */
    private EasyIO() {
        throw new UnacceptableInstanceError();
    }

    /**
     * 字符文本的读
     *
     * @param file 文件(String)
     * @return 读取后的数据
     * @throws IOException 读异常和关闭异常
     */
    public static String read(String file) throws IOException {
        return read(new File(file));
    }

    /**
     * 字符文本的读
     *
     * @param file 文件(file)
     * @return 读取后的数据
     * @throws IOException 读异常和关闭异常
     */
    public static String read(File file) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null)
                sb.append(line);
            return sb.toString();
        } finally {
            close(br);
        }
    }

    /**
     * 字节数据的读
     *
     * @param file 文件(String)
     * @return 读取后的数据
     * @throws IOException 读异常和关闭异常
     */
    public static String readStream(String file) throws IOException {
        return readStream(new File(file));
    }

    /**
     * 字节数据的读
     *
     * @param file 文件(file)
     * @return 读取后的数据
     * @throws IOException 读异常和关闭异常
     */
    public static String readStream(File file) throws IOException {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[DEFAULT_BUFFER_SIZE];
        int len;
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            while ((len = bis.read(buf)) != EOF)
                bos.write(buf, 0, len);
            return new String(bos.toByteArray(), DEFAULT_ENCODING);
        } finally {
            close(bis);
            close(bos);
        }
    }

    /**
     * 字符的写入
     *
     * @param file 目的文件(String)
     * @param data 数据
     * @throws IOException 写异常和关闭异常
     */
    public static void write(String file, String data) throws IOException {
        write(new File(file), data);
    }

    /**
     * 字符的写入
     *
     * @param file 目的文件(File)
     * @param data 数据
     * @throws IOException 写异常和关闭异常
     */
    public static void write(File file, String data) throws IOException {
        BufferedWriter br = null;
        try {
            br = new BufferedWriter(new FileWriter(file));
            br.write(data);
        } finally {
            close(br);
        }
    }

    /**
     * 将流写入指定文件
     *
     * @param file 文件路径(string)
     * @param is   源流
     * @throws IOException 写入或关闭异常
     */
    public static void write(String file, InputStream is) throws IOException {
        write(new File(file), is);
    }

    /**
     * 将流写入指定文件
     *
     * @param file 文件路径(file)
     * @param is   源流
     * @throws IOException 写入或关闭异常
     */
    public static void write(File file, InputStream is) throws IOException {
        BufferedOutputStream bos = null;
        try {
            BufferedInputStream bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(new FileOutputStream(file));
            byte[] buf = new byte[DEFAULT_BUFFER_SIZE];
            int len;
            while ((len = bis.read(buf)) != EOF) {
                bos.write(buf, 0, len);
                bos.flush();
            }
        } finally {
            close(bos);
        }
    }

    /**
     * 字符的写入
     *
     * @param file 目的文件(String)
     * @param buf  数据
     * @throws IOException 写异常和关闭异常
     */
    public static void write(String file, byte[] buf) throws IOException {
        write(new File(file), buf);
    }

    /**
     * 字符的写入
     *
     * @param file 目的文件(file)
     * @param buf  数据
     * @throws IOException 写异常和关闭异常
     */
    public static void write(File file, byte[] buf) throws IOException {
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
            bos.write(buf);
        } finally {
            close(bos);
        }
    }

    /**
     * 将输入字节流转换为字符串
     *
     * @param input 输入字节流
     * @return 字符串
     * @throws IOException 转换异常
     */
    public static String toString(InputStream input) throws IOException {
        return toString(input, DEFAULT_ENCODING);
    }

    /**
     * 将输入字节流转换为字符串,带编码
     *
     * @param input    输入字节流
     * @param encoding 编码
     * @return 字符串
     * @throws IOException 转换异常
     */
    public static String toString(InputStream input, String encoding) throws IOException {
        StringBuilderWriter sw = new StringBuilderWriter();
        copy(input, sw, encoding);
        return sw.toString();
    }

    /**
     * 将输入字符流转换为字符串
     *
     * @param input 输入字符流
     * @return 字符串
     * @throws IOException 转换异常
     */
    public static String toString(Reader input) throws IOException {
        StringBuilderWriter sw = new StringBuilderWriter();
        copy(input, sw);
        return sw.toString();
    }

    /**
     * 将字节流转换为字节
     *
     * @param input 输入流
     * @return 字节
     * @throws IOException 转换异常
     */
    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(input, output);
        return output.toByteArray();
    }

    /**
     * 将字符流转换为字节
     *
     * @param input 字符流
     * @return 字节
     * @throws IOException 转换异常
     */
    public static byte[] toByteArray(Reader input) throws IOException {
        return toByteArray(input, DEFAULT_ENCODING);
    }

    /**
     * 将字符流转换为字节,带编码
     *
     * @param input    字符流
     * @param encoding 编码
     * @return 字节
     * @throws IOException 转换异常
     */
    public static byte[] toByteArray(Reader input, String encoding) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(input, output, encoding);
        return output.toByteArray();
    }

    /**
     * 将字节流转换为字符
     *
     * @param is 字节流
     * @return 字符
     * @throws IOException 转换异常
     */
    public static char[] toCharArray(InputStream is) throws IOException {
        return toCharArray(is, DEFAULT_ENCODING);
    }

    /**
     * 将字节流转换为字符,带编码
     *
     * @param is       字节流
     * @param encoding 编码
     * @return 字符
     * @throws IOException 转换异常
     */
    public static char[] toCharArray(InputStream is, String encoding) throws IOException {
        CharArrayWriter output = new CharArrayWriter();
        copy(is, output, encoding);
        return output.toCharArray();
    }

    /**
     * 将字符流装换为字节
     *
     * @param input 字符流
     * @return 字符
     * @throws IOException 转换异常
     */
    public static char[] toCharArray(Reader input) throws IOException {
        CharArrayWriter sw = new CharArrayWriter();
        copy(input, sw);
        return sw.toCharArray();
    }

    /**
     * 将字节输入流输入到字节输出流
     *
     * @param input  字节输入流
     * @param output 字节输出流
     * @return 字节数
     * @throws IOException 输入异常
     */
    public static int copy(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        long count = 0;
        int n;
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }

        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }

    /**
     * 将字符输入流输入到字符输出流
     *
     * @param input  字符输入流
     * @param output 字符输出流
     * @return 字符数
     * @throws IOException 输入异常
     */
    public static int copy(Reader input, Writer output) throws IOException {
        char[] buffer = new char[DEFAULT_BUFFER_SIZE];
        long count = 0;
        int n;
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }

    /**
     * 将字符输入流输入到字节输出流
     *
     * @param input  字符输入流
     * @param output 字节输出流
     * @throws IOException 输入异常
     */
    public static void copy(Reader input, OutputStream output) throws IOException {
        copy(input, output, DEFAULT_ENCODING);
    }

    /**
     * 将字符输入流输入到字节输出流
     *
     * @param input    字符输入流
     * @param output   字节输出流
     * @param encoding 编码
     * @throws IOException 输入异常
     */
    public static void copy(Reader input, OutputStream output, String encoding) throws IOException {
        OutputStreamWriter out = new OutputStreamWriter(output, encoding);
        out.flush();
        copy(input, out);
    }

    /**
     * 将字节输入流输入到字符输出流
     *
     * @param input  字节输入流
     * @param output 字符输出流
     * @throws IOException 输入异常
     */
    public static void copy(InputStream input, Writer output) throws IOException {
        copy(input, output, DEFAULT_ENCODING);
    }

    /**
     * 将字节输入流输入到字符输出流
     *
     * @param input    字节输入流
     * @param output   字符输出流
     * @param encoding 编码
     * @throws IOException 输入异常
     */
    public static void copy(InputStream input, Writer output, String encoding) throws IOException {
        InputStreamReader in = new InputStreamReader(input, encoding);
        copy(in, output);
    }

    /**
     * 关闭socket
     *
     * @param socket 套接字
     */
    public static void close(Socket socket) {
        close((Closeable) socket);
    }

    /**
     * 关闭流
     *
     * @param input 字符输入流
     */
    public static void close(Reader input) {
        close((Closeable) input);
    }

    /**
     * 关闭流
     *
     * @param output 字符输出流
     */
    public static void close(Writer output) {
        close((Closeable) output);
    }

    /**
     * 关闭流
     *
     * @param input 字节输出流
     */
    public static void close(InputStream input) {
        close((Closeable) input);
    }

    /**
     * 关闭流
     *
     * @param output 字节输出流
     */
    public static void close(OutputStream output) {
        close((Closeable) output);
    }

    /**
     * 关闭游标
     *
     * @param cursor 游标
     */
    public static void closeCursor(Cursor cursor) {
        close(cursor);
    }

    /**
     * 关闭数据库
     *
     * @param database 数据库
     */
    public static void closeSQLiteDatabase(SQLiteDatabase database) {
        close(database);
    }

    /**
     * 关闭流
     *
     * @param closeable 关闭接口
     */
    public static void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接
     *
     * @param connection 数据库
     */
    public static void closeConnection(HttpURLConnection connection) {
        if (connection != null)
            connection.disconnect();
    }
}