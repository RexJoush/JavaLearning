package com.joush.springcloud.controller;

import com.joush.springcloud.entities.CommonResult;
import com.joush.springcloud.entities.Payment;
import com.joush.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rex Joush
 * @time 2021.04.23 15:05
 */
@RestController
@Slf4j
public class PaymentController {

    private PaymentService paymentService;

    @PostMapping("/payment/create")
    public CommonResult<Integer> create(Payment payment) {
        int i = paymentService.create(payment);
        log.info("结果 " + i);
        if (i > 0) {
            return new CommonResult<>(200, "插入成功", i);
        } else {
            return new CommonResult<Integer>(444, "插入失败");
        }
    }

    @GetMapping("/payment/getPaymentById/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("结果 " + payment);
        if (payment != null) {
            return new CommonResult<>(200, "查询成功", payment);
        } else {
            return new CommonResult<Payment>(444, "查询失败");
        }
    }
}
