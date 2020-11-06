package com.joush.com;

import com.joush.dao.UserDao;
import com.joush.domain.QueryVo;
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
public class MybatisTestDiff {

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
    public void testFindAll() throws IOException {

        // 5.执行查询所有方法
        List<User> users = userDao.findAll();
        for (User user : users) {
            System.out.println(user);
        }

    }


    /**
     * 测试添加用户
     */
    @Test
    public void testSaveUser() throws IOException {
        User user = new User();
        user.setId(22);
        user.setUsername("张三");
        user.setBirthday("2020-10-10");
        user.setAddress("河南省洛阳市");
        user.setSex("男");

        // 5.执行添加方法
        userDao.saveUser(user);

        // 6.提交事务,放在 destroy 方法中
        // sqlSession.commit();
    }

    /**
     * 测试更新操作
     */
    @Test
    public void testUpdateUser(){
        User user = new User();
        user.setId(22);
        user.setUsername("张三 update");
        user.setBirthday("2020-10-10");
        user.setAddress("河南省洛阳市");
        user.setSex("女");

        // 5.执行更新方法
        userDao.updateUser(user);
    }

    /**
     * 测试删除操作
     */
    @Test
    public void testDeleteUser(){

        // 5.执行删除方法
        userDao.deleteUser(22);
    }

    /**
     * 测试根据 id 查询一个
     */
    @Test
    public void testFindById(){

        // 5.执行删除方法
        User user = userDao.findById(41);

        // 6.输出查询结果
        System.out.println(user);
    }

    /**
     * 测试 根据名字模糊查询
     */
    @Test
    public void testFindByName(){
        // 5.执行根据名字模糊查询的方法,注意此处需要加上模糊查询的通配符 %
//        List<User> users = userDao.findByName("%王%");
        List<User> users = userDao.findByName("王");

        // 6.输出查询结果
        for (User user : users) {
            System.out.println(user);
        }

    }

    /**
     * 测试查询所有用户数
     */
    @Test
    public void testFindTotal(){
        // 5.执行根据名字模糊查询的方法,注意此处需要加上模糊查询的通配符 %
        int total = userDao.findTotal();

        System.out.println(total);

    }

    /**
     * 测试使用 QueryVo 作为查询条件
     */
    @Test
    public void testFindByVo(){
        // 5.执行根据名字模糊查询的方法,注意此处需要加上模糊查询的通配符 %

        QueryVo vo = new QueryVo();
        User user = new User();
        user.setUsername("%王%");
        vo.setUser(user);
        List<User> users = userDao.findUserByVo(vo);

        // 6.输出查询结果
        for (User u : users) {
            System.out.println(u);
        }

    }


}
