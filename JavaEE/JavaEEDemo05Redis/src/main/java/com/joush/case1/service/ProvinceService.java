package com.joush.case1.service;

import com.joush.case1.domain.Province;

import java.util.List;

public interface ProvinceService {

    public List<Province> findAll();
    public String findAllJson();

}
