package com.joush.web.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/*
    - 获取请求头数据
        * 方法
            - String getHeader(String name); 通过请求头的名称获取请求头的值
            - Enumeration<String> getHeaderNames(); 获取所有请求头名称
 */
@WebServlet("/request/demo02")
public class RequestDemo02Header extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 获取请求头数据
        Enumeration<String> headerNames = req.getHeaderNames();

        // 使用迭代器获取
        while (headerNames.hasMoreElements()){
            String name = headerNames.nextElement();
            System.out.println(name + " = " + req.getHeader(name));
        }

    }
}
