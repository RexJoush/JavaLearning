package com.fileDownload;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

@WebServlet("/download/demo01")
public class DownloadDemo01 extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1.获取请求参数
        String fileName = req.getParameter("filename");

        // 2.使用字节输入流加载文件进内存
        ServletContext context = req.getServletContext();
        String realPath = context.getRealPath("/images/" + fileName);

        // 3.用字节流关联
        FileInputStream fis = new FileInputStream(realPath);

        // 4.设置 response 响应头
        String mimeType = context.getMimeType(fileName); // 获取文件的 mime类型
        resp.setContentType(mimeType);

        // 5.解决中文文件名的问题
        // 获取user-agent请求头
        String agent = req.getHeader("user-agent");
        // 使用工具类来编码即可
        // String filename =  utils.getFileName(agent, fileName);

        // 6.设置响应头的打开方式
        resp.setHeader("content-disposition", "attachment;filename=" + fileName);

        // 7.输出
        ServletOutputStream sos = resp.getOutputStream();
        byte[] bytes = new byte[1024 * 8];
        int len = 0;
        while ((len = fis.read(bytes)) != -1) {
            sos.write(bytes, -1, len);
        }
        fis.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}