<%@ page import="java.util.List" %>
<%@ page import="models.User" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Display database</title>
</head>
<body>
<table>
    <tr>
        <th>User name</th>
        <th>User surname</th>
        <th>User balance</th>
    </tr>
    <%
      List<User> userList = (List) request.getAttribute("userList");
      for (User user : userList) {    %>
        <tr>
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