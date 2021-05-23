package com.joush.controller;

import org.springframework.web.bind.annotation.*;

/**
 * @author Rex Joush
 * @time 2021.04.27 15:04
 */

@RestController
public class RestfulController {

    // @RequestMapping(value = "/user",method = RequestMethod.GET)
    @GetMapping("/user")
    public String getUser(){
        return "GET-张三";
    }

    @PostMapping(value = "/user")
    public String saveUser(){
        return "POST-张三";
    }


    @PutMapping(value = "/user")
    public String putUser(){
        return "PUT-张三";
    }

    @DeleteMapping(value = "/user")
    public String deleteUser(){
        return "DELETE-张三";
    }
}
