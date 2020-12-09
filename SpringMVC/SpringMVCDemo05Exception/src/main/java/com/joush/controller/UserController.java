package com.joush.controller;

import com.joush.exception.SysException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/testException")
    public String testException() throws SysException {
        System.out.println("testException executed!");



        try {
            // 模拟异常
            int a = 10 / 0;
        } catch (Exception e){
            // 打印异常信息
            e.printStackTrace();
            // 抛出自定义异常
            throw new SysException("查询所有用户报错");
        }

        return "success";
    }

}
