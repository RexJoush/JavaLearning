package com.joush.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Rex Joush
 * @time 2021.07.22 14:14
 */
@Component
public class MyLB implements LoadBalancer{

    // 一个原子数字类
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    // 自旋锁实现自增加 1
    public final int getAndIncrement(){
        int current;
        int next;

        do {
            current = this.atomicInteger.get();
            next = current >= 2147483647 ? 0 : current + 1;
        } while (!this.atomicInteger.compareAndSet(current, next));

        System.out.println("times: " + next);
        return next;
    }

    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances) {
        // 获取负载均衡的值
        int index = getAndIncrement() % serviceInstances.size();

        // 返回进程内负载均衡
        return serviceInstances.get(index);
    }
}
