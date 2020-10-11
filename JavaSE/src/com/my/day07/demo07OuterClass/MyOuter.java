package com.my.day07.demo07OuterClass;

/*
    局部内部类，如果希望访问所在方法的局部变量，那么这个局部变量必须是 有效 final 的
    从 java8 开始，只要局部变量事实不变，那么 final 关键字可以省略
 */
public class MyOuter {
    public static void main(String[] args) {
        int num = 10; // 所在方法的局部变量

        class MyInner {
            public void methodInner(){
                System.out.println(num);
            }
        }

    }
}
