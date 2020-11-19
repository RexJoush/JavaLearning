package com.joush.factory;

import com.joush.service.AccountService;
import com.joush.service.impl.AccountServiceImpl;

/*
    模拟一个工厂类，该类可能是存在于 jar 包中，我们无法通过修改源码的方式来提供默认构造函数
 */
public class StaticFactory {

    public static AccountService getAccountService(){
        return new AccountServiceImpl();
    }

}
