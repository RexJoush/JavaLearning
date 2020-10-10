package com.joush.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/*") // 访问所有资源都会执行该过滤器
public class FilterDemo02 implements Filter {

    /**
     * 在服务器关闭后，Filter 对象被销毁，服务器正常关闭则执行 destroy 方法
     */
    public void destroy() {
    }

    /**
     * 每执行一次请求，都会执行一次
     * @param req
     * @param resp
     * @param chain
     * @throws ServletException
     * @throws IOException
     */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        // 对 request 对象进行增强
        System.out.println("filter执行...");
        chain.doFilter(req, resp);

        // 对 response 对象进行增强
        System.out.println("filter回来...");
    }

    /**
     * 在服务器启动后创建 Filter 对象，调用 init方法
     * @param config
     * @throws ServletException
     */
    public void init(FilterConfig config) throws ServletException {

    }

}
