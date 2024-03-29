package com.joush.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Rex Joush
 * @time 2021.07.30 16:17
 */
@Component
// 此处指定该接口的 fallback 类为上一步新建的异常处理类
@FeignClient(value = "CLOUD-PROVIDER-HYSTRIX-PAYMENT", fallback = PaymentFallbackService.class)
public interface PaymentHystrixService {

    @GetMapping(value = "/payment/hystrix/ok/{id}")
    String paymentInfoOk(@PathVariable("id") int id);

    @GetMapping(value = "/payment/hystrix/timeout/{id}")
    String paymentInfoTimeout(@PathVariable("id") int id);

}
