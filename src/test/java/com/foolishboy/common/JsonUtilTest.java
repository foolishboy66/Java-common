package com.foolishboy.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.foolishboy.common.utils.BeanUtil;
import com.foolishboy.common.utils.JsonUtil;
import com.foolishboy.domain.School;
import com.foolishboy.domain.User;

public class JsonUtilTest {

	public static void main(String[] args) {

		User user = new User();
		user.setName("王");
		user.setBirthDay(new Date());
		List<String> nickNames = new ArrayList<>();
		nickNames.add("ccc");
		user.setNickNames(nickNames);
		Map<String, School> map = new HashMap<>();
		map.put("no1", new School("a", "123", true));
		map.put("no2", new School("b", "456", false));
		user.setSchoolMap(map);

		System.out.println(JsonUtil.toStr(user));
		
		User user3 = new User();
		BeanUtil.copyProperties(user, user3);
		user.getSchoolMap().get("no1").setSchoolName("newbe");
		System.out.println(JsonUtil.toStr(user3));
		System.out.println(JsonUtil.toStr(user));
		
		

		String json = "{\"age\":0,\"birthDay\":1545475380425,\"name\":\"王\",\"nickNames\":[\"ccc\"],\"schoolMap\":{\"no2\":{\"excellent\":false,\"location\":\"456\",\"schoolName\":\"b\"},\"no1\":{\"excellent\":true,\"location\":\"123\",\"schoolName\":\"a\"}},\"sex\":false,\"unkosd\":\"kk\",\"test\":false,\"tt\":[\"asdf\"]}";
		User user2 = JsonUtil.toObj(json, User.class);
		System.out.println(user2);
		System.out.println(JsonUtil.toStr(user2));

	}
}
