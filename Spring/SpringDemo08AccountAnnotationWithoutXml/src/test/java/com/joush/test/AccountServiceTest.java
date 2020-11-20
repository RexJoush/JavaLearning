package com.joush.test;

import com.joush.domain.Account;
import com.joush.service.AccountService;
import config.SpringConfiguration;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * 使用 Junit 单元测试，测试配置
 */
public class AccountServiceTest {

    @Test
    public void testFindAll(){
        // 1.获取容器
        ApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfiguration.class);

        // 2.拿到业务层对象
        AccountService accountService = ac.getBean("accountServiceImpl", AccountService.class);

        // 3.执行方法
        List<Account> accounts = accountService.findAll();

        // 4.打印结果
        for (Account account : accounts) {
            System.out.println(account);
        }

    }

    @Test
    public void testFindOne(){
        // 1.获取容器
        ApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfiguration.class);

        // 2.拿到业务层对象
        AccountService accountService = ac.getBean("accountService", AccountService.class);

        // 3.执行方法
        Account account = accountService.findAccountById(1);

        // 4.打印结果
        System.out.println(account);
    }

    @Test
    public void testSave(){
        // 1.获取容器
        ApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfiguration.class);

        // 2.拿到业务层对象
        AccountService accountService = ac.getBean("accountService", AccountService.class);

        // 3.配置对象
        Account account = new Account();

        account.setName("ddd");
        account.setMoney(1000);

        // 4.执行方法
        accountService.saveAccount(account);

    }

    @Test
    public void testUpdate(){
        // 1.获取容器
        ApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfiguration.class);

        // 2.拿到业务层对象
        AccountService accountService = ac.getBean("accountService", AccountService.class);

        // 3.配置对象
        Account account = new Account();

        account.setId(3);
        account.setName("ccc");
        account.setMoney(2000d);

        // 4.执行方法
        accountService.updateAccount(account);
    }

    @Test
    public void testDelete(){
        // 1.获取容器
        ApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfiguration.class);

        // 2.拿到业务层对象
        AccountService accountService = ac.getBean("accountService", AccountService.class);

        // 3.执行方法
        accountService.deleteAccount(4);
    }
}
