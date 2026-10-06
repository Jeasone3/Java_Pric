<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.memo.model.Memo,com.example.memo.util.HtmlUtil" %>
<%
    String contextPath = request.getContextPath();
    String error = (String) request.getAttribute("error");
    String message = (String) request.getAttribute("message");
    Memo memo = (Memo) request.getAttribute("memo");
    // 表单检查失败时显示刚才的输入，否则显示数据库中保存的内容。
    String title = request.getAttribute("title") != null
            ? (String) request.getAttribute("title") : (memo == null ? "" : memo.getTitle());
    String content = request.getAttribute("content") != null
            ? (String) request.getAttribute("content") : (memo == null ? "" : memo.getContent());
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>备忘录详情</title>
    <link rel="stylesheet" href="<%= contextPath %>/css/style.css">
</head>
<body>
<main class="container form-box">
    <h1>备忘录详情</h1>
    <p><a href="<%= contextPath %>/memo/list">返回主页</a></p>
    <% if (error != null) { %>
    <p class="notice error" role="alert"><%= HtmlUtil.escape(error) %></p>
    <% } %>
    <% if (message != null) { %>
    <p class="notice"><%= HtmlUtil.escape(message) %></p>
    <% } %>
    <% if (memo != null) { %>
    <p class="muted">
        创建时间：<%= memo.getCreatedAt() == null ? "" : HtmlUtil.escape(memo.getCreatedAt().toString()) %><br>
        修改时间：<%= memo.getUpdatedAt() == null ? "" : HtmlUtil.escape(memo.getUpdatedAt().toString()) %>
    </p>
    <form action="<%= contextPath %>/memo/update" method="post">
        <input type="hidden" name="id" value="<%= memo.getId() %>">
        <label for="title">标题</label>
        <input id="title" name="title" type="text" maxlength="120"
               value="<%= HtmlUtil.escape(title) %>" required>

        <label for="content">正文</label>
        <textarea id="content" name="content" rows="12" maxlength="10000" required><%= HtmlUtil.escape(content) %></textarea>
        <p class="muted">可直接修改内容，完成后点击“保存修改”。</p>
        <div class="toolbar">
            <button type="submit">保存修改</button>
            <a class="button secondary" href="<%= contextPath %>/memo/list">返回主页</a>
        </div>
    </form>
    <% } else { %>
    <p class="empty">没有找到这条备忘录。</p>
    <% } %>
</main>
</body>
</html>
