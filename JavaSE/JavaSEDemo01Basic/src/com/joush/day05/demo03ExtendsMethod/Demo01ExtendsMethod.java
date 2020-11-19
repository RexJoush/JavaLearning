package com.joush.day05.demo03ExtendsMethod;

/*
    在父子类继承关系中，创建子类对象，访问成员方法规则
        创建的对象是谁，就优先用谁，没有则向上找
    注意事项
        无论是成员方法还是成员变量，如果没有都是向上找

    重写 (Override)
        在继承关系中，方法的名称一样，参数列表也一样
    重载 (Overload)
        方法名称一样，参数列表不一样

    方法的覆盖重写特点，创建的是子类对象，就优先用子类方法
 */
public class Demo01ExtendsMethod {
    public static void main(String[] args) {
        Zi zi = new Zi();
        zi.method(); // 子类重名方法执行
    }
}
