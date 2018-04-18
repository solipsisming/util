package solipsisming.util.gallery;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.Build;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import solipsisming.util.exception.CompressWrongException;
import solipsisming.util.exception.UnacceptableInstanceError;
import solipsisming.util.exception.VersionIncompatibleException;
import solipsisming.util.io.EasyIO;

/**
 * 位图转换工具
 * </p>
 * <ul>
 * <li> 使用Drawable保存图片对象，占用更小的内存空间。</li>
 * <li>而使用Biamtp对象，则会占用很大内存空间，很容易就出现OOM了</li>
 * <li>使用BitmapFactory的decodeResource方法会占据大量内存，</li>
 * <li>使用BitmapFactory的decodeStream方法，则占据更小的内存。</li>
 * <li>使用BitmapFactory的decodeStream无论是时间上还是空间上，都比decodeResource方法更优秀</li>
 * </ul>
 * </p>
 * 创建于 2015-6-10 23:41:39
 *
 * @author 洪东明
 * @version 1.0
 */
public class ImageUtils {

    /**
     * 禁止创建对象
     */
    private ImageUtils() {
        throw new UnacceptableInstanceError();
    }

    /**
     * drawable转换位图
     *
     * @param drawable drawable
     * @return 位图
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        BitmapDrawable bd = (BitmapDrawable) drawable;
        return bd.getBitmap();
    }

    /**
     * drawable转换为位图
     *
     * @param drawable Drawable
     * @return 位图
     * @deprecated 过于复杂
     */
    @Deprecated
    public static Bitmap drawableToBitmapByCanvas(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity()
                != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;//取drawable的颜色格式
        Bitmap bitmap = Bitmap.createBitmap(width, height, config); //建立对应bitmap
        Canvas canvas = new Canvas(bitmap);//建立对应bitmap的画布
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);// 把 drawable 内容画到画布中
        return bitmap;
    }

    /**
     * 位图转换drawable
     *
     * @param context 当前应用
     * @param bitmap  位图
     * @return drawable
     */
    public static Drawable bitmapToDrawable(Context context, Bitmap bitmap) {
        if (bitmap == null) {//如果bitmap为null默认返回drawable,但是不带图片
            throw new NullPointerException("args bitmap is null)");
        }
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    /**
     * 将流转换为位图
     *
     * @param filePath 文件路径
     * @return Drawable 图片
     * @see solipsisming.util.common.StringUtils string工具类
     */
    public static Drawable getDrawable(String filePath) {
        return BitmapDrawable.createFromPath(filePath);
    }

    /**
     * 将流转换为位图
     *
     * @param inputStream 输出流
     * @return drawable  drawable
     */
    public static Drawable getDrawable(InputStream inputStream) {
        return BitmapDrawable.createFromStream(inputStream, null);
    }


    /**
     * 将id转换为位图，<b>相对占用内存</b>
     *
     * @param context 当前应用
     * @param id      图片资源id
     * @param opts    图片设置
     * @return 位图
     */
    public static Bitmap getBitmap(Context context, int id, BitmapFactory.Options opts) {
        return BitmapFactory.decodeResource(context.getResources(), id, opts);
    }

    /**
     * 将id转换为位图
     *
     * @param descriptor 图片资源id
     * @return 位图
     */
    public static Bitmap getBitmap(FileDescriptor descriptor) {
        return BitmapFactory.decodeFileDescriptor(descriptor);
    }

    /**
     * 获取bitmap(API>=21)
     *
     * @param context 当前应用
     * @param id      图片资源id
     * @return 位图
     */
    @TargetApi(21)
    public static Bitmap getBitmap(Context context, int id) {
        if (Build.VERSION.SDK_INT < 21) {
            throw new VersionIncompatibleException
                    ("current version is to low,this method request sdk.version>=21");
        } else {//适用于API>=21,效率更高
            Drawable drawable = context.getResources().getDrawable(id, null);
            BitmapDrawable bd = (BitmapDrawable) drawable;
            if (bd != null)
                return bd.getBitmap();
            return null;
        }
    }

    /**
     * 将字节转换为位图
     *
     * @param b    字节
     * @param opts 图片设置
     * @return 位图
     */
    public static Bitmap getBitmap(byte[] b, BitmapFactory.Options opts) {
        if (opts != null)
            return BitmapFactory.decodeByteArray(b, 0, b.length, opts);
        return BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    /**
     * 将字节转换为位图
     *
     * @param filePath 文件路径
     * @param opts     图片设置
     * @return 位图
     */
    public static Bitmap getBitmap(String filePath, BitmapFactory.Options opts) {
        if (opts != null)
            return BitmapFactory.decodeFile(filePath, opts);
        return BitmapFactory.decodeFile(filePath);
    }

    /**
     * 将字节转换为bitmap
     *
     * @param inputStream 输出流
     * @param opts        位图参数
     * @return 位图
     */
    public static Bitmap getBitmap(InputStream inputStream, BitmapFactory.Options opts) {
        if (opts != null)
            return BitmapFactory.decodeStream(inputStream, null, opts);
        return BitmapFactory.decodeStream(inputStream);
    }

    /**
     * 将url转换为bitmap
     *
     * @param url  位图url地址
     * @param opts 位图参数
     * @return 位图
     */
    public static Bitmap getBitmap(URL url, BitmapFactory.Options opts) {
        try {
            return getBitmap(url.openStream(), opts);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将位图转换为字节
     *
     * @param bitmap 位图
     * @return 位图的字节
     */
    public static byte[] bitmapToByte(Bitmap bitmap) {
        if (bitmap == null) {//如果bitmap为null默认返回drawable,但是不带图片
            throw new NullPointerException("args bitmap is null)");
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);//压缩图片
        return bos.toByteArray();
    }

    /**
     * 缩放图片
     *
     * @param bitmap 位图
     * @param width  目的宽度
     * @param height 目的高度
     * @return 缩放后的位图
     * @deprecated 不确定性能是否可用
     */
    @Deprecated
    public static Bitmap scaleBitmap(Bitmap bitmap, int width, int height) {
        Bitmap temp = Bitmap.createScaledBitmap(bitmap, width, height, true);
        bitmap.recycle();
        return temp;
    }

    /**
     * 缩放图片
     *
     * @param context 当前应用
     * @param id      资源id
     * @param width   目的宽度
     * @param height  目的高度
     * @return 缩放后的位图
     */
    public static Bitmap scaleBitmap(Context context, int id, int width, int height) {
        return scaleBitmap(getBitmap(context, id, null), width, height, true);
    }

    /**
     * 缩放图片
     *
     * @param bitmap 即将缩放的位图
     * @param width  目的宽度
     * @param height 目的高度
     * @param flag   是否回收传入的位图
     * @return 缩放后的位图
     */
    public static Bitmap scaleBitmap(Bitmap bitmap, int width, int height, boolean flag) {
        if (flag)//需要回收传入的图片
            return ThumbnailUtils.extractThumbnail(bitmap, width,
                    height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return ThumbnailUtils.extractThumbnail(bitmap, width, height);
    }

    /**
     * 保存bitmap到本地
     *
     * @param bitmap   位图
     * @param filePath 路径
     * @param format   文件的格式
     * @return 保存是否成功
     * @throws IOException IO异常
     * @see solipsisming.util.common.StringUtils string工具类
     */
    public static boolean saveBitmap(Bitmap bitmap, String filePath,
                                     Bitmap.CompressFormat format) throws IOException {
        if (bitmap == null) {//如果bitmap为null默认返回drawable,但是不带图片
            throw new NullPointerException("args bitmap is null)");
        }
        FileOutputStream fos = new FileOutputStream(filePath);
        boolean result = bitmap.compress(format, 100, fos);
        fos.close();
        return result;
    }

    /**
     * 图片质量压缩
     *
     * @param bitmap   位图
     * @param format   位图格式
     * @param needSize 压缩要求大小
     * @return 压缩后的图片
     * @throws CompressWrongException 压缩异常
     * @see solipsisming.util.io.EasyIO 简单流工具
     */
    public static Bitmap compressBitmap(Bitmap bitmap, long needSize,
                                        Bitmap.CompressFormat format)
            throws CompressWrongException {
        if (bitmap == null)
            throw new NullPointerException("args bitmap is null,unable compress");

        if (format == null)
            format = Bitmap.CompressFormat.JPEG;
        if (needSize <= 1024)//压缩后的图片大小必须是实际的
            throw new IllegalArgumentException("size must be > 1024");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(format, 100, bos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        long len = bos.toByteArray().length;

        while (len > needSize) {//循环判断如果压缩后图片是否大于size,大于继续压缩
            bos.reset();//清空bos
            options -= 10;//每次都减少10
            if (options == 0) {
                try {
                    throw new CompressWrongException();//无法压缩到指定的大小
                } finally {
                    EasyIO.close(bos);
                }
            }
            bitmap.compress(format, options, bos);//这里压缩options%，把压缩后的数据存放到bos中
            len = bos.toByteArray().length;
        }

        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());//把压缩后的数据bos存放到ByteArrayInputStream中
        Bitmap b = getBitmap(bis, null);//把ByteArrayInputStream数据生成图片
        bis.reset();
        EasyIO.close(bis);
        return b;
    }

    /**
     * 当图片过大时只解析图片部分，像地图
     *
     * @param inputStream 输出流
     * @param left        图片的左位置
     * @param top         图片的上位置
     * @param right       图片的右位置
     * @param bottom      图片的下位置
     * @param opts        图片参数
     * @return 解析特定区域后的图片
     * @throws IOException 获取位图异常
     */
    public static Bitmap decodeBitmapPart(InputStream inputStream, int left, int top,
                                          int right, int bottom,
                                          BitmapFactory.Options opts) throws IOException {
        if (inputStream == null) {//如果bitmap为null默认返回drawable,但是不带图片
            throw new NullPointerException("args inputStream is null)");
        }

        BitmapRegionDecoder bd = BitmapRegionDecoder.newInstance(inputStream, true);
        Rect rect = new Rect();//矩形区域
        rect.set(left, top, right, bottom);
        if (opts == null) {//如果没有设置图片参数
            opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;//能在不分配资源给图片的情况下获取图片的大小
            opts.inDither = false;//不进行图片抖动处理
            opts.inSampleSize = 2;//使图片变成原来的1/2
            opts.inInputShareable = true;//那么它可以决定位图是否能够共享一个指向数据源的引用，或者是进行一份拷贝；
            opts.inPurgeable = true;//则由此产生的位图将分配其像素，以便系统需要回收内存时可以将它们清除；
            opts.inPreferredConfig = Bitmap.Config.ARGB_8888;//图片的像素值 默认
        }
        Bitmap temp = bd.decodeRegion(rect, opts);
        bd.recycle();
        return temp;
    }

    /**
     * 剪切图片
     *
     * @param bitmap 位图
     * @param x      剪切x坐标
     * @param y      剪切y坐标
     * @param width  剪切宽度
     * @param height 剪切高度
     * @return 剪切后的位图
     */
    public static Bitmap cutBitmap(Bitmap bitmap, int x, int y, int width, int height) {
        Bitmap temp = Bitmap.createBitmap(bitmap, x, y, width, height);
        bitmap.recycle();
        return temp;
    }

    public static Bitmap compressWithWidth(Bitmap bitmap, int width) {
        int bmpWidth = bitmap.getWidth();
        int bmpHeight = bitmap.getHeight();

        float scaleWidth = bmpWidth;
        float scaleHeight = bmpHeight;

        if (bmpWidth > width) {
            scaleWidth = width / scaleWidth;
            scaleHeight = scaleWidth;
        } else {
            scaleWidth = 1;
            scaleHeight = 1;
        }

        Matrix matrix = new Matrix();

        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bmpWidth, bmpHeight, matrix, false);

        //因为如果scaleWidth与scaleHeight都是1的话,那么就等于是原图,createBitmap返回的也是原始bitmap.
        //所以只有新建了bitmap,才能将原始bitmap回收掉,不然调用的时候就会因为bitmap已回收而挂掉.
        if (bitmap != resizeBitmap) {
            bitmap.recycle();
        }
        return resizeBitmap;
    }
}