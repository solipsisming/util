package solipsisming.util.system;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * 状态栏工具类</p>
 * 创建于 2018-3-22 11:56:48
 * <p>
 * 方法1
 * 原理：
 * 向 ContentView 中添加假 View, 然后利用 ChildView 的 marginTop 属性来
 * 模拟 fitsSystemWindows ,主要是通过修改 marginTop 的值可以在全屏模式和着色
 * 模式之间切换.
 * 缺陷：改变了 ChildView 的 marginTop 值
 * <p>
 * 方法2
 * 原理：
 * 因为 ParentView 的实质是一个 LinearLayout , 可以再其顶部添加 View .
 * 缺陷：
 * 着色模式下,会像由沉浸式状态栏引发的血案中一样出现一条黑线
 * <p>
 * 方法3
 * 原理：
 * 向 ContentView 中添加假 View, 然后利用ChildView 的 fitsSystemWindows
 * 属性来控制位置, 但是实现缺陷就是不能随时切换两种模式.
 * 缺陷：
 * 不能在不重启 Activity 的情况下切换模式.
 *
 * @author 洪东明
 * @version 1.0
 */
public class StatusBarUtils {

    /**
     * 禁止实例化
     */
    private StatusBarUtils() {
        throw new UnacceptableInstanceError();
    }

    /**
     * 状态栏紧贴的沉浸式
     *
     * @param activity  当前应用
     * @param colourful 颜色
     */
    public static void immersiveFull(Activity activity, int colourful) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            lollipopFull(activity, colourful);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            kitkatFull1(activity);
//            kitkatFull2(activity);
//            kitkatFull3(activity);
        }
    }

    /**
     * 状态栏非紧贴的沉浸式
     *
     * @param activity  当前应用
     * @param colourful 颜色
     */
    public static void immersiveColour(Activity activity, int colourful) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            lollipopColour(activity, colourful);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            kitkatColour1(activity, colourful);
