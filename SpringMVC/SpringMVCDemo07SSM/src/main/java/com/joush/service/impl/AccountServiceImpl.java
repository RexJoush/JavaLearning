package com.joush.service.impl;

import com.joush.domain.Account;
import com.joush.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务层实现类
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {
    @Override
    public List<Account> findAll() {
        System.out.println("业务层：findAll executed!");
        return null;
    }

    @Override
    public void saveAccount(Account account) {
        System.out.println("业务层：saveAccount executed!");
    }
}
