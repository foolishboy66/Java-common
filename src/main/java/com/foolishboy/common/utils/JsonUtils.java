package com.foolishboy.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * Json转换工具类
 *
 * @author wang
 */
public class JsonUtils {

    private JsonUtils() {

    }

    /**
     * 对象序列化为json字符串
     *
     * @param obj 要序列化的对象
     * @return String 序列化后的json字符串
     */
    public static String toStr(final Object obj) {

        return JSON.toJSONString(obj);
    }

    /**
     * json字符串反序列化为对象
     *
     * @param json  要反序列化的字符串
     * @param clazz 对象类型
     * @param <T>   返回对象类型
     * @return T 反序列化后的对象
     */
    public static <T> T toObj(final String json, final Class<T> clazz) {

        return JSON.parseObject(json, clazz);
    }

    /**
     * json字符串反序列化为复杂对象
     *
     * @param json 要反序列化的字符串
     * @param type 对象类型
     * @param <T>  返回对象类型
     * @return T 反序列化后的对象
     */
    public static <T> T toObj(final String json, final TypeReference<T> type) {

        return JSON.parseObject(json, type);
    }

}
