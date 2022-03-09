package com.joush.demo11readwrite;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Rex Joush
 * @time 2022.03.09
 */

// 资源类
class MyCache {

    // 创建 map 集合
    private volatile Map<String, Object> map = new HashMap<>();

    // 创建读写锁对象
    private ReadWriteLock rwLock = new ReentrantReadWriteLock();

    // 放数据
    public void put(String key, Object value) {
        // 加上写锁
        rwLock.writeLock().lock();
        System.out.println(Thread.currentThread().getName() + " 正在写");

        // 暂停一会
        try {
            TimeUnit.SECONDS.sleep(1);
            // 放数据
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + " 写完了 " + key);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 解锁
            rwLock.writeLock().unlock();
        }
    }

    // 取数据
    public Object get(String key) {

        // 添加读锁
        rwLock.readLock().lock();
        Object result = null;
        System.out.println(Thread.currentThread().getName() + " 正在读取数据");

        // 暂停一会
        try {
            TimeUnit.SECONDS.sleep(1);
            // 读数据
            result = map.get(key);
            System.out.println(Thread.currentThread().getName() + " 取完了 " + key);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 释放锁
            rwLock.readLock().unlock();
        }

        return result;
    }
}
public class Demo01ReadWrite {

    public static void main(String[] args) throws InterruptedException {
        MyCache cache = new MyCache();

        // 创建线程写数据
        for (int i = 1; i <= 5; i++) {
            final int num = i;
            new Thread(() -> {
                cache.put(num + "", num + "");
            }, String.valueOf(i)).start();
        }

        TimeUnit.SECONDS.sleep(1);

        // 创建线程读数据
        for (int i = 1; i <= 5; i++) {
            final int num = i;
            new Thread(() -> {
                cache.get(num + "");
            }, String.valueOf(i)).start();
        }
    }

}
