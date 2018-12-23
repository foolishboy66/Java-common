package com.foolishboy.common;

import java.util.HashMap;
import java.util.Map;

import com.foolishboy.common.utils.BeanUtil;
import com.foolishboy.domain.School;

public class BeanUtilTest {

	public static void main(String[] args) {

		School school = new School("No1", "beijing", true);
		School school2 = new School();
		BeanUtil.copyProperties(school, school2);
		System.out.println(school2);
		
		Map<String, Object> map = new HashMap<>();
		BeanUtil.objToMap(school, map);
		System.out.println(map);
		
		School school3 = new School();
		BeanUtil.mapToObj(map, school3);
		System.out.println(school3);
		
		School school4 = new School();
		BeanUtil.copyProperties(school3, school4, "schoolName", "excellent");
		System.out.println(school4);
	}
}
