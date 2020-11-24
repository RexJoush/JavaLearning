package com.joush.factory;

import com.joush.service.AccountService;
import com.joush.utils.TransactionManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 用于创建 Service 的代理对象工厂
 */
public class BeanFactory {
    private AccountService accountService;
    // 事务控制的对象
    private TransactionManager transactionManager;

    public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void setAccountService(AccountService accountService){
        this.accountService = accountService;
    }

    public AccountService getAccountService(){
        return (AccountService) Proxy.newProxyInstance(accountService.getClass().getClassLoader(),
                accountService.getClass().getInterfaces(),
                new InvocationHandler() {
                    /**
                     * 添加事务支持
                     * @param proxy
                     * @param method
                     * @param args
                     * @return
                     * @throws Throwable
                     */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                if("test".equals(method.getName())){
                    return method.invoke(accountService,args);
                }

                Object returnValue = null;

                try {
                    // 1.开启事务
                    transactionManager.beginTransaction();
                    // 2.执行操作
                    returnValue = method.invoke(accountService, args);
                    // 3.提交事务
                    transactionManager.commit();
                    // 4.返回结果
                    return returnValue;
                } catch (Exception e){
                    // 回滚
                    transactionManager.rollback();
                    throw new RuntimeException(e);
                } finally {
                    // 释放资源
                    transactionManager.release();
                }
            }
        });
    }
}
