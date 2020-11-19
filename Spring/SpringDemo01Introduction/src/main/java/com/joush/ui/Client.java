package com.joush.ui;

import com.joush.factory.BeanFactory;
import com.joush.service.AccountService;

/**
 * 模拟表现层用于调用业务层
 */
public class Client {
    public static void main(String[] args) {

        AccountService accountService = (AccountService) BeanFactory.getBean("accountService");

        accountService.saveAccount();

    }
}
