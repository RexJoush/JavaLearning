package com.joush.case1.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyTest {

    public static void main(String[] args) {

        // 1.创建对象
        Lenovo lenovo = new Lenovo();

        // 2.动态代理增强 Lenovo 对象

        /*
            三个参数
                1.类加载器， 真实对象.getClass().getClassLoader()
                2.接口数组， 真实对象.getClass().getInterfaces()
                3.处理器， new InvocationHandle()
         */
        SaleComputer proxyLenovo = (SaleComputer) Proxy.newProxyInstance(lenovo.getClass().getClassLoader(), lenovo.getClass().getInterfaces(), new InvocationHandler() {

            /*
             代理逻辑编写的方法，代理对象调用的所有方法都会触发该方法执行

             invoke 函数的三个参数
                1.proxy, 代理对象
                2.method, 代理对象调用的方法，被封装成的对象
                3.args, 方法执行时传递的实际参数

             */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                System.out.println("invoke executed...");

                // 判断是否是sale方法
                if (method.getName().equals("sale")){
                    // 1.增强参数
                    double money = (double) args[0];
                    money = money * 0.85;

                    // 3.增强方法体
                    System.out.println("专车接你");
                    // 使用真实对象调用方法
                    String obj = (String) method.invoke(lenovo, money);
                    System.out.println("免费送货");

                    // 2.增强返回值
                    return obj + "_鼠标垫";


                } else {
                    Object obj = method.invoke(lenovo, args);

                    return obj;
                }


            }
        });

        // 2.调用方法
        String computer = proxyLenovo.sale(8000);
        System.out.println(computer);

//        proxyLenovo.show();

    }

}
