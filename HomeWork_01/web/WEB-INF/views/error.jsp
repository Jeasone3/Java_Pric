<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.memo.util.HtmlUtil" %>
<%
    String contextPath = request.getContextPath();
    String error = (String) request.getAttribute("error");
    if (error == null) {
        error = "操作暂时无法完成，请返回主页后重试。";
    }
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>操作提示 - 备忘录</title>
    <link rel="stylesheet" href="<%= contextPath %>/css/style.css">
</head>
<body>
<main class="container form-box">
    <h1>操作提示</h1>
    <p class="notice error" role="alert"><%= HtmlUtil.escape(error) %></p>
    <a class="button" href="<%= contextPath %>/memo/list">返回主页</a>
</main>
</body>
</html>
