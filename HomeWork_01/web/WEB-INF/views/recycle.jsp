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
    <title>回收站 - 备忘录</title>
    <link rel="stylesheet" href="<%= contextPath %>/css/style.css">
</head>
<body>
<main class="container">
    <div class="topbar">
        <h1>回收站</h1>
        <div class="account">
            <span>当前用户：<%= HtmlUtil.escape((String) session.getAttribute("username")) %></span>
            <form action="<%= contextPath %>/logout" method="post" class="inline-form">
                <button type="submit" class="secondary">退出登录</button>
            </form>
        </div>
    </div>
    <p><a href="<%= contextPath %>/memo/list">返回主页</a></p>
    <p class="muted">还原后，备忘录会重新出现在主页。彻底删除后无法还原。</p>
    <% if (error != null) { %>
    <p class="notice error" role="alert"><%= HtmlUtil.escape(error) %></p>
    <% } %>
    <% if (message != null) { %>
    <p class="notice"><%= HtmlUtil.escape(message) %></p>
    <% } %>
    <% if (memos == null || memos.isEmpty()) { %>
    <p class="empty">回收站中没有备忘录。</p>
    <% } else { %>
    <div class="table-wrapper">
        <table>
            <thead>
            <tr><th>标题</th><th>创建时间</th><th>修改时间</th><th>操作</th></tr>
            </thead>
            <tbody>
            <% for (Memo memo : memos) { %>
            <tr>
                <td class="memo-title"><%= HtmlUtil.escape(memo.getTitle()) %></td>
                <td><%= memo.getCreatedAt() == null ? "" : HtmlUtil.escape(memo.getCreatedAt().toString()) %></td>
                <td><%= memo.getUpdatedAt() == null ? "" : HtmlUtil.escape(memo.getUpdatedAt().toString()) %></td>
                <td class="actions">
                    <form action="<%= contextPath %>/memo/restore" method="post" class="inline-form">
                        <input type="hidden" name="id" value="<%= memo.getId() %>">
                        <button type="submit" class="secondary">还原</button>
                    </form>
                    <form action="<%= contextPath %>/memo/remove" method="post" class="inline-form"
                          onsubmit="return confirm('确定彻底删除这条备忘录吗？删除后无法还原。');">
                        <input type="hidden" name="id" value="<%= memo.getId() %>">
                        <button type="submit" class="danger">彻底删除</button>
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
