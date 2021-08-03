package com.joush.service.impl;

import com.joush.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
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

}
