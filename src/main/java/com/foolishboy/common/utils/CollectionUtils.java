package com.foolishboy.common.utils;

import java.util.*;

/**
 * 集合工具类
 * 
 * @author wang
 *
 */
public class CollectionUtils {

    private CollectionUtils() {

    }

    private static <T> Map<?, Integer> getFrequencyMap(Collection<? extends T> colle) {

        boolean empty = isEmpty(colle);
        int size = empty ? 2 : colle.size();
        Map<T, Integer> map = new HashMap<>(size);
        if (empty) {
            return map;
        }
        for (T obj : colle) {
            Integer freq = map.get(obj);
            if (freq == null) {
                map.put(obj, 1);
            } else {
                map.put(obj, freq++);
            }
        }

        return map;
    }

    private static <T> int getMinFreq(Map<?, Integer> map1, Map<?, Integer> map2, T obj) {

        Integer freq1 = map1.get(obj);
        Integer freq2 = map2.get(obj);
        if (freq1 == null || freq2 == null) {
            return 0;
        }

        return Math.min(freq1, freq2);
    }

    private static <T> int getMaxFreq(Map<?, Integer> map1, Map<?, Integer> map2, T obj) {

        Integer freq1 = map1.get(obj);
        Integer freq2 = map2.get(obj);
        if (freq1 == null) {
            freq1 = 0;
        }
        if (freq2 == null) {
            freq2 = 0;
        }

        return Math.max(freq1, freq2);
    }

    private static <T> void setForNewColl(List<T> newColle, T obj, int freq) {

        for (int i = 0; i < freq; i++) {
            newColle.add(obj);
        }
    }

    /**
     * 判断集合是否为空
     * 
     * @param collection 待判空的集合
     * @return true:集合为空，false:集合非空
     * @param <T> 元素类型
     */
    public static <T> boolean isEmpty(final Collection<T> collection) {

        return collection == null || collection.isEmpty();
    }

    /**
     * 判断集合是否不为空
     * 
     * @param collection 待判空的集合
     * @return true:集合非空，false:集合为空
     * @param <T> 元素类型
     */
    public static <T> boolean isNotEmpty(final Collection<T> collection) {

        return !isEmpty(collection);
    }

