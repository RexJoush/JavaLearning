package com.joush.service;

import com.joush.entities.Payment;

/**
 * @author Rex Joush
 * @time 2021.04.23 15:02
 */

public interface PaymentService {

    int create(Payment payment);

    Payment getPaymentById(int id);
}
