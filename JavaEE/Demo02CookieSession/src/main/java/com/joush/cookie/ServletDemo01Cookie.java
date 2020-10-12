package com.joush.cookie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*

    Cookie 快速入门

 */
@WebServlet("/cookie/demo01")
public class ServletDemo01Cookie extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.创建 Cookie 对象
        Cookie cookie = new Cookie("msg", "hello");
        Cookie cookie2 = new Cookie("msg2", "你好");

        // 设置存活时间
        cookie.setMaxAge(30); // 存储30秒
        cookie2.setMaxAge(30); // 存储30秒
        // cookie.setMaxAge(-1); // 关闭浏览器即会删除
        // cookie.setMaxAge(0); // 删除已存储的数据

        // 2.发送 Cookie
        response.addCookie(cookie);
        response.addCookie(cookie2);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
