package com.joush.controller;

import com.joush.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Date;
import java.util.Map;

/**
 * 常用注解
 */
@Controller
@RequestMapping("/annotation")
@SessionAttributes(value = {"msg"})
public class AnnotationController {

    /**
     * 获取请求参数
     * @param username
     * @return
     */
    @RequestMapping("/testRequestParam")
    public String testRequestParam(@RequestParam(name = "name") String username){
        System.out.println("testRequestParam executed!");
        System.out.println(username);
        return "success";
    }

    /**
     * 获取请求体的内容
     * @param body
     * @return
     */
    @RequestMapping("/testRequestBody")
    public String testRequestBody(@RequestBody String body){
        System.out.println("testRequestBody executed!");
        System.out.println(body);
        return "success";
    }

    /**
     * PathVariable 注解演示
     * @param id
     * @return
     */
    @RequestMapping("/testPathVariable/{id}")
    public String testPathVariable(@PathVariable(name = "id") String id){
        System.out.println("testPathVariable executed!");
        System.out.println(id);
        return "success";
    }

    /**
     * RequestHeader 注解演示，获取请求头信息
     * @param header
     * @return
     */
    @RequestMapping("/testRequestHeader")
    //                              写出要获取头的名称即可
    public String testRequestHeader(@RequestHeader("accept") String header){
        System.out.println("testRequestHeader executed!");
        System.out.println(header);
        return "success";
    }

    /**
     * 获取 cookie 的值
     * @param cookieValue
     * @return
     */
    @RequestMapping("/testCookieValue")
    public String testCookieValue(@CookieValue(value = "JSESSIONID") String cookieValue) {
        System.out.println("testCookieValue executed!");
        System.out.println(cookieValue);
        return "success";
    }

    /*@RequestMapping("/testModelAttribute")
    public String testModelAttribute(User user) {
        System.out.println("testModelAttribute executed!");
        System.out.println(user);
        return "success";
    }*/

    @RequestMapping("/testModelAttribute")
    public String testModelAttribute(@ModelAttribute("abc") User user) {
        System.out.println("testModelAttribute executed!");
        System.out.println(user);
        return "success";
    }

    /**
     * 该方法会先执行
     */
    @ModelAttribute
    public User showUser(String name){
        System.out.println("show user executed!");

        // 通过用户名查询数据库（模拟）
        User user = new User();
        user.setName(name);
        user.setAge(20);
        user.setDate(new Date());

        return user;
    }


    @ModelAttribute
    public void showUser(String name, Map<String, User> map){
        System.out.println("show user executed!");

        User user = new User();
        user.setName(name);
        user.setAge(20);
        user.setDate(new Date());

        map.put("abc", user);

    }

    /**
     * 将数据存入 session 域中
     * @param model
     * @return
     */
    @RequestMapping("/testSessionAttribute")
    public String testSessionAttribute(Model model) {
        System.out.println("testSessionAttribute executed!");

        // 底层会存储到 request 域对象中
        model.addAttribute("msg", "hello spring");
        return "success";
    }

    /**
     * 获取上面存入的 session 数据
     * @param modelMap
     * @return
     */
    @RequestMapping("/getSessionAttribute")
    public String getSessionAttribute(ModelMap modelMap) {
        System.out.println("getSessionAttribute executed!");

        // 获取存储的 session
        String msg = (String) modelMap.get("msg");
        System.out.println(msg);

        return "success";
    }

    /**
     * 清除 session
     * @param status
     * @return
     */
    @RequestMapping("/delSessionAttribute")
    public String delSessionAttribute(SessionStatus status) {
        System.out.println("delSessionAttribute executed!");

        // 删除存储的 session
        status.setComplete();

        return "success";
    }

}
