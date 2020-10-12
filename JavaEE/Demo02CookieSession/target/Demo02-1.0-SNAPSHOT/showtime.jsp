<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.net.URLDecoder" %><%--
  Created by IntelliJ IDEA.
  User: Joush
  Date: 2020/9/19
  Time: 19:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello</title>
</head>
<body>
    <%
        String name = request.getParameter("name");
        Cookie[] cookies = request.getCookies();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String time = simpleDateFormat.format(date);

        // 解决Cookie不支持空格的问题
        time = URLEncoder.encode(time, "utf-8"); // 添加URL编码

        if (cookies != null){
            for (Cookie c : cookies){
                // 不是第一次访问
                if ("isLogin".equals(c.getName())){
                    String value = c.getValue();

                    // URL解码
                    value = URLDecoder.decode(value, "utf-8");

                    String string = "Hello " + name + ",上次访问时间为：" + value;
                    c.setValue(time); // 重新设置时间
                    c.setMaxAge(60*60*24*30); // 一个月
                    response.addCookie(c);

                    out.write(string);

                    break;
                }
            }
        } else {

            // 第一次访问，添加cookie信息

            // String time = Calendar.getInstance().getTime().toString().replace(" ","-");

            Cookie cookie = new Cookie("isLogin", time);
            cookie.setMaxAge(60*60*24*30); // 一个月
            response.addCookie(cookie);

            // 写回信息
            String string = "hello " + name;
            out.write(string);

        }
    %>
</body>
</html>
