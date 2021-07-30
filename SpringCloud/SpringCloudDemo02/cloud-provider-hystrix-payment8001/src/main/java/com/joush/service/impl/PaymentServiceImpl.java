package com.joush.service.impl;

import com.joush.service.PaymentService;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

/**
 * @author Rex Joush
 * @time 2021.07.23 16:34
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    // 正常访问的方法
    @Override
    public String paymentInfoOk(int id){
        return "线程池: " + Thread.currentThread().getName() + "paymentInfoOK\n" +
                "id: " + id;
    }

    @Override
    public String paymentInfoTimeout(int id){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "线程池: " + Thread.currentThread().getName() + "paymentInfoTimeout\n" +
                "id: " + id;
    }

}
