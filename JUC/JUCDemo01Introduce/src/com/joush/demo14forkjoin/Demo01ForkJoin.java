package com.joush.demo14forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author Rex Joush
 * @time 2022.03.09
 */

// 计算 1 + 2 + 3 + 4 + ... + 100
class MyTask extends RecursiveTask<Integer> {

    // 拆分的差值，即最长计算不超过10个数
    private static final int value = 10;

    // 开始值，结束值
    private int begin;
    private int end;

    // 返回结果
    private int result;

    // 创建有参数构造函数
    public MyTask(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    // 实现拆分和合并的逻辑
    @Override
    protected Integer compute() {
        // 判断，如果相加的两个数之差是否大于10
        if ((end - begin) <= 10) {
            // 计算
            for (int i = begin; i <= end; i++) {
                result += i;
            }
        } else {
            // 继续拆分
            // 获取数据中间值
            int mid = (begin + end) / 2;
            // 拆分左边
            MyTask task01 = new MyTask(begin, mid);

            // 拆分右边
            MyTask task02 = new MyTask(mid + 1, end);

            // 拆分
            task01.fork();
            task02.fork();

            // 合并结果
            result = task01.join() + task02.join();
        }
        return result;
    }
}
public class Demo01ForkJoin {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 创建 task 对象
        MyTask task = new MyTask(1, 100);

        // 创建分支合并对象
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        // 提交任务
        ForkJoinTask<Integer> submit = forkJoinPool.submit(task);
        Integer result = submit.get();
        System.out.println(result);

        // 关闭
        forkJoinPool.shutdown();
    }

}
