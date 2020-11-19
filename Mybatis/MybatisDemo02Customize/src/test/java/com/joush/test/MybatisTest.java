package com.joush.test;

import com.joush.dao.UserDao;
import com.joush.domain.User;
import com.joush.mybatis.io.Resources;
import com.joush.mybatis.sqlsession.SqlSession;
import com.joush.mybatis.sqlsession.SqlSessionFactory;
import com.joush.mybatis.sqlsession.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * mybatis 的入门案例
 *
 */
public class MybatisTest {

    public static void main(String[] args) throws IOException {
        // 1.读取配置文件
        InputStream resource = Resources.getResourceAsStream("sqlMapConfig.xml");

        // 2.创建 SqlSessionFactory 工厂
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(resource);

        // 3.使用工厂生产 SqlSession 对象
        SqlSession session = factory.openSession();

        // 4.使用 SqlSession 创建 Dao 接口的代理对象
        UserDao dao = session.getMapper(UserDao.class);

        // 5.使用代理对象执行方法
        List<User> users = dao.findAll();
        for (User user : users){
            System.out.println(user);
        }

        // 6.释放资源
        session.close();
        resource.close();
    }
}
