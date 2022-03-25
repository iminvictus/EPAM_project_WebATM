<%--
  Created by IntelliJ IDEA.
  User: invic
  Date: 21.03.2022
  Time: 21:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="models.Transaction" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>History logs</title>
</head>
<body>
<table>
    <tr>
        <th>UserID</th>
        <th>Transaction type</th>
        <th>Transaction amount</th>
        <th>Time</th>
    </tr>
    <%
        List<Transaction> transactionsList = (List) request.getAttribute("histList");
        for (Transaction transaction : transactionsList) {    %>
    <tr>
        <td><%=transaction.getUserid()%></td>
        <td><%=transaction.getType()%></td>
        <td><%=transaction.getAmount()%></td>
        <td><%=transaction.getTime()%></td>
    </tr>
    <%
        }
    %>
</table>
<header>Transactions of User with ID 1</header>
<table>
    <tr>
        <th>Transaction type</th>
        <th>Transaction amount</th>
        <th>Time</th>
    </tr>
    <%
        List<Transaction> transactionsListByID = (List) request.getAttribute("histListId");
        for (Transaction transaction : transactionsListByID) {    %>
    <tr>
        <td><%=transaction.getType()%></td>
        <td><%=transaction.getAmount()%></td>
        <td><%=transaction.getTime()%></td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>
