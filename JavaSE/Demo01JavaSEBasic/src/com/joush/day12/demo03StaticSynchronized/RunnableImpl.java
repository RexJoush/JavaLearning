package com.joush.day12.demo03StaticSynchronized;

/*

    卖票案例出现了线程安全问题，卖出了不存在的票和重复的票

    解决线程安全问题的第二种方法，使用同步方法
    使用步骤
        1.把访问了共享数据的代码抽取出来，放到一个方法中
        2.在方法上添加 synchronize 修饰符
    格式
        修饰符 synchronize 返回值类型 方法名 (参数列表){
            //
        }
 */
public class RunnableImpl implements Runnable {

    // 定义多个线程共享的票源
    private static int ticket = 100;

    @Override
    public void run() {
        // 让买票重复进行
        while (true){
            payTicketStatic();
        }
    }
    /*
        静态同步方法
        锁对象不是 this，因为静态方法优先于 this
        所以锁对象是本类的 class 属性的 class 文件对象
     */
    public static /*synchronized*/ void payTicketStatic(){
        synchronized (RunnableImpl.class){
            if (ticket > 0){
                // 提高安全问题，睡眠 10ms
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 票存在，卖票
                System.out.println(Thread.currentThread().getName() + "-->在卖第" + ticket + "张票");
                ticket--;
            }
        }
    }

    /*
        定义一个同步方法
        同步方法也会吧方法内部代码锁住，只让一个线程运行
        同步方法的锁对象是 new RunnableImpl()
        也就是 this
     */
    /*
    public synchronized void payTicket(){
        if (ticket > 0){
            // 票存在，买票
            System.out.println(Thread.currentThread().getName() + "-->在卖第" + ticket + "张票");
            ticket--;
        }
    }

     */
}
