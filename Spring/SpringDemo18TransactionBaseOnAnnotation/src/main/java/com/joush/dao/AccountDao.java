package com.joush.dao;

import com.joush.domain.Account;

/**
 * 账户的持久层接口
 */
public interface AccountDao {

    /**
     * 根据 id 查询账户
     * @param id
     * @return
     */
    Account findAccountById(int id);

    /**
     * 根据名称查询账户
     * @param name
     * @return
     */
    Account findAccountByName(String name);

    /**
     * 更新用户
     * @param account
     */
    void updateAccount(Account account);

}
