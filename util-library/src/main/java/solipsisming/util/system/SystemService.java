package solipsisming.util.system;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;

import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * 打开系统服务</p>
 * 创建于 2015-6-11 19:56:11
 *
 * @author 洪东明
 * @version 1.0
 */
public class SystemService {

    /**
     * 禁止实例对象
     */
    private SystemService() {
        throw new UnacceptableInstanceError();
    }

    /**
     * 打电话
     *
     * @param number 当前的电话号码
     * @return intent
     */
    public static Intent openPhone(String number) {
        return new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
    }

    /**
     * 发送短信
     *
     * @param number  电话号码
     * @param message 消息
     * @return intent
     */
    public static Intent openMessage(String number, String message) {

        Uri uri = Uri.parse("smsto:" + number);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.putExtra("sms_body", message);
        return intent;
    }

    /**
     * 打开相机
     *
     * @param context 当前应用
     * @return intent
     */
    public static Intent openCamera1(Context context) {
        Intent intent = new Intent();
        intent.setAction("android.media.action.IMAGE_CAPTURE");
        intent.addCategory("android.intent.category.DEFAULT");
        return intent;
    }

    /**
     * 启动相机
     *
     * @param uri 拍完照片如果需要置顶存储目录
     */
    public static Intent openCamera2(Uri uri) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("autofocus", true);// 自动对焦
        intent.putExtra("fullScreen", false);// 全屏
        if (uri != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        return intent;
    }

    /**
     * 打开相机,<b>Api>=19</b>
     *
     * @return intent
     */
    @TargetApi(19)
    public static Intent openCamera3() {
        Intent intent;
        if (Build.VERSION.SDK_INT >= 19) {
            intent = new Intent();
            intent.setClassName("com.android.camera2", "com.android.camera.CameraLauncher");
            return intent;
        }
        return null;
    }

    /**
     * 打开相册
     *
     * @param context 当前应用
     * @return intent
     */
    public static Intent openGallery1(Context context) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        return intent;
    }

    /**
     * 打开相册
     *
     * @return intent
     */
    public static Intent openGallery2() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");//这个参数是确定要选择的内容为图片，
        intent.putExtra("return-data", true);//是否要返回值。
        return intent;
    }

    /**
     * 打开相册并剪切
     *
     * @return intent
     */
    public static Intent openGalleryAndCut(Uri uri, int x, int y, int width, int height) {
        Intent intent = new Intent();
        intent.setAction("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");// mUri是已经选择的图片Uri
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", x);//这个是裁剪时候的裁剪框的X方向的比例。
        intent.putExtra("aspectY", y);//这个是裁剪时候的裁剪框的Y方向的比例。
        intent.putExtra("outputX", width);//返回数据的时候的X像素大小。
        intent.putExtra("outputY", height);//返回的时候Y的像素大小。
        intent.putExtra("return-data", true);
        return intent;
    }

    /**
     * 打开设置界面
     *
     * @return intent
     */
    public static Intent openSetting() {
        return new Intent(Settings.ACTION_SETTINGS); //系统设置
    }

    /**
     * 打开无线界面
     *
     * @return intent
     */
    public static Intent openWlan() {
        return new Intent(Settings.ACTION_WIFI_SETTINGS);//WIFI设置
    }

    /**
     * 打开壁纸
     *
     * @return intent
     */
    public static Intent openWallPicture() {
        return new Intent(Intent.ACTION_SET_WALLPAPER);//打开壁纸设置
    }

    /**
     * 打开gps设置
     *
     * @return intent
     */
    public static Intent openGpsSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    /**
     * 打开网址
     *
     * @param url 网址
     * @return intent
     */
    public static Intent openNet(String url) {
        Uri uri = Uri.parse(url);
        return new Intent(Intent.ACTION_VIEW, uri);
    }
}