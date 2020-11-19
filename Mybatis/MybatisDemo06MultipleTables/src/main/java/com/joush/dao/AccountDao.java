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
     * 查询所有，获取
     * @return
     */
//    List<> findAllAccount();

}
