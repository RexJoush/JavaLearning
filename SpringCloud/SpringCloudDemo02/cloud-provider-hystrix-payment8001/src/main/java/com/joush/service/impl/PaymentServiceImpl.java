package com.joush.service.impl;

import cn.hutool.core.util.IdUtil;
import com.joush.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

/**
 * @author Rex Joush
 * @time 2021.07.23 16:34
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    // 正常访问的方法
    @Override
    public String paymentInfoOk(int id){
        return "线程池: " + Thread.currentThread().getName() + "paymentInfoOK\n" +
                "id: " + id;
    }

    // 此处指定超时处理方法，指定超时时间
    @HystrixCommand(fallbackMethod = "paymentInfoTimeoutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    @Override
    public String paymentInfoTimeout(int id){
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "线程池: " + Thread.currentThread().getName() + "paymentInfoTimeout\n" +
                "id: " + id;
    }

    /**
     * 超时解决办法
     * @param id
     * @return
     */
    public String paymentInfoTimeoutHandler(int id){

        return "线程池: " + Thread.currentThread().getName() + "paymentInfoTimeoutHandler\n" +
                "id: " + id + ", 超时处理, 8001 端";
    }




    /*
        服务熔断部分
     */
    @HystrixCommand(fallbackMethod = "paymentCircuitBreakerFallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),   // 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),  // 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),   // 时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),   // 请求失败达到多少后执行熔断，按照百分比计算，超过 60%
    })
    public String paymentCircuitBreaker(@PathVariable("id") int id){
        if (id < 0) {
            throw new RuntimeException("id 不能为负数");
        }

        String serialNumber = IdUtil.simpleUUID();

        return Thread.currentThread().getName() + " 调用成功，流水号: " + serialNumber;
    }

    public String paymentCircuitBreakerFallback(@PathVariable("id") int id){
        return "id 不能为负数，请稍后再试。 id: " + id;
    }

}
