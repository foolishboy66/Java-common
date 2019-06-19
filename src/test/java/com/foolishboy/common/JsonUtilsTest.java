package com.foolishboy.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.TypeReference;
import com.foolishboy.common.utils.BeanUtils;
import com.foolishboy.common.utils.JsonUtils;
import com.foolishboy.domain.Action;
import com.foolishboy.domain.School;
import com.foolishboy.domain.User;

public class JsonUtilsTest {

	public static void main(String[] args) {

		User user = new User();
		user.setName("王");
		user.setBirthDay(new Date());
		List<String> nickNames = new ArrayList<>();
		nickNames.add("ccc");
		user.setNickNames(nickNames);
		Map<String, School> map = new HashMap<>();
		School s1 = new School("a", "123", true);
		s1.setUser(user);
		map.put("no1", s1);
		map.put("no2", new School("b", "456", false));
		user.setSchoolMap(map);

		System.out.println(JsonUtils.toStr(user));
		
		User user3 = new User();
		BeanUtils.copyProperties(user, user3);
		user.getSchoolMap().get("no1").setSchoolName("newbe");
		System.out.println(JsonUtils.toStr(user3));
		
		Action action = new Action();
		action.setName("action1");
		action.setUser(user);
		user.setAction(action);
		System.out.println(JsonUtils.toStr(user));
		Map<String, User> m = new HashMap<>();
		m.put(user.getName(), user);
		System.out.println("m--"+ JsonUtils.toStr(m));
		

		String json = "{\"王\":{\"action\":{\"name\":\"action1\"},\"age\":0,\"birthDay\":1554306213736,\"name\":\"王\",\"nickNames\":[\"ccc\"],\"schoolMap\":{\"no2\":{\"excellent\":false,\"location\":\"456\",\"schoolName\":\"b\"},\"no1\":{\"excellent\":true,\"location\":\"123\",\"schoolName\":\"newbe\",\"user\":{\"$ref\":\"$.王\"}}},\"sex\":false}}";
		Map<String, User> mm = JsonUtils.toObj(json, new TypeReference<Map<String, User>>() {});
		
		System.out.println("user:"+mm.get("王").getAction().getUser().getName());
		
		User user2 = JsonUtils.toObj(json, User.class);
		System.out.println(user2);
		System.out.println(JsonUtils.toStr(user2));

	}
}
