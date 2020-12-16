package com.joush;

import com.joush.domain.User;
import com.joush.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SpringBootApplicationTests {

    @Autowired
    private UserMapper userMapper;


    @Test
    void contextLoads() {


    }

    @Test
    void mybatisTest(){
        List<User> users = userMapper.queryUserList();
        System.out.println(users);
    }

}
