package com.joush.web.response;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


// 重定向测试

/*
    转发的特点 forward
        1.转发地址栏路径不变
        2.转发只能访问当前服务器下的资源
        3.转发是一次请求
    重定向的特点 redirect
        1.地址栏发生变化
        2.重定向可以访问其他站点
        3.重定向是两次请求

    路径写法
 */


@WebServlet("/response/demo01")
public class ResponseDemo01Redirect extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("demo01...");

        // 设置状态码为 302 重定向
        resp.setStatus(302);

        // 设置响应头 location
        resp.setHeader("location", "demo02");

        /*
            简便重定向方法
        */

        // resp.sendRedirect("demo02");

        // 动态获取虚拟目录

        // String contextPath = req.getContextPath();

        // resp.sendRedirect(contextPath + "/demo02");


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }
}
