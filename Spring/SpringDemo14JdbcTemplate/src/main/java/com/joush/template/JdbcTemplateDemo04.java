package com.joush.template;


import com.joush.dao.AccountDao;
import com.joush.domain.Account;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * JdbcTemplate 的基于 Ioc 的用法
 */
public class JdbcTemplateDemo04 {

    public static void main(String[] args) {

        // 1.获取容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");

        // 2.获取对象
        AccountDao accountDao = ac.getBean("accountDao", AccountDao.class);

        // 3.执行方法
        Account account = accountDao.findAccountById(1);

        account.setMoney(10000d);

        accountDao.updateAccount(account);

    }

}
