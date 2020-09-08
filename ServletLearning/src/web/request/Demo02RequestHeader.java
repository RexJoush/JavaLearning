package web.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/*
     获取请求头
        String getHeader(String name) 通过请求头的名称获取请求头的值
        Enumeration<String> getHeaderNames() 获取所有请求头的名称
 */

@WebServlet("/demo06")
public class Demo02RequestHeader extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1.获取所有请求头名称
        Enumeration<String> headerNames = req.getHeaderNames();

        // 2.遍历
        while (headerNames.hasMoreElements()){
            String element = headerNames.nextElement();
            String header = req.getHeader(element);
            System.out.println(element + "=" + header);
        }
    }
}
