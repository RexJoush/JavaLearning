package com.joush.dao.impl;

import com.joush.dao.UserDao;
import com.joush.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class UserDaoImpl implements UserDao {

    private SqlSessionFactory factory;
    public UserDaoImpl(SqlSessionFactory factory){
        this.factory = factory;
    }

    @Override
    public List<User> findAll() {

        // 使用工厂创建 SqlSession 对象
        SqlSession session = factory.openSession();

        // 使用 session 执行查询所有方法
        List<User> users = session.selectList("com.com.com.joush.dao.UserDao.findAll"); // 配置文件中的 namespace + id 值
        session.close();
        return users;

    }
}
