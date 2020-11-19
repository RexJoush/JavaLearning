package com.joush.service.impl;

import com.joush.service.AccountService;

import java.util.Date;

/**
 * 账户业务层的实现类
 */
public class AccountServiceImpl2 implements AccountService {

    private String name;
    private int age;
    private Date birthday;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public void saveAccount() {
        System.out.println("service saveAccount 执行了" + name + ", " + age + ", " + birthday);
    }
}
