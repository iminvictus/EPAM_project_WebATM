<%@ page import="models.Transaction" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="services.ApplicationService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Start Page</title>
    <jsp:include page="logout.jsp"></jsp:include>
    <h1>Input account ID</h1>
    <form name="idForm" action="${pageContext.request.contextPath}/status" method="get">

        <table>

            <tbody>

            <tr>
                <td>Account ID:</td>
                <td><input type="text" name="id" value="" size="12"></td>
            </tr>

            </tbody>

        </table>

        <input type="reset" value="Clear" name="clear" />
        <input type="submit" value="Submit" name="submit" />

    </form>

    <h1>Input your ID to deposit money</h1>

    <form name="depositForm" action="${pageContext.request.contextPath}/deposit" method="get">

        <table>

            <tbody>

            <tr>
                <td>ID:</td>
                <td><input type="text" name="id" value="" size="20"></td>
            </tr>

            </tbody>

        </table>

        <input type="reset" value="Clear" name="clear" />
        <input type="submit" value="Submit" name="submit" />

    </form>

    <h1>Input your ID to withdraw money</h1>

    <form name="withdrawForm" action="${pageContext.request.contextPath}/withdraw" method="get">

        <table>

            <tbody>

            <tr>
                <td>ID:</td>
                <td><input type="text" name="id_withdrawAmount" value="" size="20"></td>
            </tr>

            </tbody>

        </table>

        <input type="reset" value="Clear" name="clear" />
        <input type="submit" value="Submit" name="submit" />

    </form>
        <table>
            <tr>
                <th>TransactionID</th>
                <th>UserID</th>
                <th>Transaction type</th>
                <th>Transaction amount</th>
                <th>Time</th>
            </tr>
            <%
                ApplicationService applicationService = new ApplicationService();
                List<Transaction> transactionsList = applicationService.getAllTransactions();
                List<Transaction> last20List = transactionsList.stream()
                        .sorted(Comparator.comparingLong(Transaction::getId).reversed())
                        .collect(Collectors.toList());

                for (int i = 0; i<20; i++) {    %>
            <tr>
                <td><%=last20List.get(i).getId()%></td>
                <td><%=last20List.get(i).getUserid()%></td>
                <td><%=last20List.get(i).getType()%></td>
                <td><%=last20List.get(i).getAmount()%></td>
                <td><%=last20List.get(i).getTime()%></td>
            </tr>
            <%
                }
            %>
        </table>

    </form>

</head>


<body>

</body>
</html>
