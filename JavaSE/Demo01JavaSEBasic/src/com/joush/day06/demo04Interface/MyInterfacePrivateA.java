package com.joush.day06.demo04Interface;
/*
    问题：
        需要抽取一个公共方法，用来解决两个默认方法之间重复代码的问题，但是公共方法不应该让实现类使用
        即，应该是私有
    解决，从 java9 开始，接口允许定义私有方法
        1.普通私有方法，解决多个默认方法之间重复代码的问题
            格式：
                private 返回值类型 方法名称 (参数列表){
                    方法体
                }
        2.静态私有方法，解决多个静态方法之间重复代码的问题
            格式：
                private static 返回值类型 方法名称 (参数列表){
                    方法体
                }
 */
public interface MyInterfacePrivateA {

    public default void methodDefault1(){
        System.out.println("默认方法1");
        methodCommon();
    }
    public default void methodDefault2(){
        System.out.println("默认方法2");
        methodCommon();
    }

    // jdk9 允许定义私有
    private void methodCommon(){
        System.out.println("AAA");
        System.out.println("BBB");
        System.out.println("CCC");
    }
}
