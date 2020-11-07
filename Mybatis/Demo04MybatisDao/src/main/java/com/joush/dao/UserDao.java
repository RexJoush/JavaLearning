package com.joush.dao;

import com.joush.domain.User;

import java.util.List;

/**
 * 用户的持久层接口
 */
public interface UserDao {

    /**
     * 查询所有
     * @return
     */
    List<User> findAll();

    /**
     * 保存方法
     * @param user
     */
    void saveUser(User user);

    /**
     * 更新用户
     * @param user
     */
    void updateUser(User user);

    /**
     * 根据 id 删除用户
     * @param id
     */
    void deleteUser(int id);

    /**
     * 根据 id 查询用户
     * @param id
     * @return
     */
    User findById(int id);


    /**
     * 根据名字模糊查询
     * @param username
     * @return
     */
    List<User> findByName(String username);

    /**
     * 查询总用户数
     * @return
     */
    int findTotal();

}
