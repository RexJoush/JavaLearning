package com.joush.redis;


import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Demo01JedisTest {

    public static void main(String[] args) {
        test6();
    }

    // sortedset 操作
    public static void test6() {

        // 获取连接
        Jedis jedis = new Jedis();

        // 添加
        jedis.zadd("msset",3,"aaa");
        jedis.zadd("msset",6,"ccc");
        jedis.zadd("msset",4,"bbb");

        // 获取
        Set<String> msset = jedis.zrange("msset", 0, -1);
        System.out.println(msset);

        jedis.close();
    }

    // set 操作
    public static void test5() {

        // 获取连接
        Jedis jedis = new Jedis();

        // 添加操作
        jedis.sadd("myset","a");
        jedis.sadd("myset","b");
        jedis.sadd("myset","a");
        jedis.sadd("myset","c");

        // 查看结果
        Set<String> myset = jedis.smembers("myset");
        System.out.println(myset);

        // 删除
        jedis.srem("myset","c");

        // 查看结果
        Set<String> myset2 = jedis.smembers("myset");
        System.out.println(myset2);

        jedis.close();
    }

    // list 操作
    public static void test4() {
        // 获取连接
        Jedis jedis = new Jedis();

        // 操作
        jedis.lpush("mylist","1"); // 从左边存 1 2 3 4
        jedis.rpush("mylist", "9"); // 从右边存 9 8 7 6

        // list 范围获取
        List<String> list = jedis.lrange("mylist", 0, -1);
        System.out.println(list);

        String lpop = jedis.lpop("mylist");
        String rpop = jedis.rpop("mylist");

        // list 范围获取
        List<String> list2 = jedis.lrange("mylist", 0, -1);
        System.out.println(list);

        jedis.close();

    }

    // hash 操作
    public static void test3() {

        // 获取连接
        Jedis jedis = new Jedis();

        // 存储 hash
        jedis.hset("user", "name", "joush");
        jedis.hset("user", "age", "23");
        jedis.hset("user", "sex", "male");

        // 获取 hash
        String hget = jedis.hget("user", "name");
        System.out.println(hget);

        // 获取 hash map 中的所有数据
        Map<String, String> user = jedis.hgetAll("user");

        for (String s : user.keySet()){
            System.out.println(s + ":" + user.get(s));
        }

        jedis.close();


    }

    // string 操作
    public static void test2() {

        // 1.获取连接
        Jedis jedis = new Jedis(); // 空参，默认值是 localhost, 6379

        // 字符串存取操作
        jedis.set("username","joush");

        String username = jedis.get("username");
        System.out.println(username);

        // 可以使用 setex 方法，可以指定过期时间的 key value
        jedis.setex("user",10,"rex");

    }


    // 快速入门
    public static void test1() {
        // 1.获取连接
        Jedis jedis = new Jedis("localhost", 6379);

        // 2.操作
        jedis.set("name","joush");

        // 3.关闭连接
        jedis.close();
    }


}
