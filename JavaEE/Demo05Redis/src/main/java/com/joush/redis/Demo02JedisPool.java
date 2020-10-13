package com.joush.redis;

import com.joush.util.JedisPoolUtils;
import com.joush.util.JedisPoolUtilsMe;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Demo02JedisPool {

    public static void main(String[] args) {
        test2();
    }


    // 测试自定义的连接池工具类
    public static void test2() {
        // 获取 jedis 对象
        Jedis jedis = JedisPoolUtils.getJedis();

        // 使用
        jedis.set("hello","world");
        String hello = jedis.get("hello");

        System.out.println(hello);

        // 归还连接池
        jedis.close();

    }

    // 测试 jedis 连接池
    private static void test() {

        // 0.创建一个 Jedis 连接池配置对象
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(50); // 最大连接数
        config.setMaxIdle(10); // 最大空闲连接数

        // 1.获取 jedis 连接池对象
        JedisPool jedisPool = new JedisPool(config, "localhost", 6379);

        // 2.获取 jedis 对象
        Jedis jedis = jedisPool.getResource();

        // 3.jedis操作
        jedis.set("aaa","ccc");
        String aaa = jedis.get("aaa");
        System.out.println(aaa);

        // 4.关闭，即归还连接池对象
        jedis.close();

    }


}
