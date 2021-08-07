package com.joush.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.joush.mapper.UserMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Rex Joush
 * @time 2021.08.05 20:54
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        QueryWrapper<com.joush.entities.User> wrapper = new QueryWrapper<>();
        //wrapper.eq("username", s);
        com.joush.entities.User user = userMapper.selectOne(wrapper.lambda().eq(com.joush.entities.User::getUsername, s));

        // 说明数据库中没有用户名
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在!");
        }

        // 权限列表
        List<GrantedAuthority> authorities =
                AuthorityUtils.commaSeparatedStringToAuthorityList("abc");

        // 返回当前用户的用户名，密码，以及权限列表
        return new User(user.getUsername(), new BCryptPasswordEncoder().encode(user.getPassword()), authorities);
    }
}
