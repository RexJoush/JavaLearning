package com.joush.user;

import com.joush.dao.UserDao;
import com.joush.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class UserTest {
    private InputStream in;
    private SqlSessionFactory factory;
    private SqlSession sqlSession;
    private UserDao userDao;

    /**
     * 初始化工具
     * @throws IOException
     */
    @Before
    public void init() throws IOException {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");

        factory = new SqlSessionFactoryBuilder().build(in);

        sqlSession = factory.openSession();

        userDao = sqlSession.getMapper(UserDao.class);
    }

    /**
     * 释放资源
     */
    @After
    public void destroy(){
        sqlSession.commit();
        sqlSession.close();
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试查询所有
     */
    @Test
    public void testFindAll(){
        List<User> users = userDao.findAll();
        for (User user : users) {
            System.out.println(user);
            System.out.println(user.getAccounts());
        }
    }


    /**
     * 测试查询一个
     */
    @Test
    public void testFindById(){
        User user = userDao.findById(42);
        System.out.println(user);
    }

    /**
     * 测试模糊查询
     */
    @Test
    public void testFindByName(){
        List<User> users = userDao.findByName("%a%");
        for (User user : users) {
            System.out.println(user);
        }
    }

}
