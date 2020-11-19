package com.joush.dao;


import com.joush.domain.User;

import java.util.List;

/**
 * 用户的持久层接口
 */
public interface UserDao {

    /**
     * 查询所有 同时获取所有账户信息
     * @return
     */
    List<User> findAll();

    /**
     * 根据 id 查询用户
     * @param id
     * @return
     */
    User findById(int id);

    /**
     * 更新用户信息
     * @param user
     */
    void updateUser(User user);

}
