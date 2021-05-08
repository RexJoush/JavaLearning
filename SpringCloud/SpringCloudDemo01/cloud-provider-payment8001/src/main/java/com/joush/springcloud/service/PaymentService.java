package com.joush.springcloud.service;

import com.joush.springcloud.entities.Payment;

/**
 * @author Rex Joush
 * @time 2021.04.23 15:02
 */

public interface PaymentService {

    int create(Payment payment);

    Payment getPaymentById(long id);
}