//            kitkatColour2(activity, colourful);
//            kitkatColour3(activity, colourful);
        }
    }

    /**
     * 5.0以上沉浸式全屏模式
     *
     * @param activity    当前应用
     * @param statusColor 状态栏颜色
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void lollipopFull(Activity activity, int statusColor) {
        Window window = activity.getWindow();
        //设置透明状态栏,这样才能让 ContentView 向上
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(statusColor);

        ViewGroup mContentView = activity.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
//            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
////            ViewCompat.setFitsSystemWindows(mChildView, false);
            mChildView.setFitsSystemWindows(false);
        }
        //虚拟键盘也透明
        //window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    /**
     * 5.0以上沉浸式着色模式
     *
     * @param activity    当前应用
     * @param statusColor 状态栏颜色
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void lollipopColour(Activity activity, int statusColor) {
        Window window = activity.getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(statusColor);

        ViewGroup mContentView = activity.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
//            ViewCompat.setFitsSystemWindows(mChildView, true);
            mChildView.setFitsSystemWindows(true);
        }
        //虚拟键盘也透明
        //window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    /**
     * 4.4以上沉浸式全屏模式
     *
     * @param activity 当前应用
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void kitkatFull1(Activity activity) {
        Window window = activity.getWindow();
        ViewGroup mContentView = activity.findViewById(Window.ID_ANDROID_CONTENT);

        //首先使 ChildView 不预留空间
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
//            ViewCompat.setFitsSystemWindows(mChildView, false);
            mChildView.setFitsSystemWindows(false);
        }

        int statusBarHeight = getStatusBarHeight(activity);
        //需要设置这个 flag 才能设置状态栏
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //避免多次调用该方法时,多次移除了 View
        if (mChildView != null && mChildView.getLayoutParams() != null
                && mChildView.getLayoutParams().height == statusBarHeight) {
            //移除假的 View.
            mContentView.removeView(mChildView);
            mChildView = mContentView.getChildAt(0);
        }
        if (mChildView != null) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mChildView.getLayoutParams();
            //清除 ChildView 的 marginTop 属性
            if (lp != null && lp.topMargin >= statusBarHeight) {
                lp.topMargin -= statusBarHeight;
                mChildView.setLayoutParams(lp);
            }
        }
    }

    /**
     * 4.4以上沉浸式着色模式
     *
     * @param activity    当前应用
     * @param statusColor 状态栏颜色
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void kitkatColour1(Activity activity, int statusColor) {
        Window window = activity.getWindow();
        ViewGroup mContentView = activity.findViewById(Window.ID_ANDROID_CONTENT);

        //First translucent status bar.
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        int statusBarHeight = getStatusBarHeight(activity);

        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mChildView.getLayoutParams();
            //如果已经为 ChildView 设置过了 marginTop, 再次调用时直接跳过
            if (lp != null && lp.topMargin < statusBarHeight && lp.height != statusBarHeight) {
                //不预留系统空间
//                ViewCompat.setFitsSystemWindows(mChildView, false);
                mChildView.setFitsSystemWindows(false);
                lp.topMargin += statusBarHeight;
                mChildView.setLayoutParams(lp);
            }
        }

        View statusBarView = mContentView.getChildAt(0);
        if (statusBarView != null && statusBarView.getLayoutParams() != null
                && statusBarView.getLayoutParams().height == statusBarHeight) {
            //避免重复调用时多次添加 View
            statusBarView.setBackgroundColor(statusColor);
            return;
        }
        statusBarView = new View(activity);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
        statusBarView.setBackgroundColor(statusColor);
        //向 ContentView 中添加假 View
        mContentView.addView(statusBarView, 0, lp);
    }

    /**
     * 4.4以上沉浸式全屏模式
     *
     * @param activity 当前应用
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void kitkatFull2(Activity activity) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        ViewGroup mContentView = activity.findViewById(Window.ID_ANDROID_CONTENT);
        ViewGroup mContentParent = (ViewGroup) mContentView.getParent();

        View statusBarView = mContentParent.getChildAt(0);
        if (statusBarView != null && statusBarView.getLayoutParams() != null
                && statusBarView.getLayoutParams().height == getStatusBarHeight(activity)) {
            //移除假的 View
            mContentParent.removeView(statusBarView);
        }
        //ContentView 不预留空间
        if (mContentParent.getChildAt(0) != null) {
//            ViewCompat.setFitsSystemWindows(mContentParent.getChildAt(0), false);
            mContentParent.getChildAt(0).setFitsSystemWindows(false);
        }

        //ChildView 不预留空间
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
//            ViewCompat.setFitsSystemWindows(mChildView, false);
            mChildView.setFitsSystemWindows(false);
        }
    }

    /**
     * 4.4以上沉浸式着色模式
     *
     * @param activity    当前应用
     * @param statusColor 状态栏颜色
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void kitkatColour2(Activity activity, int statusColor) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        ViewGroup mContentView = activity.findViewById(Window.ID_ANDROID_CONTENT);
        ViewGroup mContentParent = (ViewGroup) mContentView.getParent();

        View statusBarView = mContentParent.getChildAt(0);
        if (statusBarView != null && statusBarView.getLayoutParams() != null
                && statusBarView.getLayoutParams().height == getStatusBarHeight(activity)) {
            //避免重复调用时多次添加 View
            statusBarView.setBackgroundColor(statusColor);
            return;
        }

        //创建一个假的 View, 并添加到 ContentParent
        statusBarView = new View(activity);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                getStatusBarHeight(activity));
        statusBarView.setBackgroundColor(statusColor);
        mContentParent.addView(statusBarView, 0, lp);

        //ChildView 不需要预留系统空间
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
//            ViewCompat.setFitsSystemWindows(mChildView, false);
            mChildView.setFitsSystemWindows(false);
        }
    }

    /**
     * 4.4以上沉浸式全屏模式
     *
     * @param activity 当前应用
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void kitkatFull3(Activity activity) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        ViewGroup mContentView = activity.findViewById(Window.ID_ANDROID_CONTENT);
        View statusBarView = mContentView.getChildAt(0);
        //移除假的 View
        if (statusBarView != null && statusBarView.getLayoutParams() != null
                && statusBarView.getLayoutParams().height == getStatusBarHeight(activity)) {
            mContentView.removeView(statusBarView);
        }
        //不预留空间
        if (mContentView.getChildAt(0) != null) {
//            ViewCompat.setFitsSystemWindows(mContentView.getChildAt(0), false);
            mContentView.getChildAt(0).setFitsSystemWindows(false);
        }
    }

    /**
     * 4.4以上沉浸式着色模式
     *
     * @param activity    当前应用
     * @param statusColor 状态栏颜色
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void kitkatColour3(Activity activity, int statusColor) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        ViewGroup mContentView = activity.findViewById(Window.ID_ANDROID_CONTENT);
        int statusBarHeight = getStatusBarHeight(activity);

        View mTopView = mContentView.getChildAt(0);
        if (mTopView != null && mTopView.getLayoutParams() != null
                && mTopView.getLayoutParams().height == statusBarHeight) {
            //避免重复添加 View
            mTopView.setBackgroundColor(statusColor);
            return;
        }
        //使 ChildView 预留空间
        if (mTopView != null) {
//            ViewCompat.setFitsSystemWindows(mTopView, true);
            mTopView.setFitsSystemWindows(true);
        }

        //添加假 View
        mTopView = new View(activity);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
        mTopView.setBackgroundColor(statusColor);
        mContentView.addView(mTopView, 0, lp);
    }

    /**
     * 获得状态栏的高度
     *
     * @param activity 当前应用
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Activity activity) {
        //获取status_bar_height资源的ID
        int resourceId = activity.getResources().getIdentifier("status_bar_height",
                "dimen", "android");
        if (resourceId > 0)
            //根据资源ID获取响应的尺寸值
            return activity.getResources().getDimensionPixelSize(resourceId);
        return 0;
    }
}