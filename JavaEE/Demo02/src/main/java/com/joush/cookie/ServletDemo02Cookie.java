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
@WebServlet("/cookie/demo02")
public class ServletDemo02Cookie extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.获取所有 Cookie 对象
        Cookie[] cookies = request.getCookies();

        // 2.获取数据
        if (cookies != null){
            for (Cookie c: cookies){
                System.out.println(c.getName() + ":" + c.getValue());
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
