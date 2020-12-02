package com.joush.controller;

import com.joush.domain.Account;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/param")
public class ParamController {
    /**
     * 请求参数绑定入门
     * @return
     */
    @RequestMapping("/testParam")
    public String testParam(String username){
        System.out.println("参数绑定执行了" + username);
        return "success";
    }

    /**
     * 请求参数绑定，封装到 JavaBean 中
     * @param account
     * @return
     */
    @RequestMapping("/saveAccount")
    public String saveAccount(Account account){
        System.out.println("参数绑定执行了");
        System.out.println(account);
        return "success";
    }
}
