package com.joush.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 */
public class MyInterceptor2 implements HandlerInterceptor {

    /**
     * 预处理，controller 执行前
     * @param request
     * @param response
     * @param handler
     * @return true 放行，执行下一个拦截器，如果没有，执行 controller
     *         false 不放行，可以跳转到某个页面
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("Pre MyInterceptor2 executed! 222");
        return true;
    }

    /**
     * 后处理，controller 执行之后 success.jsp 执行之前
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("Post MyInterceptor2 executed! 222");
    }

    /**
     * success.jsp 页面执行后，该方法执行
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion executed! 222");
    }
}
