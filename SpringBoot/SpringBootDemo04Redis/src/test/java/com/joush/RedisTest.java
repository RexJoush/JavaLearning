package com.joush;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joush.domain.User;
import com.joush.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void redisTest() throws JsonProcessingException {

        // 1.从 redis 获取数据，数据形式为 json
        String userListJson = redisTemplate.boundValueOps("user.findAll").get();

        // 2.判断 redis 是否存在该数据
        if (null == userListJson){
            // 3.不存在，去数据库查询
            List<User> users = userMapper.queryUserList();

            // 4.将查询的结果放入 json 格式，使用 jackson 转换
            ObjectMapper objectMapper = new ObjectMapper();
            userListJson = objectMapper.writeValueAsString(users);

            redisTemplate.boundValueOps("user.findAll").set(userListJson);

            System.out.println("数据库中查询获得 User");
        } else {
            // 5.打印
            System.out.println("获取 redis 缓存");
        }

        System.out.println(userListJson);

    }

}
