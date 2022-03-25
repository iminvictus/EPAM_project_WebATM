<%--
  Created by IntelliJ IDEA.
  User: invic
  Date: 21.03.2022
  Time: 21:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="models.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Display database</title>
</head>
<body>
<table>
    <tr>
        <th>UserID</th>
        <th>User name</th>
        <th>User surname</th>
        <th>User balance</th>
    </tr>
    <%
        List<User> userList = (List) request.getAttribute("userList");
        for (User user : userList) {    %>
    <tr>
        <td><%=user.getId()%></td>
        <td><%=user.getName()%></td>
        <td><%=user.getSurname()%></td>
        <td><%=user.getBalance()%></td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>
