package web.response;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/responseWriter")
public class Demo03ResponseWriter extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 0.设置编码
        //resp.setHeader("Content-Type","text/html;charset=utf-8");
        resp.setContentType("text/html;charset=utf-8"); // 两种j均可
        // 1.获取字符输出流
        PrintWriter printWriter = resp.getWriter();

        // 2.输出数据
        printWriter.write("<h1>hello response</h1>");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
