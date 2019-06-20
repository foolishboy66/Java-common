package com.foolishboy.common.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * JavaBean操作工具类
 *
 * @author wang
 */
public class BeanUtils {

    private BeanUtils() {

    }

    /**
     * 将source对象中的相同属性名的值复制到target对象中
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyProperties(final Object source, final Object target) {

        copyProperties(source, target, (String[])null);
    }

    /**
     * 将source对象中的相同属性名的值复制到target对象中,可以忽略一部分属性
     *
     * @param source 源对象
     * @param target 目标对象
     * @param ignoreProperties 忽略的属性数组
     */
    public static void copyProperties(final Object source, final Object target, final String... ignoreProperties) {

        org.springframework.beans.BeanUtils.copyProperties(source, target, ignoreProperties);
    }

    /**
     * map转换为对象
     *
     * @param map 待转换的map
     * @param obj 要转换成为的目标对象
     * @param <T> 元素类型
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
     * @param obj 待转换的对象
     * @param map 要转换成的目标map
     * @param <T> 元素类型
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
