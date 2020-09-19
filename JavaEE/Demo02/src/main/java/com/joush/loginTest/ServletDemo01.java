package com.joush.loginTest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

@WebServlet("/login/demo01")
public class ServletDemo01 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");
        Cookie[] cookies = request.getCookies();

        if (cookies != null){
            for (Cookie c : cookies){
                // 不是第一次访问
                if ("isLogin".equals(c.getName())){
                    String time = c.getValue();
                    String string = "Hello " + name + ",上次访问时间为：" + time;
                    response.getOutputStream().write(string.getBytes());
                }
            }
        } else {

            // 第一次访问，添加cookie信息
            Cookie cookie = new Cookie("isLogin", Calendar.getInstance().getTime().toString());
            response.addCookie(cookie);

            // 写回信息
            String string = "hello " + name;
            response.getOutputStream().write(string.getBytes());

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
