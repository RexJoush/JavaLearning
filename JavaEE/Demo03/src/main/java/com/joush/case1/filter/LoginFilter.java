package com.joush.case1.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

// 登录验证的过滤器
@WebFilter("/*")
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        // 0.强制转换
        HttpServletRequest request = (HttpServletRequest) req;

        // 1.获取资源请求的路径
        String uri = request.getRequestURI();

        // 2.是否包含登录相关资源路径,注意包含一些css等等
        if (uri.contains("/login.jsp") || uri.contains("/loginServlet")){
            // 包含，就是想登录，放行
            chain.doFilter(req, resp);
        }

        // 不包含
        else {
            // 3.获取 session
            Object user = request.getSession().getAttribute("user");
            if (user != null){
                // 登录，放行
                chain.doFilter(req, resp);
            }
            else {
                // 没有登录
                request.setAttribute("msg","尚未登录");
                request.getRequestDispatcher("/login.jsp").forward(request,resp);
            }
        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
