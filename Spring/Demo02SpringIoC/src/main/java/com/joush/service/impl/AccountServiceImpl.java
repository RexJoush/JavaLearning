package com.joush.service.impl;

import com.joush.dao.AccountDao;
import com.joush.dao.impl.AccountDaoImpl;
import com.joush.service.AccountService;

/**
 * 账户业务层的实现类
 *
 * 曾经的 xml 配置方法
 * <bean id="accountService" class="com.com.joush.service.impl.AccountServiceImpl"></bean>
 *
 * 用于创建对象的
 * 用于注入数据的
 * 用于改变作用范围的
 * 和生命周期相关的
 */
public class AccountServiceImpl implements AccountService {

    private AccountDao accountDao;

    public AccountServiceImpl() {
        System.out.println("对象创建");
    }

    @Override
    public void saveAccount() {
        accountDao.saveAccount();
    }
}
