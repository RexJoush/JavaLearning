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


    /**
     * 查询所有
     * @return
     */
    public List<User> findAll() {

        // 1.根据 factory 获取 SqlSession 对象
        SqlSession sqlSession = factory.openSession();

        // 2.调用 SqlSession 的方法，实现查询列表
        List<User> users = sqlSession.selectList("com.joush.dao.UserDao.findAll"); // 参数是能获取信息的 key

        // 3.释放资源
        sqlSession.close();
        return users;
    }

    public void saveUser(User user) {
        // 1.根据 factory 获取 SqlSession 对象
        SqlSession sqlSession = factory.openSession();

        // 2.调用 SqlSession 的方法实现保存
        sqlSession.insert("com.joush.dao.UserDao.saveUser", user); // 参数是能获取信息的 key

        // 3.提交事务
        sqlSession.commit();

        // 4.释放资源
        sqlSession.close();


    }

    public void updateUser(User user) {
        // 1.根据 factory 获取 SqlSession 对象
        SqlSession sqlSession = factory.openSession();

        // 2.调用 SqlSession 的方法实现保存
        sqlSession.update("com.joush.dao.UserDao.updateUser", user); // 参数是能获取信息的 key

        // 3.提交事务
        sqlSession.commit();

        // 4.释放资源
        sqlSession.close();
    }

    public void deleteUser(int id) {
        // 1.根据 factory 获取 SqlSession 对象
        SqlSession sqlSession = factory.openSession();

        // 2.调用 SqlSession 的方法实现保存
        sqlSession.delete("com.joush.dao.UserDao.deleteUser", id); // 参数是能获取信息的 key

        // 3.提交事务
        sqlSession.commit();

        // 4.释放资源
        sqlSession.close();
    }

    public User findById(int id) {
        // 1.根据 factory 获取 SqlSession 对象
        SqlSession sqlSession = factory.openSession();

        // 2.调用 SqlSession 的方法实现保存
        User user = sqlSession.selectOne("com.joush.dao.UserDao.findById", id);

        // 4.释放资源
        sqlSession.close();

        // 5.返回结果
        return user;
    }

    public List<User> findByName(String username) {
        // 1.根据 factory 获取 SqlSession 对象
        SqlSession sqlSession = factory.openSession();

        // 2.调用 SqlSession 的方法，实现模糊查询列表
        List<User> users = sqlSession.selectList("com.joush.dao.UserDao.findByName", username); // 参数是能获取信息的 key

        // 3.释放资源
        sqlSession.close();
        return users;
    }

    public int findTotal() {
        // 1.根据 factory 获取 SqlSession 对象
        SqlSession sqlSession = factory.openSession();

        // 2.调用 SqlSession 的方法实现查询个数
        int total = sqlSession.selectOne("com.joush.dao.UserDao.findTotal");

        // 4.释放资源
        sqlSession.close();

        // 5.返回结果
        return total;
    }
}
