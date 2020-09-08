package com.my.day13.demo01ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
    线程池，jdk1.5之后提供的
    java.util.concurrent.Executors 线程池的工厂类，用来生产线程池
    Executors 类中静态方法
        static ExecutorsService newFixedThreadPool(int nThreads) 创建一个可重用固定线程数的线程池
        参数 int nThreads 创建线程池中包含的线程数量
        返回值 ExecutorsService 接口，返回 ExecutorsService接口的实现类，可以用 ExecutorsService 接口接收
    java.util.concurrent.ExecutorsService 线程池接口
        用来从线程池中获取线程，调用 start 方法，执行线程任务
            submit(Runnable task) 提交一个 Runnable 任务用于执行
        关闭/销毁线程池的方法
            void shutdown()
    线程池的使用步骤
        1.使用线程池的工厂类 Executors 里面提供的静态方法 newFixedThreadPool 生产一个指定线程数量的线程池
        2.创建一个类，实现 Runnable 接口，重写 run 方法，设置线程任务
        3.调用 ExecutorsService 中的方法 submit ，传递线程任务(实现类)，开启线程，执行 run 方法
        4.调用 ExecutorsService 中的方法，shutdown 销毁，（不建议执行）
 */
public class Demo01ThreadPool {
    public static void main(String[] args) {
        // 1.使用线程池的工厂类 Executors 里面提供的静态方法 newFixedThreadPool 生产一个指定线程数量的线程池
        ExecutorService es = Executors.newFixedThreadPool(2);

        // 3.调用 ExecutorsService 中的方法 submit ，传递线程任务(实现类)，开启线程，执行 run 方法
        es.submit(new RunnableImpl()); // pool-1-thread-1创建了一个新的线程
        es.submit(new RunnableImpl()); // pool-1-thread-2创建了一个新的线程

        // 线程池会一直开启，使用完了线程，会自动把线程池归还给线程池，线程可以继续使用
        es.submit(new RunnableImpl()); // pool-1-thread-1创建了一个新的线程

        // 4.销毁线程池
        es.shutdown();

        es.submit(new RunnableImpl()); // 抛出异常，线程池没有了，就不能获取线程了
    }
}
