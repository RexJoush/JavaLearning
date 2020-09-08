package com.joush.dao;

import com.joush.domain.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户的持久层交口
 */
public interface IUserDao {

    /**
     * 查询所有操作
     */
    @Select("select * from user")
    List<User> findAll();

}
