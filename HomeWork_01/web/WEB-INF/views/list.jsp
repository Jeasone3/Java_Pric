<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List,com.example.memo.model.Memo,com.example.memo.util.HtmlUtil" %>
<%
    String contextPath = request.getContextPath();
    String error = (String) request.getAttribute("error");
    String message = (String) request.getAttribute("message");
    List<Memo> memos = (List<Memo>) request.getAttribute("memos");
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>我的备忘录</title>
    <link rel="stylesheet" href="<%= contextPath %>/css/style.css">
</head>
<body>
<main class="container">
    <div class="topbar">
        <h1>我的备忘录</h1>
        <div class="account">
            <span>当前用户：<%= HtmlUtil.escape((String) session.getAttribute("username")) %></span>
            <form action="<%= contextPath %>/logout" method="post" class="inline-form">
                <button type="submit" class="secondary">退出登录</button>
            </form>
        </div>
    </div>
    <nav class="toolbar">
        <a class="button" href="<%= contextPath %>/memo/add">发布备忘录</a>
        <a class="button secondary" href="<%= contextPath %>/memo/recycle">回收站</a>
    </nav>
    <% if (error != null) { %>
    <p class="notice error" role="alert"><%= HtmlUtil.escape(error) %></p>
    <% } %>
    <% if (message != null) { %>
    <p class="notice"><%= HtmlUtil.escape(message) %></p>
    <% } %>
    <% if (memos == null || memos.isEmpty()) { %>
    <p class="empty">还没有备忘录，点击“发布备忘录”创建第一条。</p>
    <% } else { %>
    <div class="table-wrapper">
        <table>
            <thead>
            <tr><th>标题</th><th>创建时间</th><th>修改时间</th><th>操作</th></tr>
            </thead>
            <tbody>
            <% for (Memo memo : memos) { %>
            <tr>
                <td class="memo-title">
                    <a href="<%= contextPath %>/memo/detail?id=<%= memo.getId() %>"><%= HtmlUtil.escape(memo.getTitle()) %></a>
                </td>
                <td><%= memo.getCreatedAt() == null ? "" : HtmlUtil.escape(memo.getCreatedAt().toString()) %></td>
                <td><%= memo.getUpdatedAt() == null ? "" : HtmlUtil.escape(memo.getUpdatedAt().toString()) %></td>
                <td class="actions">
                    <a href="<%= contextPath %>/memo/detail?id=<%= memo.getId() %>">查看 / 修改</a>
                    <form action="<%= contextPath %>/memo/delete" method="post" class="inline-form">
                        <input type="hidden" name="id" value="<%= memo.getId() %>">
                        <button type="submit" class="secondary">移入回收站</button>
                    </form>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>
    <% } %>
</main>
</body>
</html>
