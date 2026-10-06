<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.memo.util.HtmlUtil" %>
<%
    String contextPath = request.getContextPath();
    String error = (String) request.getAttribute("error");
    String title = (String) request.getAttribute("title");
    String content = (String) request.getAttribute("content");
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>发布备忘录</title>
    <link rel="stylesheet" href="<%= contextPath %>/css/style.css">
</head>
<body>
<main class="container form-box">
    <h1>发布备忘录</h1>
    <p><a href="<%= contextPath %>/memo/list">返回主页</a></p>
    <% if (error != null) { %>
    <p class="notice error" role="alert"><%= HtmlUtil.escape(error) %></p>
    <% } %>
    <form action="<%= contextPath %>/memo/add" method="post">
        <label for="title">标题</label>
        <input id="title" name="title" type="text" maxlength="120"
               value="<%= HtmlUtil.escape(title) %>" required>

        <label for="content">正文</label>
        <textarea id="content" name="content" rows="12" maxlength="10000" required><%= HtmlUtil.escape(content) %></textarea>
        <p class="muted">标题最多 120 个字符，正文最多 10000 个字符。</p>
        <div class="toolbar">
            <button type="submit">发布</button>
            <a class="button secondary" href="<%= contextPath %>/memo/list">取消</a>
        </div>
    </form>
</main>
</body>
</html>
