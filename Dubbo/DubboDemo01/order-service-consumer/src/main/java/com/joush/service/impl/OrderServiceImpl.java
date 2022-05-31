package com.joush.service.impl;

import com.joush.bean.UserAddress;
import com.joush.service.OrderService;
import com.joush.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Rex Joush
 * @time 2022.05.02
 */

@Service
public class OrderServiceImpl implements OrderService {

    // 此处就可以注入了
    @Autowired
    UserService userService;

    @Override
    public void initOrder(String userId) {
        System.out.println("userId: " + userId);
        // 1.查询用户的收获地址
        List<UserAddress> userAddressList = userService.getUserAddressList(userId);
        System.out.println(userAddressList);
    }
}
