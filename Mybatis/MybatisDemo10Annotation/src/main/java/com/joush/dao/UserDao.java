package com.joush.dao;

import com.joush.domain.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 在 mybatis 中，CRUD 有四个注解
 * @Select @Insert @Update @Delete
 */
public interface UserDao {

    /**
     * 查询所有用户
     * @return
     */
    @Select("select * from user")
    List<User> findAll();

    /**
     * 保存用户
     * @param user
     */
    @Insert("insert into user (id, username, address, sex, birthday) values (#{id},#{username},#{address},#{sex},#{birthday})")
    void saveUser(User user);

    /**
     * 更新用户
     * @param user
     */
    @Update("update user set username = #{username}, address = #{address}, sex = #{sex}, birthday = #{birthday} where id = #{id}")
    void updateUser(User user);

    /**
     * 删除用户
     * @param id
     */
    @Delete("delete from user where id = #{id}")
    void deleteUser(int id);

    /**
     * 查询一个
     * @param id
     * @return
     */
    @Select("select * from user where id = #{id}")
    User findById(int id);

    /**
     * 根据名字模糊查询
     * @return
     */
    @Select("select * from user where username like #{username}")
    List<User> findByName(String username);

    /**
     * 查询总用户数量
     * @return
     */
    @Select("select count(*) from user")
    int findTotal();
}
