package com.my.day07.demo08UnnameClass;

/*
    如果接口的实现类，或者父类的子类只用唯一一次，那么这种情况，就可以省略该类的定义
    使用【匿名内部类】
    匿名内部类的定义格式:
        接口名称 对象名 = new 接口名称(){
            // 重写所有方法
        }

    注意：
    1.匿名内部类在创建对象的时候只能使用一次，否则就要定义一个实现类
    2.
 */
public class DemoMain {
    public static void main(String[] args) {
//        MyInterface impl = new MyInterfaceImpl();
//        impl.method();

        //MyInterface myInterface = new MyInterface(); // 错误写法
        // 使用匿名内部类
        MyInterface obj = new MyInterface() {
            @Override
            public void method1() {
                System.out.println("11111");
            }

            @Override
            public void method2() {
                System.out.println("22222");
            }

        };

        obj.method1();
        obj.method2();

    }
}
