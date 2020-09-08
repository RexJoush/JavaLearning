package web.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/responseDemo01")
public class Demo01Response extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("demo01....");

        // 1.设置状态码为 302 重定向
        //resp.setStatus(302);

        // 2.设置响应头 location
        //resp.setHeader("location","/ServletLearning_war_exploded/responseDemo02");


        // 简单的重定向方法
        resp.sendRedirect("/ServletLearning_war_exploded/responseDemo02");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
