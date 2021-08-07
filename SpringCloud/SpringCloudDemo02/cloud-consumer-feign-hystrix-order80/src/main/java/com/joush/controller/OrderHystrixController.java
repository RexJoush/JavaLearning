package com.joush.controller;

import com.joush.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Rex Joush
 * @time 2021.07.30 16:24
 */
@RestController
// @DefaultProperties(defaultFallback = "paymentGlobalFallback")
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentInfoOk(@PathVariable("id") int id){
        return paymentHystrixService.paymentInfoOk(id);
    }

    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    @HystrixCommand
//    @HystrixCommand(fallbackMethod = "paymentInfoTimeoutHandler", commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
//    })
    public String paymentInfoTimeout(@PathVariable("id") int id){
        return paymentHystrixService.paymentInfoTimeout(id);
    }

    public String paymentInfoTimeoutHandler(int id){

        return "线程池: " + Thread.currentThread().getName() + "paymentInfoTimeoutHandler\n" +
                "id: " + id + ", 超时处理, 80 端";
    }


    // 全局 fallback 方法
    public String paymentGlobalFallback(){
        return "Global 异常处理信息，请稍后再试";
    }


}
