package com.joush.service.impl;

import com.joush.bean.UserAddress;
import com.joush.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author Rex Joush
 * @time 2022.05.02
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public List<UserAddress> getUserAddressList(String userId) {
        UserAddress address1 = new UserAddress(1, "北京中关村", "1", "Rex", "010-45672412", "Y");
        UserAddress address2 = new UserAddress(2, "陕西西北大学", "1", "Rex", "010-45672412", "N");

        return Arrays.asList(address1, address2);
    }
}
