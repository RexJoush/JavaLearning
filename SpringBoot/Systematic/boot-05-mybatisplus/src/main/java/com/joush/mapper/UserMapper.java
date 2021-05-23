package com.joush.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joush.entities.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Rex Joush
 * @time 2021.05.21 10:35
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
