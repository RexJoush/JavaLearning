package com.joush.test;

import com.joush.service.AccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 测试 AoP 的配置
 */
public class AoPTest {

    public static void main(String[] args) {
        // 1.获取容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");

        // 2.获取对象
        AccountService accountService = (AccountService) ac.getBean("accountServiceImpl");

        // 3.执行方法
        accountService.saveAccount();
    }

}
