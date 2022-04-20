<%@ page import="models.User" %>
<%@ page import="models.Card" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="services.ApplicationService" %>
<!DOCTYPE html>
<html style="font-size: 16px;">
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <meta name="keywords" content="Transaction History">
    <meta name="description" content="">
    <meta name="page_type" content="np-template-header-footer-from-plugin">
    <title>Home</title>
    <link rel="stylesheet" href="/view/nicepage.css" media="screen">
<link rel="stylesheet" href="/view/Home.css" media="screen">
    <script class="u-script" type="text/javascript" src="/view/jquery.js" defer=""></script>
    <script class="u-script" type="text/javascript" src="/view/nicepage.js" defer=""></script>
    <meta name="generator" content="Nicepage 4.7.1, nicepage.com">
    <link id="u-theme-google-font" rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:100,100i,300,300i,400,400i,500,500i,700,700i,900,900i|Open+Sans:300,300i,400,400i,500,500i,600,600i,700,700i,800,800i">
    <link id="u-page-google-font" rel="stylesheet" href="https://fonts.googleapis.com/css?family=Berkshire+Swash:400">
    
    
    <script type="application/ld+json">{
		"@context": "http://schema.org",
		"@type": "Organization",
		"name": ""
}</script>
    <meta name="theme-color" content="#478ac9">
    <meta property="og:title" content="Home">
    <meta property="og:type" content="website">
  </head>
  <body class="u-body u-xl-mode"><header class="u-clearfix u-gradient u-header u-header" id="sec-5f50"><div class="u-clearfix u-sheet u-sheet-1">
        <h2 class="u-align-center u-custom-font u-text u-text-default u-text-1">Uranus Bank</h2>
      </div></header> 
    <section class="u-clearfix u-image u-section-1" id="sec-4014" data-image-width="2000" data-image-height="1000">
        <%User user = (User) request.getSession().getAttribute("user");
            Card card = (Card) request.getSession().getAttribute("approvedCard");
            ApplicationService applicationService = new ApplicationService();
            BigDecimal balance = applicationService.getCardById(card.getId()).getBalance();
        %>
        <div class="u-align-left u-clearfix u-sheet u-sheet-1">
        <a href="/view/Withdraw.jsp" data-page-id="160218685" class="u-border-none u-btn u-btn-round u-button-style u-gradient u-hover-palette-1-light-1 u-none u-radius-20 u-btn-1">Withdraw</a>
        <h5 class="u-align-left u-custom-font u-font-arial u-text u-text-1">Welcome,<br><%=user.getName() +" " + user.getSurname() +" "%><br>Card number= <%=card.getAccount()%><br>Balance:<br><%=balance%>
        </h5>
        <a href="/view/Deposit.jsp" data-page-id="57458714" class="u-border-none u-btn u-btn-round u-button-style u-gradient u-hover-palette-1-light-1 u-none u-radius-20 u-btn-2">Deposit</a>
        <a href="/view/Transfer.jsp" data-page-id="566710825" class="u-border-none u-btn u-btn-round u-button-style u-gradient u-hover-palette-1-light-1 u-none u-radius-20 u-btn-3">Transfer</a>
        <a href="/view/Settings.jsp" data-page-id="54092570" class="u-border-none u-btn u-btn-round u-button-style u-gradient u-hover-palette-1-light-1 u-none u-radius-20 u-btn-4">Security</a>
        <a href="/" data-page-id="49464255" class="u-border-none u-btn u-btn-round u-button-style u-gradient u-hover-palette-1-light-1 u-none u-radius-20 u-btn-5">Payments</a>
        <a href="/history" data-page-id="836202084" class="u-border-none u-btn u-btn-round u-button-style u-gradient u-hover-palette-1-light-1 u-none u-radius-20 u-btn-6">Transactions</a>
        <a href="/lastTransactions" data-page-id="54092571" class="u-border-none u-btn u-btn-round u-button-style u-gradient u-hover-palette-1-light-1 u-none u-radius-20 u-btn-7">Last 20 transactions</a>
        <a href="/allTransactionsByUser" data-page-id="54092571" class="u-border-none u-btn u-btn-round u-button-style u-gradient u-hover-palette-1-light-1 u-none u-radius-20 u-btn-7">Last transactions by user</a>
        <p class="u-align-left u-text u-text-2">Transaction action result : _________________________________________</p>
        <a href="/logout" class="u-btn u-btn-round u-button-style u-hover-palette-1-light-1 u-palette-1-base u-radius-6 u-btn-8">Return card</a>
      </div>
    </section>
  </body>
</html>