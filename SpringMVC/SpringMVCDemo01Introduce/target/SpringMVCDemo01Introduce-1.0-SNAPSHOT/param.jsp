<%--
  Created by IntelliJ IDEA.
  User: Joush
  Date: 2020/12/2
  Time: 16:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%--  请求参数绑定  --%>
    <a href="param/testParam?username=aaa">请求参数绑定</a>

    <%--  表单  --%>
    <form action="param/saveAccount" method="post">
        姓名：<input type="text" name="username">
        密码：<input type="password" name="password">
        金额：<input type="text" name="money">
        用户名：<input type="text" name="user.name">
        年龄：<input type="text" name="user.age">
        <input type="submit" value="提交">
    </form>
</body>
</html>
