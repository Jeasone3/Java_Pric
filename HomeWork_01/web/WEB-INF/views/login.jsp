<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.memo.util.HtmlUtil" %>
<%
    String contextPath = request.getContextPath();
    String error = (String) request.getAttribute("error");
    String username = (String) request.getAttribute("username");
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>登录 - 备忘录</title>
    <link rel="stylesheet" href="<%= contextPath %>/css/style.css">
</head>
<body>
<main class="container login-box">
    <h1>备忘录</h1>
    <p class="muted">登录后可以发布、修改和管理自己的备忘录。</p>
    <% if (error != null) { %>
    <p class="notice error" role="alert"><%= HtmlUtil.escape(error) %></p>
    <% } %>
    <form action="<%= contextPath %>/login" method="post">
        <label for="username">用户名</label>
        <input id="username" name="username" type="text" maxlength="50"
               value="<%= HtmlUtil.escape(username) %>" autocomplete="username" required>

        <label for="password">密码</label>
        <%-- 登录失败时仅保留用户名，不回显密码。 --%>
        <input id="password" name="password" type="password" maxlength="255"
               autocomplete="current-password" required>
        <button type="submit">登录</button>
    </form>
</main>
</body>
</html>
