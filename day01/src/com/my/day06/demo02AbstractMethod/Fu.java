package com.my.day06.demo02AbstractMethod;
/*
    1.一个抽象类不一定含有抽象方法，只要保证抽象方法所在的类是抽象类即可
    2.没有抽象方法的抽象类美也不能直接实例对象
    3.抽象类的子类必须重写所有父类的抽象方法，否则子类必须也得是抽象类
 */
public abstract class Fu {
    public abstract void eat();
}
