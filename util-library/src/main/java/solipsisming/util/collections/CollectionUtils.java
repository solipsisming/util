package solipsisming.util.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * Collection集合工具</p>
 * 创建于 2015-07-11 10:27:23
 *
 * @author 洪东明
 * @version 1.0
 */
public class CollectionUtils {
    /**
     * 禁止创建对象
     */
    private CollectionUtils() {
        throw new UnacceptableInstanceError();
    }

    /**
     * 并集
     *
     * @param c1  集合1
     * @param c2  集合2
     * @param <O> 集合类型
     * @return 并集后的集合
     */
    public static <O> Collection<O> union(final Collection<? extends O> c1, final Collection<? extends O> c2) {

        if (c1 != null && c2 == null)
            return (Collection<O>) c1;
        else if (c2 != null && c1 == null)
            return (Collection<O>) c2;
        else if (c1 == null && c2 == null)
            return null;
        else return ListUtils.union(new ArrayList<O>(c1), new ArrayList<O>(c2));
    }

    /**
     * 无重复交集
     *
     * @param c1  集合1
     * @param c2  集合2
     * @param <O> 集合类型
     * @return 无重复交集集合
     */
    public static <O> Collection<O> sum(final Collection<? extends O> c1, final Collection<? extends O> c2) {
        return ListUtils.sum(new ArrayList<O>(c1), new ArrayList<O>(c2));
    }

    /**
     * 差集
     *
     * @param c1  集合1
     * @param c2  集合2
     * @param <O> 集合类型
     * @return 差集后的集合
     */
    public static <O> Collection<O> different(final Collection<? extends O> c1, final Collection<? extends O> c2) {
        return ListUtils.different(new ArrayList<O>(c1), new ArrayList<O>(c2));
    }

    /**
     * 交集
     *
     * @param c1  集合1
     * @param c2  集合2
     * @param <O> 集合类型
     * @return 交集后的集合
     */
    public static <O> Collection<O> intersection(final Collection<? extends O> c1, final Collection<? extends O> c2) {
        return ListUtils.intersection(new ArrayList<O>(c1), new ArrayList<O>(c2));
    }

