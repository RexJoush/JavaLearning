package web.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

/*
    String getParameter(String name) 根据参数名称获取参数值 username=zhang&password=123
    String[] getParameterValues(String name) 根据参数名称获取参数值数组 hobby=xx&hobby=yy
    Enumeration<String> getParameterNames() 获取所有请求的参数名称
    Map<String, String[]> getParameterMao() 获取所有参数的map集合
 */

@WebServlet("/demo08")
public class Demo04RequestMethod extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 0. 在 tomcat9 以后，自动解决了 get 方法的乱码问题，post 需要手动解决
        req.setCharacterEncoding("utf-8");


        // 1.根据名称获取参数值
        String username = req.getParameter("username");
        System.out.println("get");
        System.out.println(username);

        // 2.根据参数名获取值数组
        String[] hobbies = req.getParameterValues("hobby");
        for (String hobby : hobbies) {
            System.out.println(hobby);
        }

        // 3.获取所有参数的名称
        Enumeration<String> parameterNames = req.getParameterNames();
        while (parameterNames.hasMoreElements()){
            String name = parameterNames.nextElement();
            System.out.println(name);
            String value = req.getParameter(name);
            System.out.println(value);
            System.out.println("============");
        }

        // 4.获取所有参数的 map 集合
        Map<String, String[]> parameterMap = req.getParameterMap();
        for (String s : parameterMap.keySet()) {
            System.out.println(s + " " + parameterMap.get(s));

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 调用 doGet
        this.doGet(req,resp);
    }
}
