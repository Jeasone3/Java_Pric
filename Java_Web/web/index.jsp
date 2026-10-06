<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
 指令标签

  JSP:会用就行，不用深究，企业用不到

  out:文件夹下，编译后的文件
--%>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  <%
    System.out.println("你好");
    for (int i = 0; i < 10;i++){
  %>
  <p>
    你好
  </p>
  <%
    }
  %>
  </body>
</html>