    /**
     * 集合1是否包含集合2
     *
     * @param coll1 集合1
     * @param coll2 集合2
     * @return 是否包含
     */
    public static boolean containsAll(final Collection<?> coll1, final Collection<?> coll2) {
        if (coll2.isEmpty()) {
            return true;
        } else {
            final Iterator<?> it = coll1.iterator();
            final Set<Object> elementsAlreadySeen = new HashSet<Object>();
            for (final Object nextElement : coll2) {
                if (elementsAlreadySeen.contains(nextElement)) {
                    continue;
                }

                boolean foundCurrentElement = false;
                while (it.hasNext()) {
                    final Object p = it.next();
                    elementsAlreadySeen.add(p);
                    if (nextElement == null ? p == null : nextElement.equals(p)) {
                        foundCurrentElement = true;
                        break;
                    }
                }
                if (foundCurrentElement) {
                    continue;
                } else {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * 集合1是否包含集合2其中一个元素
     *
     * @param coll1 集合1
     * @param coll2 集合2
     * @return 是否包含其中一个
     */
    public static boolean containsAny(final Collection<?> coll1, final Collection<?> coll2) {
        if (coll1.size() < coll2.size()) {
            for (final Object aColl1 : coll1) {
                if (coll2.contains(aColl1)) {
                    return true;
                }
            }
        } else {
            for (final Object aColl2 : coll2) {
                if (coll1.contains(aColl2)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断两个集合是否相等
     *
     * @param c1 集合1
     * @param c2 集合2
     * @return 是否相等
     */
    public static boolean isEqualCollection(final Collection<?> c1, final Collection<?> c2) {
        return ListUtils.isEqualList(new ArrayList<Object>(c1), new ArrayList<Object>(c2));
    }

    /**
     * 添加具有迭代能力的集合
     *
     * @param collection 集合
     * @param iterable   迭代器
     * @param <C>        集合元素类型
     * @return 是否添加成功
     */
    public static <C> boolean addAll(final Collection<C> collection, final Iterable<? extends C> iterable) {
        if (iterable instanceof Collection<?>) {
            return collection.addAll((Collection<? extends C>) iterable);
        }
        return addAll(collection, iterable.iterator());
    }

    /**
     * 添加具有迭代能力的集合
     *
     * @param collection 集合
     * @param iterator   迭代器
     * @param <C>        集合元素类型
     * @return 是否添加成功
     */
    public static <C> boolean addAll(final Collection<C> collection, final Iterator<? extends C> iterator) {
        boolean changed = false;
        while (iterator.hasNext()) {
            changed |= collection.add(iterator.next());
        }
        return changed;
    }

    /**
     * 添加枚举集合
     *
     * @param collection  集合
     * @param enumeration 枚举集合
     * @param <C>         集合元素类型
     * @return 是否添加成功
     */
    public static <C> boolean addAll(final Collection<C> collection, final Enumeration<? extends C> enumeration) {
        boolean changed = false;
        while (enumeration.hasMoreElements()) {
            changed |= collection.add(enumeration.nextElement());
        }
        return changed;
    }

    /**
     * 添加数组
     *
     * @param collection 集合
     * @param elements   数组
     * @param <C>        集合元素类型
     * @return 是否添加成功
     */
    public static <C> boolean addAll(final Collection<C> collection, final C[] elements) {
        boolean changed = false;
        for (final C element : elements) {
            changed |= collection.add(element);
        }
        return changed;
    }

    /**
     * 获取迭代集合的第i个元素
     *
     * @param iterator 迭代器
     * @param index    下标
     * @param <T>      集合元素
     * @return 元素
     */
    public static <T> T get(final Iterator<T> iterator, final int index) {
        int i = index;
        checkIndexBounds(i);
        while (iterator.hasNext()) {
            i--;
            if (i == -1) {
                return iterator.next();
            }
            iterator.next();
        }
        throw new IndexOutOfBoundsException("Entry does not exist: " + i);
    }

    /**
     * 获取迭代集合的第i个元素
     *
     * @param iterable 迭代器
     * @param index    下标
     * @param <T>      集合元素
     * @return 元素
     */
    public static <T> T get(final Iterable<T> iterable, final int index) {
        checkIndexBounds(index);
        if (iterable instanceof List<?>) {
            return ((List<T>) iterable).get(index);
        }
        return get(iterable.iterator(), index);
    }


    /**
     * 获取map集合的第i个entry
     *
     * @param map   map集合
     * @param index 下标
     * @param <K>   键
     * @param <V>   值
     * @return entry
     */
    public static <K, V> Map.Entry<K, V> get(final Map<K, V> map, final int index) {
        checkIndexBounds(index);
        return get(map.entrySet(), index);
    }

    /**
     * 判断是否超出集合范围
     *
     * @param index 下标
     */
    private static void checkIndexBounds(final int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index cannot be negative: " + index);
        }
    }

    /**
     * 反转数组
     *
     * @param array 数组
     */
    public static void reverseArray(final Object[] array) {
        int i = 0;
        int j = array.length - 1;
        Object tmp;

        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    /**
     * 线程安全的集合
     *
     * @param collection 集合
     * @param <C>        集合类型
     * @return 线程安全的集合
     */
    public static <C> Collection<C> synchronizedCollection(final Collection<C> collection) {
        return Collections.synchronizedCollection(collection);
    }

    /**
     * 不可修改的集合
     *
     * @param collection 集合
     * @param <C>        集合类型
     * @return 不可修改的集合
     */
    public static <C> Collection<C> unmodifiableCollection(final Collection<? extends C> collection) {
        return Collections.unmodifiableCollection(collection);
    }
}