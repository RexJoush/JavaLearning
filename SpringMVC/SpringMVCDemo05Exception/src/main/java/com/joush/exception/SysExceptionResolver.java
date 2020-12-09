package com.joush.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常处理器对象
 */
public class SysExceptionResolver implements HandlerExceptionResolver {

    /**
     * 处理异常的逻辑
     * @param httpServletRequest 请求的 request
     * @param httpServletResponse 请求的 response
     * @param o 当前处理器对象，用的很少
     * @param e 抛出的自定义异常
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

        // 获得异常对象
        SysException exception = null;
        // 如果当前异常对象是自定义异常
        if (e instanceof SysException){
            exception = (SysException) e;
        } else {
            exception = new SysException("系统正在维护...");
        }

        // 创建 ModelAndView
        ModelAndView mv = new ModelAndView();

        // 存入提示信息
        mv.addObject("errorMsg", exception.getMessage());

        // 跳转 error.jsp
        mv.setViewName("error");

        return mv;
    }
}
