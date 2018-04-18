package solipsisming.util.collections;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * set集合工具</p>
 * 创建于 2015-07-11 10:01:50
 *
 * @author 洪东明
 * @version 1.0
 */
public class SetUtils {
    /**
     * 禁止创建对象
     */
    private SetUtils() {
        throw new UnacceptableInstanceError();
    }

    /**
     * 判断两个集合是否相等
     *
     * @param set1 集合1
     * @param set2 集合2
     * @return 是否相等
     */
    public static boolean isEqualSet(final Set<?> set1, final Set<?> set2) {
        if (set1 == set2) {
            return true;
        }
        if (set1 == null || set2 == null || set1.size() != set2.size()) {
            return false;
        }

        return set1.containsAll(set2);
    }

    /**
     * 将2个set集合合并在一起
     *
     * @param set1 集合1
     * @param set2 集合2
     * @param <O>  集合元素类型
     * @return 合并后的元素
     */
    public static <O> Set<? extends O> union(Set<? extends O> set1, Set<? extends O> set2) {
        Set<O> temp = new HashSet<O>(set1);
        temp.addAll(set2);
        return temp;
    }

    /**
     * 线程安全的集合
     *
     * @param set 集合
     * @param <E> 集合元素类型
     * @return 线程安全的集合
     */
    public static <E> Set<E> synchronizedSet(final Set<E> set) {
        return Collections.synchronizedSet(set);
    }

    /**
     * 不可修改的集合
     *
     * @param set 集合
     * @param <E> 集合元素类型
     * @return 不可修改的集合
     */
    public static <E> Set<E> unmodifiableSet(final Set<? extends E> set) {
        return Collections.unmodifiableSet(set);
    }
}