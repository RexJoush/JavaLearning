package com.joush.springcloud.dao;

import com.joush.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Rex Joush
 * @time 2021.04.23 14:51
 */
@Mapper
public interface PaymentDao {

    int create(Payment payment);

    Payment getPaymentById(@Param("id") long id);


}
