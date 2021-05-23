package com.joush.controller;

import com.joush.entities.User;
import com.joush.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Rex Joush
 * @time 2021.05.19 11:05
 */

@RestController
public class UserController {

    @Resource
    UserService userService;

    @GetMapping("/getAllUser")
    public List<User> getAllUser(){

        return userService.findAllUser();
    }

}
