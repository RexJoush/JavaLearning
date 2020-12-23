package com.joush.service;

import com.joush.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {

    /**
     * 查询所有
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
     * 更新用户
     * @param user
     */
    void updateUser(User user);

}
