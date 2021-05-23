package com.joush.mapper;

import com.joush.entities.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Rex Joush
 * @time 2021.05.19 10:51
 */

@Mapper
public interface UserMapper {

    List<User> findAllUser();

}
