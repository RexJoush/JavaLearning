package com.joush.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuickController {

    @RequestMapping("/quick")
    public String quick(){
        return "Spring Boot!";
    }

}
