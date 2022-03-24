
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Start Page</title>
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

    <h1>Input deposit amount</h1>

    <form name="depositForm" action="${pageContext.request.contextPath}/status" method="get">

        <table>

            <tbody>

            <tr>
                <td>Deposit amount:</td>
                <td><input type="text" name="depositAmount" value="" size="20"></td>
            </tr>

            </tbody>

        </table>

        <input type="reset" value="Clear" name="clear" />
        <input type="submit" value="Submit" name="submit" />

    </form>

    <h1>Input withdraw amount</h1>

    <form name="withdrawForm" action="${pageContext.request.contextPath}/status" method="get">

        <table>

            <tbody>

            <tr>
                <td>Withdraw amount:</td>
                <td><input type="text" name="withdrawAmount" value="" size="20"></td>
            </tr>

            </tbody>

        </table>

        <input type="reset" value="Clear" name="clear" />
        <input type="submit" value="Submit" name="submit" />

    </form>
</head>
<body>

</body>
</html>
