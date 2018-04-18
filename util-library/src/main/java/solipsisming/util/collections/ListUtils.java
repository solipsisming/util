package solipsisming.util.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * list集合工具</p>
 * 创建于 2015-07-11 09:45:11
 *
 * @author 洪东明
 * @version 1.0
 */
public class ListUtils {
    /**
     * 禁止创建对象
     */
    private ListUtils() {
        throw new UnacceptableInstanceError();
    }

    /**
     * 并集
     *
     * @param list1 集合1
     * @param list2 集合2
     * @param <E>   集合类型
     * @return 并集后的集合
     */
    public static <E> List<E> union(final List<? extends E> list1, final List<? extends E> list2) {
        ArrayList<E> temp = new ArrayList<E>(list1);
        temp.addAll(list2);
        return temp;
    }

    /**
     * 交集
     *
     * @param list1 集合1
     * @param list2 集合2
     * @param <E>   集合类型
     * @return 交集后的集合
     */
    public static <E> List<E> intersection(final List<? extends E> list1, final List<? extends E> list2) {
        ArrayList<E> temp = new ArrayList<E>(list1);
        temp.retainAll(list2);
        return temp;
    }

    /**
     * 差集
     *
     * @param list1 集合1
     * @param list2 集合2
     * @param <E>   集合类型
     * @return 差集后的集合
     */
    public static <E> List<E> different(final List<? extends E> list1, final List<? extends E> list2) {
        ArrayList<E> temp = new ArrayList<E>(list1);
        temp.removeAll(list2);
        return temp;
    }

    /**
     * 无重复交集
     *
     * @param list1 集合1
     * @param list2 集合2
     * @param <E>   集合类型
     * @return 无重复交集集合
     */
    public static <E> List<E> sum(final List<? extends E> list1, final List<? extends E> list2) {
        ArrayList<E> temp1 = new ArrayList<E>(list1);
        ArrayList<E> temp2 = new ArrayList<E>(list2);
        temp2.retainAll(temp1);
        temp1.addAll(temp2);
        return temp1;
    }

    /**
     * 截取一部分
     *
     * @param list  集合
     * @param start 开始位置
     * @param end   结束为止
     * @param <E>   集合类型
     * @return 截取后的集合
     */
    public static <E> List<E> sbuList(final List<E> list, int start, int end) {
        return new ArrayList<E>(list.subList(start, end));
    }

    /**
     * 两个集合是否相等
     *
     * @param list1 集合1
     * @param list2 集合2
     * @return 相等或不相等
     */
    public static boolean isEqualList(final List<?> list1, final List<?> list2) {
        if (list1 == list2)
            return true;

        if (list1 == null || list2 == null)
            return false;

        int size1 = list1.size();

        if (size1 != list2.size()) {
            return false;
        }

        for (int i = 0; i < size1; i++) {
            Object obj1 = list1.get(i);
            Object obj2 = list2.get(i);
            if (!(obj1 == null ? obj2 == null : obj1.equals(obj2))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 线程安全的集合
     *
     * @param list 集合
     * @param <E>  集合类型
     * @return 线程安全的集合
     */
    public static <E> List<E> synchronizedList(final List<E> list) {
        return Collections.synchronizedList(list);
    }

    /**
     * 不可修改的集合
     *
     * @param list 集合
     * @param <E>  集合类型
     * @return 不可修改的集合
     */
    public static <E> List<E> unmodifiableList(final List<? extends E> list) {
        return Collections.unmodifiableList(list);
    }

    /**
     * 获取指定元素的下标
     *
     * @param list 集合
     * @param <E>  集合类型
     * @return 找到则返回下标，没找到则返回-1
     */
    public static <E> int indexOf(final List<E> list, E e) {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                final E item = list.get(i);
                if (e.equals(item))
                    return i;
            }
        }
        return -1;
    }
}