<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Title</title>
  </head>
  <body>
  <%
    System.out.println(session.getAttribute("username"));

    String username = request.getParameter("username");
    String password = request.getParameter("password");


    if (username != null && password != null) {
        //存入session
        session.setAttribute("username", username);

        //重定向到首页
        response.sendRedirect("index.jsp");
    }
   %>
    <form action="/login" method="post" enctype="multipart/form-data">
      <input type="text" , placeholder="username" name="username"><br>
      <input type="password" , placeholder="password" name="password"><br>

      <button > 登录 </button>
    </form>
  </body>
</html>
