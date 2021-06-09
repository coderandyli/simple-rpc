package com.coderandyli.common.utils;

import com.google.gson.Gson;

/**
 * @Date 2021/6/9 7:05 下午
 * @Created by lizhenzhen
 */
public class JsonUtil {
    private static final Gson GSON = new Gson();

    private JsonUtil() {
        //no instance
    }

    public static <T> T fromJson(String jsonStr, Class<T> clazz) {
        return GSON.fromJson(jsonStr, clazz);
    }

    public static String toJson(Object object) {
        return GSON.toJson(object);
    }
}
