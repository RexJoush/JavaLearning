package com.my.day08.demo02Generic;

/*
    定义含有泛型的方法，定义在方法的修饰符和返回值类型之间
    格式
         public <泛型> 返回值类型 方法名 (参数列表<泛型>){
            // 方法体
         }
 */
public class GenericMethod {

    // 定义含有泛型的普通方法
    public <E> void method01 (E e){
        System.out.println(e);
    }
    // 定义一个含有泛型的静态方法
    public static <S> void method02(S s){
        System.out.println(s);
    }

}
