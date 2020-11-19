package com.joush.service.impl;

import com.joush.service.AccountService;

/**
 * 账户业务层的实现类
 */
public class AccountServiceImpl implements AccountService {


    @Override
    public void saveAccount() {
        System.out.println("service saveAccount 执行了");
    }

    public void init() {
        System.out.println("对象创建了");
    }
    public void destroy() {
        System.out.println("对象销毁了");
    }
}
