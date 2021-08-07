package com.joush.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rex Joush
 * @time 2021.08.05 15:11
 */
@RestController
public class TestController {

    @GetMapping("/hello")
    public String hello(){
        return "Hello Security";
    }

}
