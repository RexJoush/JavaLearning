package com.joush.day11.demo04Runnable;

/*
    创建多线程的第二种方法，实现 Runnable 接口
    java.long.Runnable 是由那些打算通过某一线程执行其实例的类来实现，类必须定义一个称为 run 的无参方法
    java.long.Thread 类的构造方法，可以传递 Runnable 实现类的对象
        Thread(Runnable target); 分配新的 Thread对象
        Thread(Runnable target, String name); 分配新的 Thread对象

    实现 Runnable 接口创建多线程程序的好处
        1.避免了单继承的局限性
            一个类智能继承一个类，类继承了 Thread 类就不能继承其他类，实现 Runnable 接口，还可以继承其他的类，实现其他的接口
        2.增强了程序的扩展性，降低了程序的耦合性
            实现 Runnable 接口的方式，把设置线程任务和开启新线程进行了分离，实现类中重写了 run 方法，用来设置线程任务
            创建 Thread 类对象，调用 start 方法，用来开启新线程

 */
public class Demo01Runnable{
    public static void main(String[] args) {
        RunnableImpl run = new RunnableImpl();
        Thread thread = new Thread(run);

        thread.start();
        for (int i = 0; i < 20; i++) {
            System.out.println(Thread.currentThread().getName() + "->" + i);
        }

    }
}
