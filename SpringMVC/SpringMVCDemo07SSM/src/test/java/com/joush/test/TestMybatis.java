package com.joush.test;

import com.joush.dao.AccountDao;
import com.joush.domain.Account;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TestMybatis {

    /**
     * 测试查询
     * @throws IOException
     */
    @Test
    public void run1() throws IOException {
        // 1.加载配置文件
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");

        // 2.创建 SqlSessionFactory 对象
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);

        // 3.创建 SqlSession 对象
        SqlSession sqlSession = factory.openSession();

        // 4.获取代理对象
        AccountDao dao = sqlSession.getMapper(AccountDao.class);

        // 5.查询
        List<Account> accounts = dao.findAll();
        for (Account account : accounts) {
            System.out.println(account);
        }

        // 6.释放资源
        sqlSession.close();
        inputStream.close();

    }

    @Test
    public void run2() throws IOException {
        Account account = new Account();
        account.setName("octopus");
        account.setMoney(500);

        // 1.加载配置文件
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");

        // 2.获取 SqlSessionFactory 对象
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);

        // 3.获取 SqlSession 对象
        SqlSession sqlSession = factory.openSession();

        // 4.获取代理 dao 对象
        AccountDao dao = sqlSession.getMapper(AccountDao.class);

        // 5.执行
        dao.saveAccount(account);

        // 提交事务
        sqlSession.commit();

        // 6.关闭资源
        sqlSession.close();
        inputStream.close();
    }
}
