package com.joush.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rex Joush
 * @time 2021.03.23
 */

@RestController
public class RouterController {

    @RequestMapping({"/", "/index"})
    public String index(){
        System.out.println("controller");
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

}
