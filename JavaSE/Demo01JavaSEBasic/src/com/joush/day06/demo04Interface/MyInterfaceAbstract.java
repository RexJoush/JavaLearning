package com.joush.day06.demo04Interface;

/*
    在任何版本中，接口都可以定义抽象方法
        public abstract 返回值类型 方法名称 (参数列表);
    注意事项：
        1.接口当中修饰符必须是固定的两个关键字 public abstract
        2.这两个关键字修饰符可以选择性的省略
 */
public interface MyInterfaceAbstract {

    // 这是一个抽象方法
    public abstract void methodAbs1();

    // 以下都是抽象方法
    public void methodAbs2();
    abstract void methodAbs3();
    void methodAbs4();

}
