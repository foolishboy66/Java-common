package com.foolishboy.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 集合工具类
 * 
 * @author wang
 *
 */
public class CollectionUtil {

	private CollectionUtil() {
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
	 * @param collection
	 * @return
	 */
	public static <T> boolean isEmpty(final Collection<?> collection) {

		return collection == null || collection.isEmpty();
	}

	/**
	 * 判断集合是否不为空
	 * 
	 * @param collection
	 * @return
	 */
	public static <T> boolean isNotEmpty(final Collection<?> collection) {

		return !isEmpty(collection);
	}

	/**
	 * 判断多个集合中是否有空集合
	 * 
	 * @param colles
	 * @return
	 */
	public static <T> boolean isAnyEmpty(final Collection<?>... colles) {

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
	 * @param colles
	 * @return
	 */
	public static <T> boolean isAllEmpty(final Collection<?>... colles) {

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
	 * @param colles
	 * @return
	 */
	public static <T> boolean isNoneEmpty(final Collection<?>... colles) {

		return !isAnyEmpty(colles);
	}

	/**
	 * 判断集合中是否存在某元素
	 * 
	 * @param collection
	 * @param obj
	 * @return
	 */
	public static <T> boolean contains(final Collection<?> collection, final T obj) {

		return collection.contains(obj);
	}

	/**
	 * 
	 * 判断某集合元素中是否全部存在于另一个集合中
	 * 
	 * @param collection
	 * @param collection2
	 * @return
	 */
	public static <T> boolean contains(final Collection<?> collection, final Collection<?> collection2) {

		return collection.containsAll(collection2);
	}

	/**
	 * 取两个集合的交集
	 * 
	 * @param colle1
	 * @param colle2
	 * @return
	 */
	public static <T> Collection<?> intersect(final Collection<? extends T> colle1,
			final Collection<? extends T> colle2) {

		List<T> newColle = new ArrayList<>();
		if (isAnyEmpty(colle1, colle2)) {
			return newColle;
		}

		Set<T> set = new HashSet<>();
		set.addAll(colle1);
		set.addAll(colle2);

		for (T obj : set) {
			setForNewColl(newColle, obj, getMinFreq(getFrequencyMap(colle1), getFrequencyMap(colle2), obj));
		}

		return newColle;
	}

	/**
	 * 取两个集合的并集
	 * 
	 * @param colle1
	 * @param colle2
	 * @return
	 */
	public static <T> Collection<?> union(final Collection<? extends T> colle1, final Collection<? extends T> colle2) {

		List<T> newColle = new ArrayList<>();
		if (isAnyEmpty(colle1, colle2)) {
			return newColle;
		}

		Set<T> set = new HashSet<>();
		set.addAll(colle1);
		set.addAll(colle2);

		for (T obj : set) {
			setForNewColl(newColle, obj, getMaxFreq(getFrequencyMap(colle1), getFrequencyMap(colle2), obj));
		}

		return newColle;
	}

	/**
	 * 取两个集合的差集
	 * 
	 * @param colle1
	 * @param colle2
	 * @return
	 */
	public static <T> Collection<?> diff(final Collection<? extends T> colle1, final Collection<? extends T> colle2) {

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
	 * @param collection
	 * @param obj
	 * @return
	 */
	public static <T> Collection<?> filter(final Collection<?> collection, final T obj) {

		if (!isEmpty(collection) && obj != null) {
			Iterator<?> iterator = collection.iterator();
			while (iterator.hasNext()) {
				if (obj.equals(iterator.next())) {
					iterator.remove();
				}
			}
		}

		return collection;

	}

	/**
	 * 将集合切割为指定size大小的多个组
	 * 
	 * @param collection
	 * @param size
	 * @return
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
