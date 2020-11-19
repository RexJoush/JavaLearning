package com.joush.case1.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joush.case1.domain.Province;
import com.joush.case1.service.ProvinceService;
import com.joush.case1.service.impl.ProvinceServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/provinceServlet")
public class ProvinceServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 1.调用 service 查询
        ProvinceService service = new ProvinceServiceImpl();
        String provinces = service.findAllJson();

        // 3.响应结果
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(provinces);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
