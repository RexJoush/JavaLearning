package com.joush.day10.demo04RegisterException;

import java.util.Scanner;

/*
    要求
        模拟注册操作，如果用户名已经存在，则抛出异常提示，用户已被注册
    分析
        1.使用数组保存已经注册过的用户名
        2.用 Scanner 获取用户输入的注册用户
        3.定义一个方法，对用户输入的用户名进行校验
 */
public class Demo01RegisterException {
    static String[] username = {"张三","李四","王五"};

    public static void main(String[] args) /*throws RegisterException*/ {
        Scanner sc = new Scanner(System.in);
        System.out.println("输入注册的用户名：");
        String user = sc.next();
        checkUsername(user);

    }
    public static void checkUsername(String name) /*throws RegisterException*/ {
        for (String s : username){
            if (s.equals(name)){
                // 用户存在
                try {
                    throw new RegisterException("该用户已被注册！");
                } catch (RegisterException e) {
                    e.printStackTrace();
                    return; // 结束方法
                }
            }
        }
        System.out.println("注册成功！");
    }
}
