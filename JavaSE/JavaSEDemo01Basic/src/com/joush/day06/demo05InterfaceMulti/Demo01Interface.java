package com.joush.day06.demo05InterfaceMulti;

/*
    使用接口时应当注意
        1.接口是没有静态代码块或者构造方法的
        2.一个类的直接父类有且只有一个，但可以同时实现多个接口
        格式：
            public class MyInterfaceImpl implements MyInterfaceA, MyInterfaceB {
                // 重写所有抽象方法
            }
        3.如果实现类所实现的多个接口中存在重名的抽象方法，那么只需要重写一次即可
        4.如果实现类没有重写所有接口中的所有抽象方法，那么实现类必须是抽象类
        5.如果实现类所实现的多个接口中，存在重复的默认方法，那么实现类一定要对冲突的方法进行重写
        6.一个类如果直接父类的方法和接口中的方法冲突，那么优先使用父类的方法
 */
public class Demo01Interface implements MyInterfaceA, MyInterfaceB {
    @Override
    public void methodA() {
        System.out.println("覆盖重写了A方法");
    }

    @Override
    public void methodB() {
        System.out.println("覆盖重写了B方法");
    }

    @Override
    public void methodAbs() {
        System.out.println("覆盖重写了AB接口共有的抽象方法");
    }

    // 必须对冲突的方法进行重写
    @Override
    public void methodDefault() {
        System.out.println("解决冲突");
    }
}
