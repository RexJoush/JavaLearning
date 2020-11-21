package com.joush.test;

import com.joush.domain.Account;
import com.joush.service.AccountService;
import config.SpringConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 *  使用 Junit 单元测试，测试配置
 *  Spring 整合 Junit 配置
 *      1.导入 spring 整合 junit 的坐标
 *      2.使用 Junit 提供的注解，把原有的 main 方法替换成 spring 提供的 main 方法
 *          @RunWith
 *      3.告知 spring 运行器，ioc 是基于 xml 还是注解，并说明位置
 *          @ContextConfiguration
 *              locations: 指定 xml 文件的位置
 *              classes: 指定注解类所在的位置
 *  当使用 spring 5.x 版本时，要求 junit 的版本必须是 4.1.2及以上
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Test
    public void testFindAll(){

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
