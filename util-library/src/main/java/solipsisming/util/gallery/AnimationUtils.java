package solipsisming.util.gallery;


import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import solipsisming.util.exception.UnacceptableInstanceError;
import solipsisming.util.system.PrintLog;

/**
 * 动画工具</p>
 * 创建于 2015-6-10 19:07:15
 *
 * @author 洪东明
 * @version 1.0
 */
public class AnimationUtils {

    /**
     * 禁止创建对象
     */
    private AnimationUtils() {
        throw new UnacceptableInstanceError();
    }

    /**
     * 平移动画
     *
     * @param fromX 开始X
     * @param toX   目的X
     * @param fromY 开始Y
     * @param toY   目的Y
     * @return 平移动画
     */
    public static Animation translateAnim(float fromX, float toX, float fromY, float toY) {
        return translateAnim(fromX, toX, fromY, toY, null);
    }

    /**
     * 平移动画
     *
     * @param fromX 开始X
     * @param toX   目的X
     * @param fromY 开始Y
     * @param toY   目的Y
     * @param acp   动画公共参数
     * @return 平移动画
     */
    public static Animation translateAnim(float fromX, float toX,
                                          float fromY, float toY, AnimCommonParam acp) {
        TranslateAnimation translate;
        if (acp == null) {//没有设置动画参数
            translate = new TranslateAnimation(fromX, toX, fromY, toY);
            return translate;
        }
        translate = new TranslateAnimation(acp.fromXType, fromX, acp.toXType, toX,
                acp.fromYType, fromY, acp.toYType, toY);
        setParameter(translate, acp);
        return translate;
    }

    /**
     * 旋转动画
     *
     * @param from 开始角度
     * @param to   结束角度
     * @return 旋转动画
     */
    public static Animation rotateAnim(float from, float to) {
        return rotateAnim(from, to, null);
    }

    /**
     * 旋转动画
     *
     * @param from 开始角度
     * @param to   结束角度
     * @param acp  动画公共参数
     * @return 旋转动画
     */
    public static Animation rotateAnim(float from, float to, AnimCommonParam acp) {
        RotateAnimation rotate;
        if (acp == null) {//没有设置动画参数
            rotate = new RotateAnimation(from, to);
            return rotate;
        }
        rotate = new RotateAnimation(from, to, acp.XType, acp.XValue, acp.YType, acp.YValue);
        setParameter(rotate, acp);
        return rotate;
    }

    /**
     * 透明度动画
     *
     * @param from 开始透明度
     * @param to   结束透明度
     * @return 透明度动画
     */
    public static Animation alphaAnim(float from, float to) {
        return alphaAnim(from, to, null);
    }

    /**
     * 透明度动画
     *
     * @param from 开始透明度
     * @param to   结束透明度
     * @param acp  动画公共参数
     * @return 透明度动画
     */
    public static Animation alphaAnim(float from, float to, AnimCommonParam acp) {
        AlphaAnimation alpha = new AlphaAnimation(from, to);
        if (acp == null)//没有设置动画参数
            return alpha;
        setParameter(alpha, acp);
        return alpha;
    }

    /**
     * 缩放动画
     *
     * @param fromX 开始縮放的X坐标
     * @param toX   结束结束缩放的X坐标
     * @param fromY 开始縮放的Y坐标
     * @param toY   结束结束缩放的Y坐标
     * @return 缩放动画
     */
    public static Animation scaleAnim(float fromX, float toX, float fromY, float toY) {
        return scaleAnim(fromX, toX, fromY, toY, null);
    }

    /**
     * 缩放动画
     *
     * @param fromX 开始縮放的X坐标
     * @param toX   结束结束缩放的X坐标
     * @param fromY 开始縮放的Y坐标
     * @param toY   结束结束缩放的Y坐标
     * @param acp   动画公共参数
     * @return 缩放动画
     */
    public static Animation scaleAnim(float fromX, float toX, float fromY,
                                      float toY, AnimCommonParam acp) {
        ScaleAnimation scale;
        if (acp == null) {//没有设置动画参数
            scale = new ScaleAnimation(fromX, toX, fromY, toY);
            return scale;
        }
        scale = new ScaleAnimation(fromX, toX, fromY, toY, acp.XType,
                acp.XValue, acp.YType, acp.YValue);
        setParameter(scale, acp);
        return scale;
    }

    /**
     * 组合动画
     *
     * @param anims 所有动画
     * @return 组合后的动画
     */
    public static AnimationSet combinationAnims(Animation... anims) {
        if (anims == null || anims.length < 0) {//没有传入动画
            PrintLog.logW("AnimationUtil.combinationAnims(args anims is null or length==0)");
            return null;
        }
        /*
          new AnimationSet(false)创建AnimationSet时，
          可以在每个添加到AnimationSet中的Animation都使用Interpolator，且效果都能清楚的观察。
            new AnimationSet(true)创建AnimationSet时，
            如果在添加到AnimationSet中的Animation设置Interpolator将无效果，
            通过设置AnimationSet的Interpolator可以设置所有动画的Interpolator且
            所有动画的Interpolator都一样。
         */
        AnimationSet set = new AnimationSet(false);
        for (Animation anim : anims)
            set.addAnimation(anim);
        return set;
    }

    /**
     * 设置参数
     *
     * @param anim 动画
     * @param acp  动画参数
     */
    private static void setParameter(Animation anim, AnimCommonParam acp) {
        anim.setRepeatCount(acp.count);
        anim.setRepeatMode(acp.model);
        anim.setDuration(acp.duration);
        anim.setFillAfter(acp.state);
    }

    /**
     * 动画公共参数</p>
     * <ul>
     * <li>Animation.RELATIVE_TO_SELF 参照自身</li>
     * <li>Animation.RELATIVE_TO_PARENT 参照父控件</li>
     * <li>Animation.ABSOLUTE 绝对坐标</li>
     * </ul>
     */
    public static class AnimCommonParam {
        /**
         * 执行次数
         */
        public int count = 1;
        /**
         * 持续时间
         */
        public int duration = 1000;
        /**
         * RESTART:重新开始
         * REVERSE:从尾开始
         */
        public int model = Animation.REVERSE;
        /**
         * 动画结束是否保持其结束状态
         */
        public boolean state = true;
        /**
         * x轴参照物
         */
        public int XType = Animation.ABSOLUTE;
        /**
         * 相对参照物的x坐标起始值(自身控件的一半为x轴)
         */
        public float XValue = 0;
        /**
         * y轴参照物
         */
        public int YType = Animation.ABSOLUTE;
        /**
         * 相对参照物的y坐标起始值(自身控件的一半为y轴)
         */
        public float YValue = 0;
        /**
         * 开始x坐标的参照物
         */
        public int fromXType = Animation.ABSOLUTE;
        /**
         * 开始y坐标的参照物
         */
        public int fromYType = Animation.ABSOLUTE;
        /**
         * 结束x坐标的参照物
         */
        public int toXType = Animation.ABSOLUTE;
        /**
         * 结束y坐标的参照物
         */
        public int toYType = Animation.ABSOLUTE;
    }
}