    /**
     * 判断多个集合中是否有空集合
     * 
     * @param colles 待判空的集合数组
     * @return true:有空集合，false:无空集合
     * @param <T> 元素类型
     */
    @SuppressWarnings("unchecked")
    public static <T> boolean isAnyEmpty(final Collection<T>... colles) {

        if (colles == null || colles.length == 0) {
            return true;
        }
        for (Collection<?> collection : colles) {
            if (isEmpty(collection)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断多个集合中是否全部为空
     * 
     * @param colles 待判空的集合数组
     * @return true:全部为空，false:有非空集合
     * @param <T> 元素类型
     */
    @SuppressWarnings("unchecked")
    public static <T> boolean isAllEmpty(final Collection<T>... colles) {

        if (colles == null || colles.length == 0) {
            return true;
        }
        for (Collection<?> collection : colles) {
            if (isNotEmpty(collection)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断多个集合中是否全部不为空
     * 
     * @param colles 待判空的集合数组
     * @param <T> 元素类型
     * @return true:全部非空，false:有空集合
     */
    public static <T> boolean isNoneEmpty(final Collection<?>... colles) {

        return !isAnyEmpty(colles);
    }

    /**
     * 判断集合中是否存在某元素
     * 
     * @param collection 集合
     * @param obj 元素
     * @param <T> 元素类型
     * @return true:集合中包含该元素,false:集合为空或集合中不包含该元素
     */
    public static <T> boolean contains(final Collection<T> collection, final T obj) {

        return collection != null && collection.contains(obj);
    }

    /**
     * 
     * 判断某集合元素中是否全部存在于另一个集合中
     * 
     * @param collection 集合1
     * @param collection2 集合2
     * @param <T> 元素类型
     * @return true:集合1包含集合2中的所有元素，false:集合1为空或集合1不包含集合2中的所有元素
     */
    public static <T> boolean contains(final Collection<T> collection, final Collection<T> collection2) {

        return collection != null && collection.containsAll(collection2);
    }

    /**
     * 取两个集合的交集
     * 
     * @param colle1 集合1
     * @param colle2 集合2
     * @param <T> 元素类型
     * @return Collection 拥有集合1和集合2中都包含的元素的集合
     */
    @SuppressWarnings("unchecked")
    public static <T> Collection<T> intersect(final Collection<T> colle1, final Collection<T> colle2) {

        List<T> newColle = new ArrayList<>();
        if (isAnyEmpty(colle1, colle2)) {
            return newColle;
        }

        Set<T> set = new HashSet<>(colle1);
        set.addAll(colle2);

        for (T obj : set) {
            setForNewColl(newColle, obj, getMinFreq(getFrequencyMap(colle1), getFrequencyMap(colle2), obj));
        }

        return newColle;
    }

    /**
     * 取两个集合的并集
     * 
     * @param colle1 集合1
     * @param colle2 集合2
     * @param <T> 元素类型
     * @return Collection 拥有集合1和集合2中所有元素的集合
     */
    @SuppressWarnings("unchecked")
    public static <T> Collection<T> union(final Collection<T> colle1, final Collection<T> colle2) {

        if (isAnyEmpty(colle1, colle2)) {
            return new ArrayList<>();
        }

        Set<T> set = new HashSet<>(colle2);
        set.addAll(colle1);

        List<T> newColle = new ArrayList<>();
        for (T obj : set) {
            setForNewColl(newColle, obj, getMaxFreq(getFrequencyMap(colle1), getFrequencyMap(colle2), obj));
        }

        return newColle;
    }

    /**
     * 取两个集合的差集
     *
     * @param colle1 集合1
     * @param colle2 集合2
     * @param <T> 元素类型
     * @return Collection 拥有集合1有但集合2没有以及集合2有但集合1没有的元素的集合
     */
    @SuppressWarnings("unchecked")
    public static <T> Collection<T> diff(final Collection<T> colle1, final Collection<T> colle2) {

        List<T> newColle = new ArrayList<>();
        if (isAnyEmpty(colle1, colle2)) {
            return newColle;
        }

        Set<T> set = new HashSet<>();
        set.addAll(colle1);
        set.addAll(colle2);

        Map<?, Integer> frequencyMap1 = getFrequencyMap(colle1);
        Map<?, Integer> frequencyMap2 = getFrequencyMap(colle2);

        for (T obj : set) {
            setForNewColl(newColle, obj,
                getMaxFreq(frequencyMap1, frequencyMap2, obj) - getMinFreq(frequencyMap1, frequencyMap2, obj));
        }

        return newColle;
    }

    /**
     * 过滤集合中的指定元素
     * 
     * @param collection 集合
     * @param objs 元素数组
     * @param <T> 元素类型
     * @return 过滤了集合中的指定元素的集合
     */
    @SuppressWarnings("unchecked")
    public static <T> Collection<T> filter(final Collection<T> collection, final T... objs) {

        if (isNotEmpty(collection) && objs != null) {
            List<T> elements = Arrays.asList(objs);
            collection.removeIf(elements::contains);
        }

        return collection;
    }

    /**
     * 将集合切割为指定size大小的多个组
     * 
     * @param collection 集合
     * @param size 每个list指定的大小
     * @param <T> 元素类型
     * @return  List<Collection<T>> 切割好的指定size的集合组成的list
     */
    public static <T> List<Collection<T>> split(final Collection<T> collection, final int size) {

        if (size <= 0) {
            throw new IllegalArgumentException("The size must be larger than 0");
        }

        List<Collection<T>> list = new ArrayList<>();
        if (isEmpty(collection)) {
            return list;
        }

        Iterator<T> iterator = collection.iterator();
        int count = 0;
        List<T> temp = new ArrayList<>();
        while (iterator.hasNext()) {
            count++;
            temp.add(iterator.next());
            if (count % size == 0) {
                list.add(temp);
                temp = new ArrayList<>();
            }
        }
        list.add(temp);

        return list;
    }

}
