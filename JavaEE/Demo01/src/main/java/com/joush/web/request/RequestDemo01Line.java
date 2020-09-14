package com.joush.web.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
    request 继承关系
        ServletRequest      -- 接口
               | 继承
        HttpServletRequest  -- 接口
               | 实现
        org.apache.catalina.connector.RequestFacade (tomcat实现的)

    request 功能
        1.获取请求数据
            - 获取请求行数据
                * GET /Demo1/demo01?name=tom HTTP/1.1
                * 方法
                    1.获取请求方式： GET
                        - String getMethod();
                    2.(*)获取虚拟目录 /Demo1
                        - String getContextPath();
                    3.获取Servlet路径 /demo01
                        - String getServletPath();
                    4.获取get方式请求参数 name=tom
                        - String getQueryString();
                    5.(*)获取请求的uri /Demo1/demo01
                        - String getRequestURI();
                        - StringBuffer getRequestURL(); // http://localhost:8080/Demo1/demo01

                        URL: 统一资源定位符 http://localhost:8080/Demo1/demo01
                        URI: 统一资源标识符 /Demo1/demo01

                    6.获取协议及版本 HTTP/1.1
                        - String getProtocol();
                    7.获取客户机的IP地址
                        - String getRemoteAddr();

        2.其他功能
 */
@WebServlet("/request/demo01")
public class RequestDemo01 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1.获取请求方式： GET
        System.out.println(req.getMethod()); // GET

        // 2.(*)获取虚拟目录 /Demo1
        System.out.println(req.getContextPath()); // /Demo01

        // 3.获取Servlet路径 /demo01
        System.out.println(req.getServletPath()); // /request/demo01

        // 4.获取get方式请求参数 name=tom
        System.out.println(req.getQueryString()); // null

        // 5.(*)获取请求的uri /Demo1/demo01
        System.out.println(req.getRequestURI()); // /Demo01/request/demo01
        System.out.println(req.getRequestURL()); // http://localhost:8080/Demo01/request/demo01
        // 6.获取协议及版本 HTTP/1.1
        System.out.println(req.getProtocol()); // HTTP/1.1

        // 7.获取客户机的IP地址
        System.out.println(req.getRemoteAddr()); // 0:0:0:0:0:0:0:1


    }
}
