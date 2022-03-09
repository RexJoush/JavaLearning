package com.joush.demo15async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author Rex Joush
 * @time 2022.03.09
 */

// CompletableFuture 异步调用
public class Demo01Async {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 异步调用没有返回值
        CompletableFuture<Void> completableFuture1 = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " completableFuture1");
        });
        // 结束调用
        completableFuture1.get();

        // 异步调用有返回值
        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " completableFuture2");
            // 模拟异常
            // int i = 1 / 0;
            return 10;
        });
        // 判断是否完成
        completableFuture2.whenComplete((t, u) -> {
            System.out.println(t); // t 为方法的返回值
            System.out.println(u); // u 为异常信息
        }).get();

    }

}
