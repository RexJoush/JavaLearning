package com.joush.case1.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joush.case1.dao.ProvinceDao;
import com.joush.case1.dao.impl.ProvinceDaoImpl;
import com.joush.case1.domain.Province;
import com.joush.case1.service.ProvinceService;
import com.joush.util.JedisPoolUtils;
import redis.clients.jedis.Jedis;

import java.util.List;

public class ProvinceServiceImpl implements ProvinceService {

    private ProvinceDao dao = new ProvinceDaoImpl();


    @Override
    public List<Province> findAll() {
        return dao.findAll();
    }



    @Override
    public String findAllJson() {
        Jedis jedis = JedisPoolUtils.getJedis();

        String province = jedis.get("province");

        // 判断是否存在
        if (province == null || province.length() == 0){
            // redis 没有数据，那么从数据库查询
            List<Province> provinces = dao.findAll();

            ObjectMapper mapper = new ObjectMapper();
            try {
                province = mapper.writeValueAsString(provinces);
                // 数据返回，并写入redis中
                jedis.set("province", province);
                jedis.close();

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return province;

    }
}
