package com.joush.cglib;

import com.joush.proxy.IProducer;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 模拟一个消费者
 */
public class Client {

    public static void main(String[] args) {

        Producer producer = new Producer();

        /*
            动态代理
                特点：字节码随用随加载
                作用：不修改源码的基础上对方法增强
                分类：基于接口的，基于子类的
            基于子类的动态代理
                涉及的类，Enhancer
                提供者：第三方 cglib 库
            如何创建代理对象
                使用 Enhancer 类中的 create 方法
            创建代理对象的要求
                被代理类不能是最终类
            方法签名
            public static Object create(Class type, Callback callback);
                参数：
                    Class type, 字节码，被代理对象的字节码
                    Callback callback, 用于提供增强的代码，写如何代理，一般写一个该接口的实现类，通常写的匿名内部类（非必须）
                                       此接口的实现类都是谁用谁写，我们一般写的是该接口的子接口实现类，MethodInterceptor

         */
        Producer cglibProducer = (Producer) Enhancer.create(producer.getClass(), new MethodInterceptor() {
            /**
             * 执行被代理对象的任何方法都会经过该方法
             * @param o 代理对象的引用
             * @param method 当前执行的方法
             * @param objects 当前执行方法所需的参数
             *  以上三个参数和基于接口动态代理中 invoke 方法参数一样的
             * @param methodProxy 当前执行方法的代理对象
             * @return 与执行方法的返回值相同
             * @throws Throwable
             */
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                /*
                    提供增强的代码
                 */
                Object returnValue = null;
                // 获取方法的执行参数
                double money = (double) objects[0];

                // 判断当前方法是不是 销售
                if ("saleProduct".equals(method.getName())) {
                    returnValue = method.invoke(producer, money * 0.8);
                }

                return returnValue;
            }
        });


        cglibProducer.saleProduct(12000d);
    }

}
