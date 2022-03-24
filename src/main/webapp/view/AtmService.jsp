<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ page import = "models.User"%>
<%@ page import="java.math.BigDecimal" %>


<%
    User user = (User) request.getAttribute("user");
    long account = user.getId();
    BigDecimal balance = user.getBalance();
    String userName = user.getName() + " " + user.getSurname();
%>

<%=
"<p> User name: " + userName + "</p>" +
        "<p> Bank account: " + account + "</p>" +
        "<p> Balance: " + balance.toString() + "</p>"
%>

</body>
</html>
