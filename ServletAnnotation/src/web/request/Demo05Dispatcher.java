package web.request;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/*
    请求转发，一种在服务器内部的资源跳转方式
        1.通过 request 对象获取请求转发器对象 RequestDispatcher getRequestDispatcher(String path)
        2.使用 RequestDispatcher 对象来进行转发 forward(ServletRequest request, ServletResponse response);

        特点
            1.浏览器地址栏路径不会发生变化
            2.只能访问当前服务器内部资源，不能访问外部资源
            3.转发是一次请求，即所有资源使用的是同一次请求

    共享数据
        request 域，在一次请求范围内，一般用于多个请求转发的多个资源中共享数据
        方法
            1.void setAttribute(String name, Object obj) 存储数据
            2.Object getAttribute(String name) 获取数据
            3.void removeAttribute(String name) 通过键名移除数据

 */

@WebServlet("/demo09")
public class Demo05Dispatcher extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("demo09...");

        /*RequestDispatcher requestDispatcher = req.getRequestDispatcher("/demo09");
        requestDispatcher.forward(req,resp);*/


        // 共享数据
        req.setAttribute("msg","aaa");

        // 转发
        req.getRequestDispatcher("/demo10").forward(req,resp);


    }
}
