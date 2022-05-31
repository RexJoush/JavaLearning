package com.joush.service;

import com.joush.bean.UserAddress;

import java.util.List;

/**
 * @author Rex Joush
 * @time 2022.05.02
 */
public interface UserService {

    /**
     * 按照用户 id 返回所有的收货地址
     * @param userId 用户 id
     * @return 地址列表
     */
    List<UserAddress> getUserAddressList(String userId);
}
