package com.joush.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rex Joush
 * @time 2021.08.05 15:11
 */
@RestController
public class TestController {

    @GetMapping("/index")
    public String index(){
        return "Hello Index";
    }

    @GetMapping("/annotation")
    @Secured({"ROLE_user", "ROLE_admin1"}) // 指定 user 和 admin 两个角色可以访问此接口
    public String annotation(){
        return "Hello Annotation";
    }

}
