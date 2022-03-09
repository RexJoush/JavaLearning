package com.joush.demo11readwrite;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Rex Joush
 * @time 2022.03.09
 */
// 锁降级
public class Demo02DownLock {

    public static void main(String[] args) {
        // 可重入读写锁对象
        ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

        // 读锁
        ReentrantReadWriteLock.ReadLock readLock = rwLock.readLock();
        // 写锁
        ReentrantReadWriteLock.WriteLock writeLock = rwLock.writeLock();

        // 锁降级
        // 1.获取写锁
        writeLock.lock();
        System.out.println("joush");
        // 2.获取读锁
        readLock.lock();
        System.out.println("read");

        // 3.释放写锁
        writeLock.unlock();
        // 4.释放读锁
        readLock.unlock();
    }
}
