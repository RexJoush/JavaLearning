package com.joush.web.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

/*
    其他方法
    1.获取请求参数通用方式
        - String getParameter(String name); 根据参数名获取参数值
        - String[] getParameterValues(String name); 根据参数名获取参数值数组 hobby=movie&hobby=watch
        - Enumeration<String> getParameterNames(); 获取所有参数的名称
        - Map<String,String[]> getParameterMap(); 获取所有参数的键值对 map 集合

        * 中文乱码问题
            在 post 请求前面加一句 req.setCharacterEncoding("UTF-8");
    2.请求转发
    3.共享数据
    4.获取 ServletContext
 */
@WebServlet("/request/demo04")
public class RequestDemo04OtherFunc extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("POST");

        // 解决乱码问题
        req.setCharacterEncoding("UTF-8");

        // 根据参数名获取参数值
        System.out.println(req.getParameter("username"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("GET");

        // 根据参数名获取参数值
        System.out.println(req.getParameter("username"));
        System.out.println("-----");

        // 根据参数名获取参数值数组，复选框
        String[] hobbies = req.getParameterValues("hobby");
        System.out.println(Arrays.toString(hobbies));
        System.out.println("-----");

        // 获取所有参数名称
        Enumeration<String> names = req.getParameterNames();
        while (names.hasMoreElements()){
            String name = names.nextElement();
            System.out.println(name + " = " + req.getParameter(name));
        }
        System.out.println("-----");

        // 获取所有参数键值对的 map 集合
        Map<String, String[]> parameterMap = req.getParameterMap();
        Set<String> keySet = parameterMap.keySet();
        for (String key : keySet){
            String[] strings = parameterMap.get(key);

            System.out.println(key + Arrays.toString(strings));

        }
    }
}
