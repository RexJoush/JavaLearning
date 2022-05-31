package com.joush.service.impl;

import com.joush.bean.UserAddress;
import com.joush.service.OrderService;
import com.joush.service.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
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
    @DubboReference
    UserService userService;

    @Override
    public List<UserAddress> initOrder(String userId) {
        System.out.println("userId: " + userId);
        // 1.查询用户的收获地址
        return userService.getUserAddressList(userId);
    }
}
