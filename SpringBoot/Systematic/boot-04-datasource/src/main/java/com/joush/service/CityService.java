package com.joush.service;

import com.joush.entities.City;
import com.joush.mapper.CityMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Rex Joush
 * @time 2021.05.20 20:28
 */

@Service
public class CityService {

    @Resource
    private CityMapper cityMapper;

    public City getCityById(int cityId){
        return cityMapper.getById(cityId);
    }

    public int saveCity(City city){
        return cityMapper.saveCity(city);
    }

}
