package web.response;


import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/responseOutputStream")
public class Demo04ResponseOutputStream extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 0.设置编码
        resp.setContentType("text/html;charset=utf-8"); // 两种j均可
        // 1.获取字符输出流
        ServletOutputStream servletOutputStream = resp.getOutputStream();

        // 2.输出数据
        servletOutputStream.write("<h1>hello response</h1>".getBytes("utf-8"));

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
