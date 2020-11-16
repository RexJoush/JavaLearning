package com.joush.dao;

import com.joush.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * 在 mybatis 中，CRUD 有四个注解
 * @Select @Insert @Update @Delete
 */
@CacheNamespace(blocking = true)
public interface UserDao {

    /**
     * 查询所有用户
     * @return
     */
    @Select("select * from user")
    @Results(id = "userMap", value = {
            @Result(id = true, column = "id", property = "userId"),
            @Result(column = "username", property = "userName"),
            @Result(column = "address", property = "userAddress"),
            @Result(column = "sex", property = "userSex"),
            @Result(column = "birthday", property = "userBirthday"),
            @Result(column = "id", property = "accounts", many = @Many(
                select = "com.com.joush.dao.AccountDao.findById", fetchType = FetchType.LAZY
            ))
    })
    List<User> findAll();

    /**
     * 查询一个
     * @param id
     * @return
     */
    @Select("select * from user where id = #{id}")
    @ResultMap(value = {"userMap"})
    User findById(int id);

    /**
     * 根据名字模糊查询
     * @return
     */
    @Select("select * from user where username like #{username}")
    List<User> findByName(String username);

}
