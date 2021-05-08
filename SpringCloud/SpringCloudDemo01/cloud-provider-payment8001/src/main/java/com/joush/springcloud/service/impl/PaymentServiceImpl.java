package com.joush.springcloud.service.impl;

import com.joush.springcloud.dao.PaymentDao;
import com.joush.springcloud.entities.Payment;
import com.joush.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Rex Joush
 * @time 2021.04.23 15:03
 */

@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource
    private PaymentDao paymentDao;


    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(long id) {
        return paymentDao.getPaymentById(id);
    }
}
