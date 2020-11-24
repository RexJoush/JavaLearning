package com.joush.service;

/**
 * 账户的业务层接口
 */
public interface AccountService {

    /**
     * 模拟保存账户
     */
    void saveAccount();

    /**
     * 模拟更新账户
     * @param i
     */
    void updateAccount(int i);

    /**
     * 模拟删除账户
     */
    int deleteAccount();
}
