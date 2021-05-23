package com.joush.controller;

import com.joush.entities.City;
import com.joush.service.CityService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Rex Joush
 * @time 2021.05.20 20:27
 */

@RestController
public class CityController {

    @Resource
    CityService cityService;

    @GetMapping("/getCityById")
    public City getCityById(@RequestParam("cityId") int cityId) {

        return cityService.getCityById(cityId);

    }

    @PostMapping("/saveCity")
    public String saveCity(City city) {
        int i = cityService.saveCity(city);
        if (i > 0) {
            return "success";
        } else {
            return "error";
        }
    }

}
