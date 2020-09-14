package com.loginTest.servlet;

import com.loginTest.dao.UserDao;
import com.loginTest.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/loginTest/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 设置编码
        req.setCharacterEncoding("utf-8");

        // 获取登录数据
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User loginUser = new User();
        loginUser.setUsername(username);
        loginUser.setPassword(password);

        UserDao dao = new UserDao();
        User user = dao.login(loginUser);

        if (user == null){
            req.getRequestDispatcher("/loginTest/fail").forward(req, resp);
        } else {
            req.setAttribute("user", user);
            req.getRequestDispatcher("/loginTest/success").forward(req, resp);
        }


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
