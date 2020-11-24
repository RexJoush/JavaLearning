package com.joush.service.impl;

import com.joush.dao.AccountDao;
import com.joush.dao.impl.AccountDaoImpl;
import com.joush.domain.Account;
import com.joush.service.AccountService;
import com.joush.utils.TransactionManager;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 账户业务层的实现类
 */
@Service
public class AccountServiceImpl_old implements AccountService {

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

        try {
            // 1.开启事务
            transactionManager.beginTransaction();
            // 2.执行操作
            List<Account> accounts = accountDao.findAll();
            // 3.提交事务
            transactionManager.commit();
            // 4.返回结果
            return accounts;
        } catch (Exception e){
            // 回滚
            transactionManager.rollback();
            throw new RuntimeException(e);
        } finally {
            // 释放资源
            transactionManager.release();
        }
    }

    @Override
    public Account findAccountById(int id) {
        try {
            // 1.开启事务
            transactionManager.beginTransaction();
            // 2.执行操作
            Account account = accountDao.findAccountById(id);
            // 3.提交事务
            transactionManager.commit();
            // 4.返回结果
            return account;
        } catch (Exception e){
            // 回滚
            transactionManager.rollback();
            throw new RuntimeException(e);
        } finally {
            // 释放资源
            transactionManager.release();
        }
    }

    @Override
    public void saveAccount(Account account) {
        try {
            // 1.开启事务
            transactionManager.beginTransaction();
            // 2.执行操作
            accountDao.saveAccount(account);
            // 3.提交事务
            transactionManager.commit();
        } catch (Exception e){
            // 回滚
            transactionManager.rollback();
            throw new RuntimeException(e);
        } finally {
            // 释放资源
            transactionManager.release();
        }

    }

    @Override
    public void updateAccount(Account account) {
        try {
            // 1.开启事务
            transactionManager.beginTransaction();
            // 2.执行操作
            accountDao.updateAccount(account);
            // 3.提交事务
            transactionManager.commit();
        } catch (Exception e){
            // 回滚
            transactionManager.rollback();
            throw new RuntimeException(e);
        } finally {
            // 释放资源
            transactionManager.release();
        }

    }

    @Override
    public void deleteAccount(int id) {
        try {
            // 1.开启事务
            transactionManager.beginTransaction();
            // 2.执行操作
            accountDao.deleteAccount(id);
            // 3.提交事务
            transactionManager.commit();
        } catch (Exception e){
            // 回滚
            transactionManager.rollback();
            throw new RuntimeException(e);
        } finally {
            // 释放资源
            transactionManager.release();
        }

    }

    @Override
    public void transfer(String sourceName, String targetName, double money) {
        try {
            // 1.开启事务
            transactionManager.beginTransaction();
            // 2.执行操作
                // 1.根据名称查询转出账户
                Account source = accountDao.findAccountByName(sourceName);

                // 2.根据名称查询转入账户
                Account target = accountDao.findAccountByName(targetName);

                // 3.转出账户减钱
                source.setMoney(source.getMoney() - money);
                accountDao.updateAccount(source);

                int i = 1/0;

                // 4.转入账户加钱
                target.setMoney(target.getMoney() + money);
                accountDao.updateAccount(target);
            // 3.提交事务
            transactionManager.commit();
        } catch (Exception e){
            // 回滚
            transactionManager.rollback();
            throw new RuntimeException(e);
        } finally {
            // 释放资源
            transactionManager.release();
        }

    }


}
