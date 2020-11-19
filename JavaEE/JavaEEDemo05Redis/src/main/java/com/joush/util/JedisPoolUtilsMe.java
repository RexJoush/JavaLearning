package com.joush.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JedisPoolUtilsMe {

    private static JedisPool jedisPool;

    static {
        // 读取文件
        InputStream resource = JedisPool.class.getClassLoader().getResourceAsStream("jedis.properties");

        // 创建 Properties对象

        Properties properties = new Properties();

        try {
            properties.load(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println();
        int maxTotal = Integer.parseInt(properties.getProperty("maxTotal"));
        int maxIdle = Integer.parseInt(properties.getProperty("maxIdle"));
        String host = properties.getProperty("host");
        int port = Integer.parseInt(properties.getProperty("port"));

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);

        jedisPool = new JedisPool(config, host, port);

    }


    public static Jedis getJedis () {
        return jedisPool.getResource();
    }

}
