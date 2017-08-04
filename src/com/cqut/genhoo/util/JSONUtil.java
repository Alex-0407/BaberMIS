package com.cqut.genhoo.util;

import com.alibaba.fastjson.JSON;

public class JSONUtil {
    // 将 Java 对象转为 JSON 字符串
    public static <T> String toJSON(T obj) {
        String jsonStr = "";
        try {
            jsonStr = JSON.toJSONString(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonStr;
    }

    // 将 JSON 字符串转为 Java 对象
    public static <T> T fromJSON(String json, Class<T> type) {
        T obj = null;
        try {
            obj = JSON.parseObject(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
