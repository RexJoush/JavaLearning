package com.loginTest.dao;

import com.loginTest.domain.User;
import com.loginTest.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 *  操作数据库中的 User 表
 */
public class UserDao {

    // 声明 JdbcTemplate 对象共用
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());


    /**
     * 登录方法
     * @param loginUser
     * @return user的全部数据
     */
    public User login(User loginUser){
        // 1.编写sql
        String sql = "select * from user where username = ? and password = ?";

        // 2.调用query方法
        List<User> users = template.query(sql,
                new BeanPropertyRowMapper<>(User.class),
                loginUser.getUsername(), loginUser.getPassword());
        System.out.println(users);
        if (users.size() == 0){
            return null;
        } else {
            return users.get(0);
        }

    }
}
