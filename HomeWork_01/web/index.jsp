<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- 项目首页进入主页，未登录时由 LoginFilter 跳转到登录页。 --%>
<%
    response.sendRedirect(request.getContextPath() + "/memo/list");
%>
