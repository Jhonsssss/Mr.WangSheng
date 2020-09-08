package com.statistical.time.tool;

import com.google.gson.Gson;

/**
 * 类描述：解析JSON
 * 创建人：Sven
 */
public class GsonUtil {
    public static <T> T JsonToEntity(String result, Class<T> clazz){
        Gson gson = new Gson();
        return gson.fromJson(result, clazz);
    }

    public static String beanToString(Object bean){
        Gson gson = new Gson();
        return gson.toJson(bean);
    }
}