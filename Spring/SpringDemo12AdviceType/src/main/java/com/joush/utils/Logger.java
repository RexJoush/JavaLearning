package com.joush.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 用于记录日志的工具类，提供了公共代码
 */
public class Logger {


    /**
     * 前置通知
     */
    public void beforePrintLog(){
        System.out.println("前置通知 Logger 类中的 beforePrintLog 方法执行了... ");
    }

    /**
     * 后置通知
     */
    public void afterReturningPrintLog(){
        System.out.println("后置通知 Logger 类中的 afterReturningPrintLog 方法执行了... ");
    }

    /**
     * 异常通知
     */
    public void afterThrowingPrintLog(){
        System.out.println("异常通知 Logger 类中的 afterThrowingPrintLog 方法执行了... ");
    }

    /**
     * 最终通知
     */
    public void afterPrintLog(){
        System.out.println("最终通知 Logger 类中的 afterFinallyPrintLog 方法执行了... ");
    }

    /**
     * 环绕通知
     * 问题
     *     当我们配置了环绕通知后，切入点方法没有执行，通知方法执行了
     * 分析
     *     发现动态代理中的环绕通知有明确的切入点方法调用，而此处没有
     * 解决
     *     Spring 框架提供了一个接口，ProceedingJoinPoint, 该接口有一个方法， process(), 此方法就相当于明确调用切入点方法
     *     该接口可以作为环绕通知的方法参数，在程序执行时，spring 框架会为我们提供该接口的实现类
     * spring 中的环绕通知
     *     它是 spring 为我们提供的一种可以在代码中手动控制增强方法何时执行的方式
     * @return
     */
    public Object aroundPrintLog(ProceedingJoinPoint proceedingJoinPoint){

        Object returnValue = null;

        try {
            System.out.println("最终通知 Logger 类中的 aroundPrintLog 方法执行了... 前置");
            Object[] args = proceedingJoinPoint.getArgs();
            returnValue = proceedingJoinPoint.proceed(args);
            System.out.println("最终通知 Logger 类中的 aroundPrintLog 方法执行了... 后置");
            return returnValue;
        } catch (Throwable throwable) {
            System.out.println("最终通知 Logger 类中的 aroundPrintLog 方法执行了... 异常");
            throw new RuntimeException(throwable);
        } finally {
            System.out.println("最终通知 Logger 类中的 aroundPrintLog 方法执行了... 最终");
        }


    }
}
