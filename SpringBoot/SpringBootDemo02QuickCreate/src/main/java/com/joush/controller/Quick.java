package com.joush.controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Quick {

    @RequestMapping("/quick")
    public String quick(){
        return "hello";
    }


}
