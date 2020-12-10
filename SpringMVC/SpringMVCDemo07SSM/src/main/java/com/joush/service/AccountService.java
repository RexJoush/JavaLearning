package com.joush.service;

import com.joush.domain.Account;

import java.util.List;

/**
 * 业务层接口
 */
public interface AccountService {
    /**
     * 查询所有
     * @return
     */
    List<Account> findAll();

    /**
     * 保存账户
     * @param account
     */
    void saveAccount(Account account);
}
