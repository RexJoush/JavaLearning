package com.my.day12.demo05ThreadStatus;

/*
    NEW 至今尚未启动的线程处于此状态
    RUNNABLE 正在 java 虚拟机中执行的线程处于这种状态
    BLOCKED 受阻碍并等待某个监视器锁的线程处于这种状态
    WAITING 无限期的等待另一个线程来执行某一特定操作的线程处于这种状态
    TIME_WAITING 等待另一个线程来执行取决于指定等待时间的操作线程处于这种状态
    TERMINATED 已退出的线程处于这种状态

    阻塞状态，具有 CPU 执行资格，等待 CPU 空闲时执行
    休眠状态，放弃 CPU 执行资格，CPU 空闲也不执行

 */
public class Demo01ThreadStatus {
}
