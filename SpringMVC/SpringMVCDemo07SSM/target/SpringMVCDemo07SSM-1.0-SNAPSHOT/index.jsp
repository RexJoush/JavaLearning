<%--
  Created by IntelliJ IDEA.
  User: Joush
  Date: 2020/12/10
  Time: 10:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <a href="account/findAll">测试</a>

    <form action="account/save" method="post">
        name: <input type="text" name="name">
        money: <input type="text" name="money">
        <input type="submit" value="保存">
    </form>
</body>
</html>
