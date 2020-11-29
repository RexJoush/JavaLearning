package com.joush.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 事务管理相关的工具类
 * 包含开启事务，提交事务，回滚事务，释放连接
 */
@Component("transactionManager")
@Aspect
public class TransactionManager {

    @Autowired
    private ConnectionUtils connectionUtils;

    @Pointcut("execution(* com.joush.service.impl.*.*(..))")
    private void pt1(){}

    /**
     * 开启事务
     */

    public void beginTransaction(){
        try {
            connectionUtils.getThreadConnection().setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 提交事务
     */

    public void commit(){
        try {
            connectionUtils.getThreadConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 回滚事务
     */

    public void rollback(){
        try {
            connectionUtils.getThreadConnection().rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放连接
     */

    public void release(){
        try {
            connectionUtils.getThreadConnection().close(); // 还回线程池中，但还带着连接，所以需要解绑连接
            connectionUtils.removeConnection(); // 解绑
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 环绕通知实现事务
     * @param proceedingJoinPoint
     * @return
     */
    @Around("pt1()")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint){

        Object returnValue = null;

        try {
            // 1.获取参数
            Object[] args = proceedingJoinPoint.getArgs();

            // 2.开启事务
            this.beginTransaction();

            // 3.执行方法
            returnValue = proceedingJoinPoint.proceed(args);

            // 4.提交事务
            this.commit();

            return returnValue;
        } catch (Throwable e){
            // 5.回滚事务
            this.rollback();
            throw new RuntimeException(e);
        } finally {
            // 释放资源
            this.release();
        }
    }

}
