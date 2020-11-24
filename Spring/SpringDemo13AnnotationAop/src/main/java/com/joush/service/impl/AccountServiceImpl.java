package com.joush.service.impl;


import com.joush.service.AccountService;
import org.springframework.stereotype.Service;

@Service("accountServiceImpl")
public class AccountServiceImpl implements AccountService {
    @Override
    public void saveAccount() {
        System.out.println("执行的保存");
    }

    @Override
    public void updateAccount(int i) {
        System.out.println("执行了更新" + i);
    }

    @Override
    public int deleteAccount() {
        System.out.println("执行了删除");
        return 0;
    }
}
