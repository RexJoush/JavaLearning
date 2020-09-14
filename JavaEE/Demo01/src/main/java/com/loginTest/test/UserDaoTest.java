package com.joush.test;

import com.joush.dao.UserDao;
import com.joush.domain.User;
import org.junit.Test;

public class UserDaoTest {
    @Test
    public void testLogin(){
        User user = new User();
        user.setUsername("super");
        user.setPassword("admin");

        UserDao dao = new UserDao();
        User loginUser = dao.login(user);

        System.out.println(loginUser);

    }
}
