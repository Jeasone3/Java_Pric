<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Title</title>
  </head>
  <body>
    <%
        String username = session.getAttribute("username") != null ? session.getAttribute("username").toString() : "";
    %>
    <h1>
    hello <%= username %>
    </h1>
  </body>
</html>
