package com.joush.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/*

    JedisPool 工具类
    加载配置文件，配置连接池参数
    提供获取连接方法


 */
public class JedisPoolUtils {

    private static JedisPool jedisPool;

    static {
        // 读取配置文件
        InputStream resource = JedisPool.class.getClassLoader().getResourceAsStream("jedis.properties");

        // 创建 Properties 对象
        Properties properties = new Properties();

        // 关联文件
        try {
            properties.load(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 获取数据，设置到 JedisPoolConfig 中
        JedisPoolConfig config = new JedisPoolConfig();

        // 添加配置
        config.setMaxTotal(Integer.parseInt(properties.getProperty("maxTotal")));
        config.setMaxIdle(Integer.parseInt(properties.getProperty("maxIdle")));

        // 初始化 JedisPool
        jedisPool = new JedisPool(config,properties.getProperty("host"), Integer.parseInt(properties.getProperty("port")));
    }


    // 获取连接方法
    public static Jedis getJedis(){
        return jedisPool.getResource();
    }


}
