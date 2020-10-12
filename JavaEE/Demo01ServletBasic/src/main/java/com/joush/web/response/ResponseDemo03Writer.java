package com.joush.web.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/response/demo03")
public class ResponseDemo03Writer extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 获取流之前，设置默认编码
        // resp.setCharacterEncoding("utf-8");
        // 服务器消息的数据编码，建议浏览器使用此编码
        resp.setHeader("Content-Type","text/htm;charset=utf-8");
        resp.setContentType("text/html;charset=utf-8"); // 简便设置


        // 获取字符输出流
        PrintWriter writer = resp.getWriter();

        // 输出数据
        writer.write("Hello World");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
