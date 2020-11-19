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

public class AnnotationCRUDTest {
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
     * 测试添加用户
     */
    @Test
    public void testSave(){
        User user = new User();
        user.setId(42);
        user.setUsername("aaa");
        user.setAddress("西安市长安区");
        user.setSex("男");
        user.setBirthday("2020-11-11");

        userDao.saveUser(user);
    }

    /**
     * 测试更新用户
     */
    @Test
    public void testUpdate(){
        User user = new User();
        user.setId(42);
        user.setUsername("bbb");
        user.setAddress("西安市长安区");
        user.setSex("男");
        user.setBirthday("2020-11-11");

        userDao.updateUser(user);
    }

    /**
     * 测试删除用户
     */
    @Test
    public void testDelete(){
        userDao.deleteUser(42);
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

    /**
     * 测试查询用户数
     */
    @Test
    public void testFindTotal(){
        int total = userDao.findTotal();
        System.out.println(total);
    }
}
