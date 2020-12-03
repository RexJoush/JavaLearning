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

    <%--  表单，数据封装到 Java Bean  --%>
    <%--
    <form action="param/saveAccount" method="post">
        姓名：<input type="text" name="username">
        密码：<input type="password" name="password">
        金额：<input type="text" name="money">
        用户名：<input type="text" name="user.name">
        年龄：<input type="text" name="user.age">
        <input type="submit" value="提交">
    </form>
    --%>

    <%--  数据封装到 Account 类中，类中存在 list 和 map 的集合  --%>
    <%--
    <form action="param/saveAccount" method="post"><br>
        姓名：<input type="text" name="username"><br>
        密码：<input type="password" name="password"><br>
        金额：<input type="text" name="money"><br>

        &lt;%&ndash;  封装到 User 对象中，将此对象封装到 list 中  &ndash;%&gt;
        用户名1：<input type="text" name="list[0].name"><br>
        年龄1：<input type="text" name="list.[0].age"><br>

        &lt;%&ndash;  封装到 User 对象中，将此对象封装到 map 中
                map['key'].attribute
          &ndash;%&gt;
        用户名2：<input type="text" name="map['one'].name"><br>
        年龄2：<input type="text" name="map['one'].age"><br>
        <input type="submit" value="提交"><br>
    </form>
    --%>

    <%--  自定义类型转换  --%>
    <%--<form action="param/saveUser" method="post"><br>
        用户姓名：<input type="text" name="name"><br>
        用户年龄：<input type="text" name="age"><br>
        用户生日：<input type="text" name="date"><br>

        <input type="submit" value="提交"><br>
    </form>--%>

    <a href="param/testServlet">servlet 原生的 api</a>
</body>
</html>
