package com.joush.com;

import com.joush.dao.AccountDao;
import com.joush.dao.UserDao;
import com.joush.domain.Account;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 测试 Mybatis 的 CRUD 操作
 */
public class AccountTest {

    private InputStream in;
    private SqlSession sqlSession;
    private AccountDao accountDao;

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
        accountDao = sqlSession.getMapper(AccountDao.class);
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
    public void testFindAll() throws IOException {

        // 5.执行查询所有方法
        List<Account> accounts = accountDao.findAll();
        for (Account account : accounts) {
            System.out.println(account);
            System.out.println(account.getUser());
        }

    }
}
