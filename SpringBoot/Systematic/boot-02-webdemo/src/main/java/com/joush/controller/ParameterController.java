package com.joush.controller;

import com.joush.bean.Person;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rex Joush
 * @time 2021.04.27 18:57
 */

@RestController
public class ParameterController {

    @GetMapping("/car/{id}/owner/{username}")
    public Map<String, Object> getCar(@PathVariable("id") int id,
                                      @PathVariable("username") String name,
                                      @PathVariable Map<String, String> pv,
                                      @RequestHeader("User-Agent") String userAgent,
                                      @RequestHeader Map<String, String> headers,
                                      @RequestParam("age") int age,
                                      @RequestParam("interests") List<String> interests,
                                      @RequestParam Map<String, String> params,
                                      @CookieValue("token") Cookie cookie){
        Map<String, Object> result = new HashMap<>();

//        result.put("id", id);
//        result.put("name", name);
//        result.put("pv", pv);
//        result.put("userAgent", userAgent);
//        result.put("headers", headers);
        result.put("age", age);
        result.put("interests", interests);
        result.put("params", params);
        result.put("cookie", cookie.getValue());

        return result;
    }


    @PostMapping("/save")
    public Map<String, Object> testPost(@RequestParam String username,
                                        @RequestParam String email){
        Map<String, Object> result = new HashMap<>();
        result.put("username", username);
        result.put("email", email);

        return result;
    }

    /**
     * 数据绑定：页面提交的请求数据（GET, POST）都可以和对象属性进行绑定
     * @param person
     * @return
     */
    @PostMapping("/saveuser")
    public String saveUser(Person person) {
        return person.toString();
    }



}
