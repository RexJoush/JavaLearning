package com.joush.dao.impl;

import com.joush.dao.AccountDao;
import org.springframework.stereotype.Repository;

/**
 * 账户的持久层实现类
 */
@Repository
public class AccountDaoImpl implements AccountDao {


    @Override
    public void saveAccount() {
        System.out.printf("保存成功");
    }
}
