package com.joush.controller;

import com.joush.bean.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @author Rex Joush
 * @time 2021.04.29 16:20
 */

@Controller
public class ResponseController {

    @ResponseBody
    @GetMapping("/test/person")
    public Person getPerson(){
        Person person = new Person();
        person.setAge(28);
        person.setBirth(new Date().toString());
        person.setUserName("一行");
        return person;
    }

}
