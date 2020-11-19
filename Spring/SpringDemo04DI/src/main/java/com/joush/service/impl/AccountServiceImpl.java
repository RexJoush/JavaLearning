package com.joush.service.impl;

import com.joush.service.AccountService;
import java.util.Date;

/**
 * 账户业务层的实现类
 */
public class AccountServiceImpl implements AccountService {

    // 如果是经常变化的数据，并不适合注入的方式
    private String name;
    private int age;
    private Date birthday;

    public AccountServiceImpl(String name, int age, Date birthday) {
        this.name = name;
        this.age = age;
        this.birthday = birthday;
    }



    @Override
    public void saveAccount() {
        System.out.println("service saveAccount 执行了" + name + ", " + age + ", " + birthday);
    }
}
