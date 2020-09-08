package com.my.day18.demo02ExtendsReference;

/*
    定义子类，
 */
public class Man extends Human {
    // 子类重写父类方法
    @Override
    public void sayHello() {
        System.out.println("Hello Man!");
    }

    // 定义参数传递 Greetable 接口
    public void method(Greetable g){
        g.greet();
    }

    public void show(){
        // 调用 method 方法，方法参数 Greetable 是一个函数式接口，所以可以传递 Lambda
        /*method(()->{
            // 创建父类 Human 对象
            Human h = new Human();
            // 调用父类的 sayHello 方法
            h.sayHello();
        });*/

        // 因为有子父类关系，所以存在 super 关键字，可以直接使用 super 调用父类的成员方法
        method(()-> super.sayHello());

        /*
            使用 super 引用父类的成员方法
                1.super 已经存在
                2.sayHello已经存在
         */
        method(this::sayHello);

    }

    public static void main(String[] args) {
        new Man().show();
    }

}
