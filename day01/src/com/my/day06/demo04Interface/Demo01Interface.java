package com.my.day06.demo04Interface;

/*
    1.接口就是多个类的公共规范
    2.接口是一个引用数据类型，最重要的是抽象方法
    3.定义一个接口的格式
        public interface Name{
            // 内容
        }
    4.换成 interface 以后，编译生成的字节码文件依然是 .java ---> .class

    如果是 jdk7，可以包含
        - 常量
        - 抽象方法
    如果是 jdk8+，可以包含
        - 默认方法
        - 静态方法
    如果是 jdk9+，可以包含
        - 私有方法
    5.接口使用步骤
        - 接口不能直接使用，必须有一个实现类，来实现该接口
        格式：
            public class 实现类名称 implements 接口名称 {

            }
        - 接口的实现类必须重写接口的所有抽象方法
        - 创建实现类的对象，使用接口
    6.注意事项，如果实现类没有重写所有抽象方法，那么这个实现类必须是抽象类
 */
public class Demo01Interface {
    public static void main(String[] args) {
        MyInterfaceAbstractImpl impl = new MyInterfaceAbstractImpl();
        impl.methodAbs1();
        impl.methodAbs2();
    }
}
