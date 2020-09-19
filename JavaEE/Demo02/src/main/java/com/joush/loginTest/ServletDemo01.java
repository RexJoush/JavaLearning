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
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String time = simpleDateFormat.format(date);

        // 解决Cookie不支持空格的问题
        time = URLEncoder.encode(time, "utf-8"); // 添加URL编码

        if (cookies != null){
            for (Cookie c : cookies){
                // 不是第一次访问
                if ("isLogin".equals(c.getName())){
                    String value = c.getValue();

                    // URL解码
                    value = URLDecoder.decode(value, "utf-8");

                    String string = "Hello " + name + ",上次访问时间为：" + value;
                    c.setValue(time); // 重新设置时间
                    c.setMaxAge(60*60*24*30); // 一个月
                    response.addCookie(c);

                    response.getOutputStream().write(string.getBytes());

                    break;
                }
            }
        } else {

            // 第一次访问，添加cookie信息

            // String time = Calendar.getInstance().getTime().toString().replace(" ","-");

            Cookie cookie = new Cookie("isLogin", time);
            cookie.setMaxAge(60*60*24*30); // 一个月
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
