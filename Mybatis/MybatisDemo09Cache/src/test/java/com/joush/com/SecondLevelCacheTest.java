package com.joush.com;

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

/**
 * 测试 Mybatis 的 CRUD 操作
 */
public class SecondLevelCacheTest {

    private InputStream in;
    private SqlSessionFactory factory;

    /**
     * 初始化资源
     * @throws IOException
     */
    @Before // 用于在测试方法之前执行
    public void init() throws IOException {
        // 1.读取配置文件
        in = Resources.getResourceAsStream("SqlMapConfig.xml");

        // 2.获取 SqlSessionFactory 对象
        factory = new SqlSessionFactoryBuilder().build(in);

    }

    /**
     * 释放资源方法
     * @throws IOException
     */
    @After // 在测试方法执行之后执行
    public void destroy() throws IOException {

        in.close();
    }


    /**
     * 测试查询所有
     */
    @Test
    public void testSecondLevelCache(){

        /*
            第一个 SqlSession 对象
         */

        // 3.获取 SqlSession 对象
        SqlSession sqlSession1 = factory.openSession();

        // 4.获取 UserDao 的代理对象
        UserDao userDao1 = sqlSession1.getMapper(UserDao.class);

        // 查询 41 的用户信息
        User user1 = userDao1.findById(41);
        System.out.println(user1); // com.com.com.joush.domain.User@47dbb1e2

        sqlSession1.close(); // 一级缓存消失

        /*
            第二个 SqlSession 对象
         */
        // 3.获取 SqlSession 对象
        SqlSession sqlSession2 = factory.openSession();

        // 4.获取 UserDao 的代理对象
        UserDao userDao2 = sqlSession2.getMapper(UserDao.class);

        // 查询 41 的用户信息
        User user2 = userDao2.findById(41);
        System.out.println(user2); // com.com.com.joush.domain.User@77602954

        sqlSession1.close(); // 一级缓存消失


        /*
            此处 false 的原因，二级缓存存放的是数据，而不是对象，所以获取的不是同一个对象
         */
        System.out.println(user1 == user2); // false



    }
}
