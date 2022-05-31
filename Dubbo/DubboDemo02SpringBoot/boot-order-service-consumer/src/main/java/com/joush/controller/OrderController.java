package com.joush.controller;

import com.alibaba.fastjson.JSON;
import com.joush.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Rex Joush
 * @time 2022.05.03
 */

@RestController
public class OrderController {

    @Resource
    OrderService orderService;

    @GetMapping("/initOrder")
    public String initOrder(@RequestParam("uid") String userId) {
        return JSON.toJSONString(orderService.initOrder(userId));
    }
}
