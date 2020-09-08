package web.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/demo07")
public class Demo03RequestBody extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取请求消息体

        // 1.获取字符流
        BufferedReader br = req.getReader();

        // 2.读取数据
        String line = null;
        while ((line = br.readLine()) != null){
            System.out.println(line);
        }

    }
}
