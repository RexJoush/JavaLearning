package com.joush.service;

import com.joush.domain.Account;
import java.util.List;

/**
 * 账户的业务层接口
 */
public interface AccountService {

    /**
     * 查询所有
     * @return
     */
    List<Account> findAll();

    /**
     * 查询一个
     * @param id
     * @return
     */
    Account findAccountById(int id);

    /**
     * 保存
     * @param account
     */
    void saveAccount(Account account);

    /**
     * 更新
     * @param account
     */
    void updateAccount(Account account);

    /**
     * 删除
     * @param id
     */
    void deleteAccount(int id);
}
