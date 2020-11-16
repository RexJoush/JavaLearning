package com.joush.dao.impl;

import com.joush.dao.AccountDao;

/**
 * 账户的持久层实现类
 */
public class AccountDaoImpl implements AccountDao {


    @Override
    public void saveAccount() {
        System.out.printf("保存成功");
    }
}
