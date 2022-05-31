package com.joush.service;

import com.joush.bean.UserAddress;

import java.util.List;

/**
 * @author Rex Joush
 * @time 2022.05.02
 */
public interface OrderService {

    List<UserAddress> initOrder(String userId);
}
