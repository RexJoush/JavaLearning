package com.joush.controller;

import com.joush.entities.CommonResult;
import com.joush.entities.Payment;
import com.joush.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author Rex Joush
 * @time 2021.04.23 15:05
 */
@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping("/payment/create")
    public CommonResult<Integer> create(@RequestBody Payment payment) {
        int i = paymentService.create(payment);
        log.info("结果 " + i);
        if (i > 0) {
            return new CommonResult<>(200, "插入成功, serverPort: " + serverPort, i);
        } else {
            return new CommonResult<Integer>(444, "插入失败");
        }
    }

    @GetMapping("/payment/getPaymentById/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") int id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("结果 " + payment);
        if (payment != null) {
            return new CommonResult<>(200, "查询成功, serverPort: " + serverPort, payment);
        } else {
            return new CommonResult<Payment>(444, "查询失败");
        }
    }

    @GetMapping("/payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }

    @GetMapping("/payment/feign/timeout")
    public String paymentFeignTimeout(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return serverPort;
    }
}
