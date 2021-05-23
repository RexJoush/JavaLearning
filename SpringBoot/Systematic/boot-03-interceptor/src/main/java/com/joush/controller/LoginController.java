package com.joush.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rex Joush
 * @time 2021.05.10 11:03
 */
@RestController
public class LoginController {

    @GetMapping(value = {"/login", "/"})
    public String login(){
        return "login";
    }


}
