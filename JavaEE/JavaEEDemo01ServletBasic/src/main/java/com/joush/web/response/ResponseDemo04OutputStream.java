package com.joush.web.response;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/response/demo04")
public class ResponseDemo04OutputStream extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 获取流之前，设置默认编码
        resp.setContentType("text/html;charset=utf-8"); // 简便设置

        // 获取字符输出流
        ServletOutputStream outputStream = resp.getOutputStream();

        // 输出数据
        outputStream.write("Hello World".getBytes());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
