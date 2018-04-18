package solipsisming.util.gallery;

import android.content.Context;
import android.util.DisplayMetrics;

import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * dp/dip,px,sp之间的转换</p>
 * 创建于 2015-5-29 20:15:52
 *
 * @author 洪东明
 * @version 1.0
 */
public class ConvertUnit {

    private static DisplayMetrics metric;

    /**
     * 禁止创建对象
     */
    private ConvertUnit() {
        throw new UnacceptableInstanceError();
    }

    /**
     * 将px值转换为dp/dip值
     *
     * @param pxValue px值
     * @return dp值
     */
    public static int px2dp(Context context, float pxValue) {
        if (metric == null)
            metric = context.getResources().getDisplayMetrics();
        return (int) (pxValue / metric.density + 0.5f);
    }

    /**
     * 将dip/dp值转换为px值
     *
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(Context context, float dpValue) {
        if (metric == null)
            metric = context.getResources().getDisplayMetrics();
        return (int) (dpValue * metric.density + 0.5f);
    }

    /**
     * 将px值转换为sp值
     *
     * @param pxValue px值
     * @return sp值
     */
    public static int px2sp(Context context, float pxValue) {
        if (metric == null)
            metric = context.getResources().getDisplayMetrics();
        return (int) (pxValue / metric.scaledDensity + 0.5f);
    }

    /**
     * 将sp值转换为px值
     *
     * @param spValue sp值
     * @return px值
     */
    public static int sp2px(Context context, float spValue) {
        if (metric == null)
            metric = context.getResources().getDisplayMetrics();
        return (int) (spValue * metric.scaledDensity + 0.5f);
    }
}