package com.joush.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rex Joush
 * @time 2021.04.28 13:35
 */

@Controller
public class RequestController {

    @GetMapping("/goto")
    public String goToPage(HttpServletRequest request){

        request.setAttribute("msg", "success");
        request.setAttribute("code", 200);

        return "forward:/success";
    }

    @GetMapping("/params")
    public String testParam(Map<String, Object> map,
                            Model model,
                            HttpServletRequest request,
                            HttpServletResponse response){

        map.put("hello", "world!!!");
        model.addAttribute("world", "hello");
        request.setAttribute("message", "Hello world");
        Cookie cookie = new Cookie("c1", "v1");
        cookie.setDomain("localhost");
        response.addCookie(cookie);

        return "forward:/success";
    }


    @ResponseBody
    @GetMapping("/success")
    public Map<String, Object> success(
                                       HttpServletRequest request){
        Object msg1 = request.getAttribute("msg");

        Map<String, Object> result = new HashMap<>();

        Object hello = request.getAttribute("hello");
        Object world = request.getAttribute("world");
        Object message = request.getAttribute("message");


        result.put("reqMethod_msg", msg1);
//        result.put("annotation_msg", msg);
        result.put("hello", hello);
        result.put("world", world);
        result.put("message", message);

        return result;
    }


    @GetMapping("/cars/sell")
    public Map<String, Object> carsSell(@MatrixVariable int low,
                                        @MatrixVariable List<String> brand){
        Map<String, Object> result = new HashMap<>();

        result.put("low", low);
        result.put("brand", brand);

        return result;
    }


}
