package com.joush;

import com.joush.domain.User;
import com.joush.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySpringBootApplication.class)
@WebAppConfiguration
public class MybatisTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test(){
        List<User> users = userMapper.queryUserList();
        System.out.println(users);
    }

}
