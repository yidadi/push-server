package com.even.push.utils;

import com.alibaba.fastjson.JSON;

/**
 * @Auther yidadi
 * @Date 17-9-4 下午4:07
 */
public class JsonUtils {

    /**
     *
     * @param data
     * @param type
     * @return
     */
    public static Object readFromJson(String data,Class type) {
        return JSON.parseObject(data,type);
    }

    /**
     *
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        return JSON.toJSONString(obj);
    }
}
