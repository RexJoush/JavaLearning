package com.joush.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joush.entities.User;
import com.joush.service.UserService;
import com.joush.util.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Rex Joush
 * @time 2021.05.21 10:48
 */

@RestController
public class UserController {

    @Resource
    UserService userService;

    @GetMapping("/users")
    public String getUsers(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage){


        // 获取 Page 对象
        Page<User> userPage = new Page<>(currentPage, 3);
        Page<User> page = userService.page(userPage, null);

        return Result.ok("获取列表成功", page);
    }

    @GetMapping("/users/{id}")
    public String getUserById(@PathVariable("id") int id){

        User user = userService.getById(id);

        return Result.ok("获取用户成功", user);
    }


    @DeleteMapping("/users/{id}")
    public String delUser(@PathVariable("id") int id){
        System.out.println(id);

        if (userService.removeById(id)){
            return Result.ok("删除用户成功");
        } else {
            return Result.error("删除用户失败");
        }

    }

    @PostMapping("/users")
    public String addUser(@RequestBody User user){
        if (userService.save(user)){
            return Result.ok("添加用户成功");
        } else {
            return Result.error("添加用户失败");
        }
    }

    @PutMapping("/users")
    public String updateUser(@RequestBody User user){
        if (userService.updateById(user)){
            return Result.ok("修改用户成功");
        } else {
            return Result.error("修改用户失败");
        }
    }

}
