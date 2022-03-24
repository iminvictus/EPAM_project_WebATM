<%@ page import="java.util.List" %>
<%@ page import="models.User" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Deposit money</title>
</head>
<body>
<table>
    <tr>
        <th>User name | </th>
        <th>User surname | </th>
        <th>User balance | </th>
        <th>Enter amount of money that you want to deposit</th>
    </tr>
    <%
      User user = (User) request.getAttribute("user");
      %>
    <form action="deposit" method="post"><tr>
            <td><input name="id" type="hidden" value="<%=user.getId()%>"><%=user.getName()%></td>
            <td><%=user.getSurname()%></td>
            <td><%=user.getBalance()%></td>
            <td><input name="amount" type="text" placeholder="input an amount"></td>
            <td><input type="submit" value="Deposit"></td>
    </tr></form>
</table>
</body>
</html>