package com.joush.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/*
    为一个 Servlet 定义多个url地址
    Servlet 地址的配置方式
        - /xxx
        - /xxx/xxx  多级目录
        - /xxx/*    通配符

 */
@WebServlet({"/demo06_01","/demo06_02","/demo06_03"})
//@WebServlet("/demo06/01")
//@WebServlet("/demo06/*")
//@WebServlet("*.do") 不要加 /
public class ServletDemo06HttpServletMulti extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Demo06 doGet...");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Demo06 doPost...");
    }
}
