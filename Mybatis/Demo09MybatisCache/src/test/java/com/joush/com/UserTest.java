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
import java.util.List;

/**
 * 测试 Mybatis 的 CRUD 操作
 */
public class UserTest {

    private InputStream in;
    private SqlSession sqlSession;
    private UserDao userDao;

    /**
     * 初始化资源
     * @throws IOException
     */
    @Before // 用于在测试方法之前执行
    public void init() throws IOException {
        // 1.读取配置文件
        in = Resources.getResourceAsStream("SqlMapConfig.xml");

        // 2.获取 SqlSessionFactory 对象
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);

        // 3.获取 SqlSession 对象
        sqlSession = factory.openSession();

        // 4.获取 UserDao 的代理对象
        userDao = sqlSession.getMapper(UserDao.class);
    }

    /**
     * 释放资源方法
     * @throws IOException
     */
    @After // 在测试方法执行之后执行
    public void destroy() throws IOException {
        // 提交事务
        sqlSession.commit();

        // 释放资源
        sqlSession.close();
        in.close();
    }


    /**
     * 测试查询所有
     */
    @Test
    public void testFirstLevelCache(){

        User user1 = userDao.findById(41);
        System.out.println(user1); // com.com.joush.domain.User@b968a76

        sqlSession.clearCache(); // 此方法可以清空缓存

        User user2 = userDao.findById(41);
        System.out.println(user2); // com.com.joush.domain.User@b968a76

        System.out.println(user1 == user2); // true


    }

    /**
     * 测试缓存的同步
     */
    @Test
    public void testClearCache(){
        // 查询用户
        User user1 = userDao.findById(41);
        System.out.println(user1); // com.com.joush.domain.User@73ff4fae

        // 更新用户信息
        user1.setUsername("update clear cache");
        user1.setAddress("上海市徐汇区");

        userDao.updateUser(user1);

        // 再次查询
        User user2 = userDao.findById(41);
        System.out.println(user2); // com.com.joush.domain.User@2611b9a3

        System.out.println(user1 == user2); // false

    }
}
