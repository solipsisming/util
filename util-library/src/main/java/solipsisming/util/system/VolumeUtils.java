package solipsisming.util.system;

import android.content.Context;
import android.media.AudioManager;

import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * 音量控制工具
 * <ul>
 * <li>AudioManager.STREAM_VOICE_CALL 电话音量</li>
 * <li>AudioManager.STREAM_SYSTEM 系统音量</li>
 * <li>AudioManager.STREAM_RING  铃声音量</li>
 * <li>AudioManager.STREAM_MUSIC 音乐音量</li>
 * <li>AudioManager.STREAM_NOTIFICATION 提示音量</li>
 * <li> AudioManager.STREAM_ALARM 闹钟音量</li>
 * </ul>
 * </p>
 * 创建于 2015-7-15 21:39:29
 *
 * @author 洪东明
 * @version 1.0
 */
public class VolumeUtils {


    private VolumeUtils() {
        throw new UnacceptableInstanceError();
    }

    /**
     * 降低音量
     *
     * @param context 当前应用
     * @param model   音量模式
     */
    public static void lowVolume(Context context, int model) {
        AudioManager mAudioManager = (AudioManager)
                context.getSystemService(Context.AUDIO_SERVICE);
        if (mAudioManager != null)
            mAudioManager.adjustStreamVolume(model, AudioManager.ADJUST_LOWER,
                    AudioManager.FX_FOCUS_NAVIGATION_UP);
    }

    /**
     * 增加音量
     *
     * @param context 当前应用
     * @param model   音量模式
     */
    public static void raiseVolume(Context context, int model) {
        AudioManager mAudioManager = (AudioManager)
                context.getSystemService(Context.AUDIO_SERVICE);
        if (mAudioManager != null)
            mAudioManager.adjustStreamVolume(model, AudioManager.ADJUST_RAISE,
                    AudioManager.FX_FOCUS_NAVIGATION_UP);
    }

    /**
     * 获取当前音量
     *
     * @param context 当前应用
     * @param model   音量模式
     * @return 音量值
     */
    public static int getCurrentVolume(Context context, int model) {
        AudioManager mAudioManager = (AudioManager)
                context.getSystemService(Context.AUDIO_SERVICE);
        if (mAudioManager != null)
            return mAudioManager.getStreamVolume(model);
        return -1;
    }

    /**
     * 获取最大音量
     *
     * @param context 当前应用
     * @param model   音量模式
     * @return 音量值
     */
    public static int getMaxVolume(Context context, int model) {
        AudioManager mAudioManager = (AudioManager)
                context.getSystemService(Context.AUDIO_SERVICE);
        if (mAudioManager != null)
            return mAudioManager.getStreamMaxVolume(model);
        return -1;
    }


    /**
     * 设置静音
     *
     * @param context 当前应用
     * @param model   音量模式
     */
    public static void setSilentVolume(Context context, int model) {
        AudioManager mAudioManager = (AudioManager)
                context.getSystemService(Context.AUDIO_SERVICE);
        if (mAudioManager != null)
            mAudioManager.setStreamVolume(model, 0, 0);
    }

    /**
     * 设置音量
     *
     * @param context 当前应用
     * @param volume  音量值
     * @param model   音量模式
     */
    public static void setVolume(Context context, int volume, int model) {
        AudioManager mAudioManager = (AudioManager)
                context.getSystemService(Context.AUDIO_SERVICE);
        if (mAudioManager != null)
            mAudioManager.setStreamVolume(model, volume, 0);
    }
}