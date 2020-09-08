package com.my.day10.demo03MyException;

/*
    子父类异常
        - 如果父类抛出了多个异常，子类在重写父类方法时，抛出和父类相同的异常或者是父类异常的子类，或者不抛出异常
        - 父类方法没有抛出异常，子类重写父类方法时也不可抛出异常，此时子类产生异常，只能捕获，不能抛出
    注意：
        父类异常什么样，子类就什么样
 */
public class Fu {
    public void show01() throws NullPointerException,ClassCastException{}
    public void show02() throws IndexOutOfBoundsException{}
    public void show03() throws IndexOutOfBoundsException{}
    public void show04() {}
}
class Zi extends Fu{
    // 子类在重写父类方法时，抛出和父类相同的异常
    public void show01() throws NullPointerException,ClassCastException{}

    // 子类在重写父类方法时，抛出父类异常的子类
    public void show02() throws ArrayIndexOutOfBoundsException{}

    // 子类在重写父类方法时，不抛出异常
    public void show03() {}

    // 子类重写父类方法，不能抛出异常
   // public void show04() {}

    // 父类方法没有抛出异常，子类重写父类方法时也不可抛出异常
    public void show04() /*throws Exception*/ {
        try {
            throw new Exception("aaa");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
