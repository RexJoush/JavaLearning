<%--
  Created by IntelliJ IDEA.
  User: Joush
  Date: 2020/9/17
  Time: 20:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <!-- 定义的java代码，在service中，service可定义什么，此处就可以定义什么 -->
    <%
        System.out.println("Hello JSP");
    %>

    <!-- 定义的java代码在java类的成员位置，成员变量，成员方法等 -->
    <%!
        int i = 3;
    %>

    <!-- 相当于输出语句，将变量的值输出到页面，定义在service方法中 -->
    <%= i

    %>

    <h1>Hello Java</h1>
</body>
</html>
