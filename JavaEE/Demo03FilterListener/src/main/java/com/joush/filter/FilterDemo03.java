package com.joush.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

// 只有转发时才会被执行
//@WebFilter(value = "/ServletDemo01",dispatcherTypes = DispatcherType.FORWARD)

// 即可以直接被访问，也可以转发访问
@WebFilter(value = "/ServletDemo01",dispatcherTypes = {DispatcherType.FORWARD,DispatcherType.REQUEST})

public class FilterDemo03 implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        System.out.println("Filter Demo03 ...");

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
