package solipsisming.util.application;

import android.view.MotionEvent;

/**
 * 手势判断工具
 * </p>
 * 创建于 2015-7-21 19:22:06
 *
 * @author 洪东明
 * @version 1.0
 */
public class GestureUtils {
    /**
     * 空操作
     */
    public static final int EMPTY = 0;
    /**
     * 向上操作
     */
    public static final int UP = 1;
    /**
     * 向下操作
     */
    public static final int DOWN = 2;
    /**
     * 向左操作
     */
    public static final int LEFT = 3;
    /**
     * 向右操作
     */
    public static final int RIGHT = 4;
    /**
     * 移动距离
     */
    public static final int FLING_MIN_DISTANCE = 100;
    /**
     * 移动速率
     */
    public static final int FLING_MIN_VELOCITY = 200;

    /**
     * 触发条件: X轴的坐标位移大于FLING_MIN_DISTANCE，且移动速度大于FLING_MIN_VELOCITY个像素/秒
     *
     * @param e1        第1个ACTION_DOWN MotionEvent
     * @param e2        最后一个ACTION_MOVE MotionEvent
     * @param velocityX X轴上的移动速度，像素/秒
     * @param velocityY Y轴上的移动速度，像素/秒
     * @return 手势走向
     */
    public int onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float e1x = e1.getX();
        float e2x = e2.getX();
        float e2y = e1.getY();
        float e1y = e1.getY();
        if (e1x - e2x > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {//向左
            return LEFT;
        } else if (e2x - e1x > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {//向右
            return RIGHT;
        } else if (e2y - e1y > FLING_MIN_DISTANCE && Math.abs(velocityY) > FLING_MIN_VELOCITY) {//向下
            return DOWN;
        } else if (e1y - e2y > FLING_MIN_DISTANCE && Math.abs(velocityY) > FLING_MIN_VELOCITY) {//向上
            return UP;
        }
        return EMPTY;
    }
}