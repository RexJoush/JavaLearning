package com.joush.proxy;

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
            基于接口的动态代理
                涉及的类，Proxy
                提供者：JDK
            如何创建代理对象
                使用 Proxy 类中的 newProxyInstance 方法
            创建代理对象的要求
                被代理类最少实现一个接口，如果没有，则不能使用
            方法签名
            public static Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h);
                参数：
                    ClassLoader loader, 类加载器，用于加载代理对象的字节码，和被代理对象使用同样的类加载器，固定写法
                    Class<?>[] interfaces, 字节码数组，用于代理对象和被代理对象有相同的方法
                    InvocationHandler h, 用于提供增强的代码，写如何代理，一般写一个该接口的实现类，通常写的匿名内部类（非必须）

         */


        IProducer proxyProducer = (IProducer) Proxy.newProxyInstance(producer.getClass().getClassLoader(),
                producer.getClass().getInterfaces(),
                new InvocationHandler() {
                    /**
                     * 执行被代理对象的任何接口方法，都会经过该方法
                     * @param proxy 代理对象的引用
                     * @param method 当前执行的方法
                     * @param args 当前执行方法所需的参数
                     * @return 和被代理对象方法有相同的返回值
                     * @throws Throwable
                     */
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        /*
                            提供增强的代码
                         */
                        Object returnValue = null;
                        // 获取方法的执行参数
                        double money = (double) args[0];

                        // 判断当前方法是不是 销售
                        if ("saleProduct".equals(method.getName())){
                            returnValue = method.invoke(producer, money * 0.8);
                        }

                        return returnValue;

                    }
                });
        proxyProducer.saleProduct(10000d);
    }

}
