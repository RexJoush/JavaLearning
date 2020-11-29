package com.joush.service.impl;

import com.joush.dao.AccountDao;
import com.joush.dao.impl.AccountDaoImpl;
import com.joush.domain.Account;
import com.joush.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 账户业务层的实现类
 */

@Service("accountService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true) // 查询型配置
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;


    @Override
    public Account findAccountById(int id) {
        return accountDao.findAccountById(id);
    }



    @Override
    // 需要读写型配置
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void transfer(String sourceName, String targetName, double money) {

        System.out.println("transfer...");

        // 1.根据名称查询转出账户
        Account source = accountDao.findAccountByName(sourceName);

        // 2.根据名称查询转入账户
        Account target = accountDao.findAccountByName(targetName);

        // 3.转出账户减钱
        source.setMoney(source.getMoney() - money);
        accountDao.updateAccount(source);

//        int i = 1/0;

        // 4.转入账户加钱
        target.setMoney(target.getMoney() + money);
        accountDao.updateAccount(target);
    }



}
