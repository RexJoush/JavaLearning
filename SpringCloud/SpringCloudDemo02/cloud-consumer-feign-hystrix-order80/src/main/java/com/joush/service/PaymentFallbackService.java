package com.joush.service;

import org.springframework.stereotype.Component;

/**
 * @author Rex Joush
 * @time 2021.08.03 14:33
 */
@Component
public class PaymentFallbackService implements PaymentHystrixService{
    @Override
    public String paymentInfoOk(int id) {
        return "PaymentFallbackService fall back";
    }

    @Override
    public String paymentInfoTimeout(int id) {
        return null;
    }
}
