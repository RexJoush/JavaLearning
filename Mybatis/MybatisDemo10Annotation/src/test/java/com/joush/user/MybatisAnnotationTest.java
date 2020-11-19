package com.joush.user;

import com.joush.dao.UserDao;
import com.joush.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisAnnotationTest {

    /**
     * 测试基于注解的 mybatis 使用
     * @param args
     */
    public static void main(String[] args) throws IOException {

        // 1.获取字节输入流
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");

        // 2.获取 SqlSessionFactory 工厂
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);

        // 3.获取 SqlSession 对象
        SqlSession sqlSession = factory.openSession();

        // 4.获取代理 dao 对象
        UserDao userDao = sqlSession.getMapper(UserDao.class);

        // 5.执行方法
        List<User> users = userDao.findAll();
        for (User user : users) {
            System.out.println(user);
        }

        // 6.释放资源
        sqlSession.close();
        in.close();
    }

}
