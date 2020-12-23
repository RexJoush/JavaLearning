package com.joush.dao;

import com.joush.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserDao {

    /**
     * 查询所有
     * @return
     */
    @Select("select * from vue_user")
    List<User> findAll();

    /**
     * 根据 id 查询用户
     * @param id
     * @return
     */
    @Select("select * from vue_user where id = #{id}")
    User findById(int id);

    /**
     * 更新用户
     * @param user
     */
    @Update("update vue_user set username=#{username}, password=#{password}, age=#{age}, sex=#{sex}, email=#{email} where id=#{id}")
    void updateUser(User user);
}
