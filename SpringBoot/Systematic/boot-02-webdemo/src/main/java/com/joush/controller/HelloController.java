package com.joush.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rex Joush
 * @time 2021.04.27 14:23
 */

@RestController
public class HelloController {

    @RequestMapping("/a.txt")
    public String Hello(){
        return "Hello";
    }


}
