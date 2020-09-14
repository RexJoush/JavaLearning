package com.joush.web.request;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
    请求转发，一种在服务器内部资源跳转的方式
        - 步骤
            1.通过 request 获取请求转发对象 RequestDispatcher getRequestDispatcher(String path);
            2.RequestDispatcher对象调用 forward 方法 void forward(ServletRequest res, ServletResponse resp)
        - 特点
            1.浏览器地址栏不发生变化
            2.只能转发到当前服务器内部资源
            3.多个资源中使用的是同一次请求
 */
@WebServlet("/request/demo05")
public class RequestDemo05Dispatcher extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 转发资源
        System.out.println("demo05....");
        req.getRequestDispatcher("/request/demo04").forward(req, resp);
    }
}
