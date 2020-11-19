package com.joush.dao;

import com.joush.domain.Account;

import java.util.List;

public interface AccountDao {
    /**
     * 查询所有,同时获取当前账户的用户信息
     * @return
     */
    List<Account> findAll();

    /**
     * 根据用户 id 查询用户信息
     * @param uid
     * @return
     */
    List<Account> finAccountByUid(int uid);

}
