package com.joush.web.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/request/demo06_01")
public class RequestDemo06ShareData1 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 添加转发数据
        System.out.println("/request/demo06_01");
        req.setAttribute("a", "aaa");
        req.getRequestDispatcher("/request/demo06_02").forward(req, resp);
    }
}
