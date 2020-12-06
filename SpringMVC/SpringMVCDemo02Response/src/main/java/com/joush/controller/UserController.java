package com.joush.controller;

import com.joush.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * 返回值类型是 String
     * @param model
     * @return
     */
    @RequestMapping("/testString")
    public String testString(Model model){
        System.out.println("testString executed!");

        // 模拟从数据库中查询出 User 对象
        User user = new User();
        user.setUsername("joush");
        user.setPassword("123");
        user.setAge(20);

        // model 对象
        model.addAttribute("user", user);

        return "success";
    }

    /**
     * 返回值类型是 void
     * 请求转发是一次请求，跳转路径不用编写项目的名称
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/testVoid")
    public void testVoid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("testVoid executed!");

        // 可以编写请求转发的程序
        // request.getRequestDispatcher("/WEB-INF/pages/success.jsp").forward(request, response);

        // 也可以重定向
        // response.sendRedirect(request.getContextPath() + "/index.jsp");

        // 直接进行响应
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println("你好");
    }

    /**
     * 返回 ModelAndView 对象
     * @return
     */
    @RequestMapping("/testModelAndView")
    public ModelAndView testModelAndView(){
        System.out.println("testModelAndView executed!");

        // 创建 ModelAndView 对象
        ModelAndView mv = new ModelAndView();

        // 模拟从数据库中查询出 User 对象
        User user = new User();
        user.setUsername("joush");
        user.setPassword("123");
        user.setAge(20);

        // 把 user 对象存入 mv 中，也会把 user 对象存入 request 中
        mv.addObject("user", user);

        // 跳转到哪个页面，通过视图解析器完成
        mv.setViewName("success");

        return mv;
    }

    /**
     * 使用关键字来实现转发或重定向
     * @return
     */
    @RequestMapping("/testForwardOrRedirect")
    public String testForwardOrRedirect(){

        System.out.println("testForwardOrRedirect executed!");

        // 请求转发
        // return "forward:/WEB-INF/pages/success.jsp";

        // 重定向
        return "redirect:/index.jsp";
    }

    @RequestMapping("/testAjax")
    public @ResponseBody User testAjax(@RequestBody User user){
        System.out.println("testAjax executed!");

        // 客户端发送 ajax 请求，传的是 json 字符串，后端把 json 字符串封装到 user 对象中
        System.out.println(user);

        // 做响应
        user.setUsername("haha");
        user.setAge(30);

        return user;
    }

}
