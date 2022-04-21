<%@ page import="models.Transaction" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="java.time.format.FormatStyle"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html style="font-size: 16px;">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <meta name="keywords" content="Uranus Bank, Transaction history">
    <meta name="description" content="">
    <meta name="page_type" content="np-template-header-footer-from-plugin">
    <title>History</title>
    <link rel="stylesheet" href="/view/nicepage.css" media="screen">
    <link rel="stylesheet" href="/view/Home.css" media="screen">
    <script class="u-script" type="text/javascript" src="/view/jquery.js" defer=""></script>
    <script class="u-script" type="text/javascript" src="/view/nicepage.js" defer=""></script>
    <meta name="generator" content="Nicepage 4.7.1, nicepage.com">
    <link id="u-theme-google-font" rel="stylesheet"
          href="https://fonts.googleapis.com/css?family=Roboto:100,100i,300,300i,400,400i,500,500i,700,700i,900,900i|Open+Sans:300,300i,400,400i,500,500i,600,600i,700,700i,800,800i">
    <link id="u-page-google-font" rel="stylesheet" href="https://fonts.googleapis.com/css?family=Berkshire+Swash:400">


    <script type="application/ld+json">{
        "@context": "http://schema.org",
        "@type": "Organization",
        "name": ""
    }</script>
    <meta name="theme-color" content="#478ac9">
    <meta property="og:title" content="History">
    <meta property="og:type" content="website">
</head>
<body class="u-body u-xl-mode">
<header class="u-clearfix u-gradient u-header u-header" id="sec-5f50">
    <div class="u-clearfix u-sheet u-sheet-1">
        <h2 class="u-align-center u-custom-font u-text u-text-default u-text-1">Uranus Bank</h2>
    </div>
</header>

<% List<Transaction> transactions = (List<Transaction>) request.getAttribute("transactions");%>
<section class="u-clearfix u-image u-section-1" id="sec-28a1" data-image-width="2000" data-image-height="1000">
    <div class="u-align-left u-clearfix u-sheet u-sheet-1">
        <h5 class="u-align-left u-custom-font u-font-arial u-text u-text-2"><p class="u-text u-text-2"></p>
                <table>
                    <tr>
                        <th>TransactionID</th>
                        <th>UserID</th>
                        <th>Transaction type</th>
                        <th>Transaction amount</th>
                        <th>Time</th>
                    </tr>
                    <%  DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
                        for (Transaction t: transactions) {%>
                    <tr>
                        <td><%=t.getId()%></td>
                        <td><%=t.getUserid()%></td>
                        <td><%=t.getType()%></td>
                        <td><%=t.getAmount()%></td>
                        <td><%=t.getTime().format(formatter)%></td>
                   <% } %>
                </table>

            </form>
        </h5>
        <a href="/view/HomeAdmin.jsp" data-page-id="49464255" class="u-border-none u-btn u-btn-round u-button-style u-gradient u-hover-palette-1-light-1 u-none u-radius-20 u-btn-1">Home</a>
    </div>
</section>

</body>
</html>