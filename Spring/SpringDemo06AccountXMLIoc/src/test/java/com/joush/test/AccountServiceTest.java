package com.joush.test;

import com.joush.domain.Account;
import com.joush.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 使用 Junit 单元测试，测试配置
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean.xml")
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

        // 3.执行方法
        Account account = accountService.findAccountById(1);

        // 4.打印结果
        System.out.println(account);
    }

    @Test
    public void testSave(){

        // 3.配置对象
        Account account = new Account();

        account.setName("ddd");
        account.setMoney(1000);

        // 4.执行方法
        accountService.saveAccount(account);

    }

    @Test
    public void testUpdate(){

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

        // 3.执行方法
        accountService.deleteAccount(4);
    }
}
