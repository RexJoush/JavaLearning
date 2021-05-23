package com.joush.util;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joush.entities.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rex Joush
 * @time 2021.05.21 15:14
 */

public class Result {

    public static String ok(String message){
        Map<String, Object> result = new HashMap<>();
        result.put("code", 1200);
        result.put("message", message);
        return JSON.toJSONString(result);
    }

    public static String ok(String message, Object data){
        Map<String, Object> result = new HashMap<>();
        result.put("code", 1200);
        result.put("message", message);
        result.put("data", data);
        return JSON.toJSONString(result);
    }

    public static String error(String message) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 1400);
        result.put("message", message);
        return JSON.toJSONString(result);
    }

    public static String info(String message) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 1200);
        result.put("message", message);
        return JSON.toJSONString(result);
    }

}
