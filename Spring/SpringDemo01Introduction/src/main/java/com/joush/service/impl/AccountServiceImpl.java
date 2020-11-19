package com.joush.service.impl;

import com.joush.dao.AccountDao;
import com.joush.factory.BeanFactory;
import com.joush.service.AccountService;

/**
 * 账户业务层的实现类
 */
public class AccountServiceImpl implements AccountService {

    private AccountDao accountDao = (AccountDao) BeanFactory.getBean("accountDao");

    @Override
    public void saveAccount() {
        accountDao.saveAccount();
    }
}
