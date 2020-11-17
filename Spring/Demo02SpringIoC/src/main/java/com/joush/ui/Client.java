package com.joush.ui;

import com.joush.service.AccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 模拟表现层用于调用业务层
 */
public class Client {

    /**
     * @param args
     */
    public static void main(String[] args) {

        // 1.获取核心容器对象
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");

        // 2.根据 id 获取 bean 对象
        AccountService accountService = (AccountService) ac.getBean("accountService");

        System.out.println(accountService);
//        accountService.saveAccount();


    }
}
