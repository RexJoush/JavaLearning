package com.joush.dao;

import com.joush.domain.User;
import com.joush.mybatis.annotation.Select;

import java.util.List;

public interface UserDao {


    /**
     * 查询所有操作
     * @return List 查询结果
     */
    @Select("select * from user")
    List<User> findAll();

}
