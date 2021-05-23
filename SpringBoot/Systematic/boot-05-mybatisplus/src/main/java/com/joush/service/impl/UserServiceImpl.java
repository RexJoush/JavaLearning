package com.joush.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joush.entities.User;
import com.joush.mapper.UserMapper;
import com.joush.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author Rex Joush
 * @time 2021.05.21 10:44
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
