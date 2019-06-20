package com.foolishboy.common.utils;

import java.util.UUID;

/**
 * Uuid工具类
 * 
 * @author wang
 *
 */
public class UuidUtils {

    private UuidUtils() {

    }

    /**
     * 获取uuid
     * 
     * @return String 生成的32为的uuid字符串
     */
    public static String getUuid() {

        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
