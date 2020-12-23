package com.joush.controller;

import com.joush.dao.UserDao;
import com.joush.domain.User;
import com.joush.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 查询所有
     * @return
     */
    @RequestMapping("/findAll")
    public List<User> findAll() {
        System.out.println("controller findAll");
        return userService.findAll();
    }

    /**
     * 通过 id 查询
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public User findById(int id) {
        return userService.findById(id);
    }

    /**
     * 更新账户
     * @param user
     */
    @RequestMapping("/updateUser")
    public void updateUser(@RequestBody User user) {
        userService.updateUser(user);
    }

}
