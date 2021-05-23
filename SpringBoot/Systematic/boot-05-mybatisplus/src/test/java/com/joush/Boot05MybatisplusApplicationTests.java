package com.joush;

import com.joush.entities.User;
import com.joush.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class Boot05MybatisplusApplicationTests {

    @Resource
    UserMapper userMapper;

    @Test
    void contextLoads() {

        User user = userMapper.selectById(1);
        System.out.println(user);

    }

}
