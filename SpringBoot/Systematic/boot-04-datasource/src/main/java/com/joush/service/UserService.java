package com.joush.service;

import com.joush.entities.User;
import com.joush.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Rex Joush
 * @time 2021.05.19 11:03
 */

@Service
public class UserService {

    @Resource
    UserMapper userMapper;

    public List<User> findAllUser() {
        return userMapper.findAllUser();
    }

}
