package com.joush.web.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/*
    获取请求体数据，请求体只有 POST 请求才有
    步骤
        1.获取流对象
            - BufferedReader getReader(); 获取字符输入流，只能操作字符数据
            - ServletInputStream getInputStream(); 获取字节输入流，可以操作所有类型数据
        2.从流对象获取数据
 */
@WebServlet("/request/demo03")
public class RequestDemo03Body extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 1.获取字符流
        BufferedReader reader = req.getReader();

        // 2.读取数据
        String line = null;
        while ((line = reader.readLine()) != null){
            System.out.println(line);
        }

    }
}
