package com.foolishboy.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * Json转换工具类
 * 
 * @author wang
 *
 */
public class JsonUtil {

	private JsonUtil() {
	}

	/**
	 * 对象序列化为json字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String toStr(Object obj) {

		return JSON.toJSONString(obj);
	}

	/**
	 * json字符串反序列化为对象
	 * 
	 * @param json
	 * @return
	 */
	public static <T> T toObj(String json, Class<T> clazz) {

		return JSON.parseObject(json, clazz);
	}

	/**
	 * json字符串反序列化为复杂对象
	 * 
	 * @param json
	 * @param type
	 * @return
	 */
	public static <T> T toObj(String json, TypeReference<T> type) {

		return JSON.parseObject(json, type);
	}

}
