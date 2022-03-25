<%--
  Created by IntelliJ IDEA.
  User: invic
  Date: 21.03.2022
  Time: 21:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="models.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Withdraw Page</title>
</head>
<body>
<table>
    <tr>
        <th>User name</th>
        <th>User surname</th>
        <th>User balance</th>
        <th>Amount to withdraw</th>
    </tr>
    <%
        User user = (User) request.getAttribute("user"); %>
    <form action="withdraw" method="post"><tr>
        <td><input name="userId" type="hidden" value="<%=user.getId()%>"><%=user.getName()%></td>
        <td><input name="surname" type="hidden" value="<%=user.getSurname()%>"><%=user.getSurname()%></td>
        <td><input name="balance" type="hidden" value="<%=user.getBalance()%>"><%=user.getBalance()%></td>
        <td><input name="amount" type="number" min="0" step="0.01" placeholder="input an amount"></td>
        <td><input type="submit" value="Approve"></td>
    </tr>
    </form>

</table>
</body>
</html>
