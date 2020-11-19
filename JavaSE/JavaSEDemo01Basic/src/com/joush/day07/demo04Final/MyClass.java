package com.joush.day07.demo04Final;

/*
    当 final 关键字用来修饰一个类的时候，格式：
    public final class 类名称 {
        // 。。。
    }
    含义：当前类不能有任何的子类
    注意：一个类如果是 final 的，那么其中所有的成员方法都无法重写，所以不能有抽象方法
 */
public final class MyClass {
	public void method(){
		System.out.println("方法执行");
	}
}
