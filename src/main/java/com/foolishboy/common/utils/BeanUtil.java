package com.foolishboy.common.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.beans.BeanUtils;

/**
 * JavaBean操作工具类
 * 
 * @author wang
 *
 */
public class BeanUtil {

	private BeanUtil() {

	}

	/**
	 * 将source对象中的相同属性名的值复制到target对象中
	 * 
	 * @param source
	 * @param target
	 */
	public static void copyProperties(final Object source, final Object target) {

		copyProperties(source, target, (String[]) null);
	}

	/**
	 * 将source对象中的相同属性名的值复制到target对象中,可以忽略一部分属性
	 * 
	 * @param source
	 * @param target
	 * @param ignoreProperties
	 */
	public static void copyProperties(final Object source, final Object target, final String... ignoreProperties) {

		BeanUtils.copyProperties(source, target, ignoreProperties);
	}

	/**
	 * map转换为对象
	 * 
	 * @param map
	 * @param obj
	 */
	public static <T> void mapToObj(final Map<String, Object> map, final T obj) {

		if (map == null || obj == null) {
			return;
		}

		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor propertyDescriptor : descriptors) {
				String name = propertyDescriptor.getName();
				if (map.containsKey(name)) {
					Object val = map.get(name);
					Method setter = propertyDescriptor.getWriteMethod();
					if (setter != null) {
						setter.invoke(obj, val);
					}
				}
			}
		} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new RuntimeException("map不能转换为对象,caused by获取对象信息失败!");
		}
	}

	/**
	 * 对象转换为map
	 * 
	 * @param obj
	 * @param map
	 */
	public static <T> void objToMap(final T obj, final Map<String, Object> map) {

		if (obj == null || map == null) {
			return;
		}

		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor propertyDescriptor : descriptors) {
				String name = propertyDescriptor.getName();
				if ("class".equalsIgnoreCase(name)) {
					continue;
				}
				Method getter = propertyDescriptor.getReadMethod();
				Object val = null;
				if (getter != null) {
					val = getter.invoke(obj);
				}
				map.put(name, val);
			}
		} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new RuntimeException("对象不能转换为map,caused by获取对象信息失败!");
		}

	}
}
