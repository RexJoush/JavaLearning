package com.joush.web.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/request/demo06_02")
public class RequestDemo06ShareData2 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取转发的数据
        System.out.println("/request/demo06_02");
        Object a = req.getAttribute("a");
        System.out.println(a);
    }
}
