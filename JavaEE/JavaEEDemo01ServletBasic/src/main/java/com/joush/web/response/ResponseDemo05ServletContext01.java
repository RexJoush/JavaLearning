package com.joush.web.response;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet("/response/demo05")
public class ResponseDemo05ServletContext01 extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // ServletContext 对象的获取
        ServletContext context1 = req.getServletContext();
        ServletContext context2 = getServletContext();
        System.out.println(context1 == context2); // true

        // 定义文件名称
        String filename = "a.jpg";

        // 获取MIME类型
        String mimeType = context1.getMimeType(filename);
        System.out.println(mimeType);

        // 获取文件的真实路径
        String realPath = context1.getRealPath("/a.txt"); // web 目录
        String realPath2 = context1.getRealPath("/WEB-INF/a.txt"); // WEB-INF 目录
        String realPath3 = context1.getRealPath("/WEB-INF/classes/a.txt"); // src 目录


        System.out.println(realPath);

//        File file = new File(realPath);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
