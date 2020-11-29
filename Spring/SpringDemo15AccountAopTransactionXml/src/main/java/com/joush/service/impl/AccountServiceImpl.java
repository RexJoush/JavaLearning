package com.joush.service.impl;

import com.joush.dao.AccountDao;
import com.joush.dao.impl.AccountDaoImpl;
import com.joush.domain.Account;
import com.joush.service.AccountService;
import com.joush.utils.TransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 账户业务层的实现类
 */

public class AccountServiceImpl implements AccountService {

    private AccountDao accountDao;

    public void setAccountDao(AccountDaoImpl accountDao) {
        this.accountDao = accountDao;
    }
    // 事务控制的对象
    private TransactionManager transactionManager;

    public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public List<Account> findAll() {
        return accountDao.findAll();
    }

    @Override
    public Account findAccountById(int id) {
        return accountDao.findAccountById(id);
    }

    @Override
    public void saveAccount(Account account) {
        accountDao.saveAccount(account);
    }

    @Override
    public void updateAccount(Account account) {
        accountDao.updateAccount(account);
    }

    @Override
    public void deleteAccount(int id) {
        accountDao.deleteAccount(id);
    }

    @Override
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
