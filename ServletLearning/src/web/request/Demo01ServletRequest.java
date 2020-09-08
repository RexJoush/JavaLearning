package web.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
    http 请求的数据格式
        - 请求行
            请求方式 请求url 请求协议/版本
            GET /login.html HTTP/1.1
        - 请求头
            键值对的形式
            常见的有
                Host: 主机名
                User-Agent: 浏览器版本信息
                Accept: 可接受的文件类型，mime
                Accept-Encoding
                Accept-Language
                Referer: 判断当前连接从哪里来的，防止盗链，以及统计
        - 请求空行
        - 请求体

    方法
    获取请求行
        String getMethod() 获取请求方法
        String getContextPath() 获取虚拟目录
        String getServletPath() 获取 Servlet 路径
        String getQueryString() 获取参数字符串
        String getRequestURI() 获取请求URI
        StringBuffer getRequestURL() 获取完整的URL
        String getProtocol() 获取协议版本
        String getRemoteAddr() 获取客户端的IP地址


 */

@WebServlet("/demo05")
public class Demo01ServletRequest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1.获取请求方式
        String method = req.getMethod();
        System.out.println(method);

        // 2.获取虚拟目录
        String contextPath = req.getContextPath();
        System.out.println(contextPath);

        // 3.获取 Servlet 路径
        String servletPath = req.getServletPath();
        System.out.println(servletPath);

        // 4.获取个get方法请求参数
        String queryString = req.getQueryString();
        System.out.println(queryString);

        // 5.获取请求URI，URL
        String requestURI = req.getRequestURI();
        StringBuffer requestURL = req.getRequestURL();
        System.out.println(requestURI);
        System.out.println(requestURL);

        // 6.返回协议版本
        String protocol = req.getProtocol();
        System.out.println(protocol);

        // 7.返回客户端IP
        String remoteAddr = req.getRemoteAddr();
        System.out.println(remoteAddr);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
