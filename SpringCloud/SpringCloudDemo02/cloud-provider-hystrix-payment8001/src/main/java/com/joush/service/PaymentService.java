package com.joush.service;

import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Rex Joush
 * @time 2021.07.23 16:34
 */

public interface PaymentService {

    String paymentInfoOk(int id);

    String paymentInfoTimeout(int id);

    String paymentInfoTimeoutHandler(int id);

    String paymentCircuitBreaker(@PathVariable("id") int id);

}
