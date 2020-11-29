package com.joush.service;

import com.joush.domain.Account;

public interface AccountService {

    /**
     * 根据 id 查询用户信息
     * @param id 用户 id
     * @return
     */
    Account findAccountById(int id);

    /**
     * 转账操作
     * @param sourceName 转出账户
     * @param targetName 转入账户
     * @param money 转账金额
     */
    void transfer(String sourceName, String targetName, double money);

}
