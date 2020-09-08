package web.servlet;

import javax.servlet.*;
import java.io.IOException;

public class Demo02ServletMethod implements Servlet {

    /**
     *  初始化方法
     *  在 Servlet 被创建时执行，仅执行一次
     * @param servletConfig
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("init...");
    }

    /**
     * 获取 ServletConfig 对象
     * @return
     */
    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    /**
     * 提供服务方法
     * 每一次 Servlet 被访问时执行，执行多次
     * @param servletRequest
     * @param servletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("service...");
    }

    /**
     *  获取 Servlet 的信息，版本，作者等等
     * @return
     */
    @Override
    public String getServletInfo() {
        return null;
    }

    /**
     *  销毁方法
     *  在 Servlet 被杀死时，即服务器关闭时执行一次
     */
    @Override
    public void destroy() {
        System.out.println("destroy...");
    }
}
