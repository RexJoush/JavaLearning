package com.joush.mapper;

import com.joush.entities.City;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author Rex Joush
 * @time 2021.05.20 20:23
 */

@Mapper
public interface CityMapper {

    @Select("select * from city where id = #{cityId}")
    City getById(int cityId);

    int saveCity(City city);

}
