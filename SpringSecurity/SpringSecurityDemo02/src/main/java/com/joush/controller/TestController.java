package com.joush.controller;

import com.joush.entities.User;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rex Joush
 * @time 2021.08.05 15:11
 */
@RestController
public class TestController {

    @GetMapping("/index")
    public String index() {
        return "Hello Index";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello Security";
    }

    @GetMapping("/annotation")
    @Secured({"ROLE_user", "ROLE_admin1"}) // 指定 user 和 admin 两个角色可以访问此接口
    // @PreAuthorize("hasAnyAuthority('admin')")
    public String annotation() {
        return "Hello Annotation";
    }

    @GetMapping("/prePost")
    @PostAuthorize("hasAnyAuthority('admin')")
    public String prePostEnabled() {
        System.out.println("aaa");
        return "Hello prePost";
    }

    @GetMapping("/prePostFilter")
    @PostAuthorize("hasAnyRole('admin')")
    @PostFilter("filterObject.username == '李一航'")
    public List<User> prePostFilter() {

        List<User> users = new ArrayList<>();
        users.add(new User(12, "李一航", "123456", 12, "aaa"));
        users.add(new User(13, "陈一然", "123456", 12, "aaa"));
        return users;
    }

}
