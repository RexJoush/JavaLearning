package com.joush.controller;

import com.joush.domain.User;
import com.joush.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/user")
public class MybatisController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/queryUserList")
    @ResponseBody
    public List<User> queryUserList(){
        List<User> users = userMapper.queryUserList();
        System.out.println(users);
        return users;
    }
}
