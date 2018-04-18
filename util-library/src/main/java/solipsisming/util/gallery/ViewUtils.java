package solipsisming.util.gallery;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import solipsisming.util.exception.UnacceptableInstanceError;
import solipsisming.util.system.PrintLog;

/**
 * 计算view的大小</p>
 * 创建于 2015-6-30 20:56:28
 *
 * @author 洪东明
 * @version 1.0
 */
public class ViewUtils {

    private static final String CLASS_NAME_GRID_VIEW = "android.widget.GridView";
    private static final String FIELD_NAME_VERTICAL_SPACING = "mVerticalSpacing";

    private ViewUtils() {
        throw new UnacceptableInstanceError();
    }

    /**
     * 计算listview的高度
     *
     * @param view 视图
     * @return 控件高度
     */
    public static int getListViewHeight(ListView view) {
        if (view == null) {
            PrintLog.logW("args view is null");
            return -1;
        }
        int height = getAbsListViewHeight(view);
        ListAdapter adapter = view.getAdapter();
        int adapterCount = adapter.getCount();
        if (adapterCount > 0) {//分割线
            height += view.getDividerHeight() * (adapterCount - 1);
        }
        return height;
    }

    /**
     * 计算gridview的高度
     *
     * @param view 视图
     * @return 控件高度
     * @throws IllegalAccessException 不可访问异常
     * @throws NoSuchFieldException   没有该方法异常
     * @throws ClassNotFoundException 找不到类异常
     */
    public static int getGridViewHeight(GridView view) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
        if (view == null || view.getChildCount() <= 0) {
            PrintLog.logW("args view is null or view.getChildCount() <= 0");
            return -1;
        }
        int height = getAbsListViewHeight(view);
        ListAdapter adapter = view.getAdapter();
        int adapterCount = adapter.getCount();
        int numColumns = getGridViewNumColumns(view);
        if (adapterCount > 0 && numColumns > 0) {
            int rowCount = (int) Math.ceil(adapterCount / (double) numColumns);
            height = rowCount * (height / adapterCount + getGridViewVerticalSpacing(view));
        }
        return height;
    }

    /**
     * 获取gridview的列数
     *
     * @param view 视图
     * @return 控件列数
     */
    public static int getGridViewNumColumns(GridView view) {
        if (view == null) {
            PrintLog.logW("args view is null");
            return -1;
        }
        if (Build.VERSION.SDK_INT >= 11) {
            return getNumColumnsCompat11(view);
        } else {
            int columns = 0;
            int children = view.getChildCount();
            if (children > 0) {
                int width = view.getChildAt(0).getMeasuredWidth();
                if (width > 0) {//可能控件被gone了
                    columns = view.getWidth() / width;
                }
            }
            return columns;
        }
    }

    @TargetApi(11)
    private static int getNumColumnsCompat11(GridView view) {
        return view.getNumColumns();
    }

    /**
     * 获取gridview的间距
     *
     * @param view 视图
     * @return 间距
     * @throws IllegalAccessException 不可访问异常
     * @throws NoSuchFieldException   没有该方法异常
     * @throws ClassNotFoundException 找不到类异常
     */
    public static int getGridViewVerticalSpacing(GridView view) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        if (view == null) {
            PrintLog.logE("args view is null");
            return -1;
        }
        Class<?> demo = Class.forName(CLASS_NAME_GRID_VIEW);
        Field field = demo.getDeclaredField(FIELD_NAME_VERTICAL_SPACING);
        field.setAccessible(false);
        return (int) field.get(view);
    }

    /**
     * 计算view的高度
     *
     * @param view view
     * @return 控件高度
     */
    public static int getAbsListViewHeight(AbsListView view) {
        ListAdapter adapter = view.getAdapter();
        int height = 0;
        int count = adapter.getCount();
        for (int i = 0; i < count; i++) {//遍历item
            View item = adapter.getView(i, null, view);
            if (item instanceof ViewGroup) {
                item.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            }
            item.measure(0, 0);
            height += item.getMeasuredHeight();
        }
        height += view.getPaddingTop() + view.getPaddingBottom();
        return height;
    }

    /**
     * 设置view的高度
     *
     * @param view   视图
     * @param height 控件高度
     */
    public static void setViewHeight(View view, int height) {
        if (height < 0) {
            PrintLog.logW("args height is invalid must be >=0");
            return;
        }
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
    }

    /**
     * 设置gridview的高度
     *
     * @param view 视图
     * @throws IllegalAccessException 不可访问异常
     * @throws NoSuchFieldException   没有该方法异常
     * @throws ClassNotFoundException 找不到类异常
     */
    public static void setGridViewHeight(GridView view) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
        setViewHeight(view, getGridViewHeight(view));
    }

    /**
     * 设置ListView的高度
     *
     * @param view 视图
     */
    public static void setListViewHeight(ListView view) {
        setViewHeight(view, getListViewHeight(view));
    }

    /**
     * 设置AbsListView的高度
     *
     * @param view 视图
     */
    public static void setAbsListViewHeight(AbsListView view) {
        setViewHeight(view, getAbsListViewHeight(view));
    }

    /**
     * 设置view的监听器
     *
     * @param v        视图
     * @param listener 监听器
     */
    public static void setViewClickListener(View v, OnClickListener listener) {
        if (v == null || listener == null) {
            PrintLog.logE("args view is null or listener is null");
            return;
        }
        if (v instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) v;
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = group.getChildAt(i);
                if (child instanceof LinearLayout || child instanceof RelativeLayout) {
                    setViewClickListener(child, listener);
                }
                if (child instanceof TextView) {
                    TextView text = (TextView) child;
                    text.setFocusable(false);
                }
                child.setOnClickListener(listener);
            }
        }
    }

    /**
     * 获取view的所有指定子控件
     *
     * @param parent          视图
     * @param filter          指定的控件
     * @param includeSubClass 是否包含子类
     * @param <T>             视图
     * @return 视图集合
     */
    public static <T extends View> List<T> getDescendants(ViewGroup parent, Class<T> filter, boolean includeSubClass) {
        int childCount = parent.getChildCount();
        ArrayList<T> list = new ArrayList<T>();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            Class<? extends View> childClass = child.getClass();
            if ((includeSubClass && filter.isAssignableFrom(childClass)) || (!includeSubClass && childClass == filter)) {
                list.add(filter.cast(child));
            }
            if (child instanceof ViewGroup) {
                list.addAll(getDescendants((ViewGroup) child, filter, includeSubClass));
            }
        }
        return list;
    }
}