package com.joush.service;

import com.joush.config.FeignConfig;
import com.joush.entities.CommonResult;
import com.joush.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Rex Joush
 * @time 2021.07.22 20:26
 */
@Component
// 此处加上 configuration，值是配置类的字节码文件, 否则日志不生效
@FeignClient(value = "CLOUD-PAYMENT-SERVICE", configuration = FeignConfig.class)
public interface PaymentFeignService {

    // 此处 value = '' 必须写，不然会找不到方法
    @GetMapping(value = "/payment/getPaymentById/{id}")
    CommonResult<Payment> getPaymentById(@PathVariable("id") int id);

    @GetMapping(value = "/payment/feign/timeout")
    String paymentFeignTimeout();
}
