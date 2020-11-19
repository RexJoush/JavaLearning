package com.joush.dao;

import com.joush.domain.Role;

import java.util.List;

public interface RoleDao {

    /**
     * 查询所有角色
     * @return
     */
    List<Role> findAll();

}
