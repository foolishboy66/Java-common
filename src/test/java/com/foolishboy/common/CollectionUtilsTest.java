package com.foolishboy.common;

import java.util.ArrayList;
import java.util.List;

import com.foolishboy.common.utils.CollectionUtils;

public class CollectionUtilsTest {

	@SuppressWarnings("unchecked")
    public static void main(String[] args) {

		List<String> a1 = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			a1.add(i + 1 + "");
		}
		
		List<String> a2 = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			a2.add(i + 6 + "");
		}
		
		System.out.println(CollectionUtils.split(a1, 4));
		System.out.println(CollectionUtils.split(a1, 0));
		System.out.println(CollectionUtils.isEmpty(a1));
		System.out.println(CollectionUtils.isEmpty(a2));
		System.out.println(CollectionUtils.isNoneEmpty(a1, a2));
		System.out.println(a1);
		System.out.println(a2);
		System.out.println(CollectionUtils.union(a1, a2));
		System.out.println(CollectionUtils.diff(a1, a2));
		System.out.println(CollectionUtils.intersect(a1, a2));
		System.out.println(CollectionUtils.filter(a1, "2"));

	}
}
