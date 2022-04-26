<!DOCTYPE html>
<html style="font-size: 16px;">
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <meta name="keywords" content="Uranus Bank, Deposit">
    <meta name="description" content="">
    <meta name="page_type" content="np-template-header-footer-from-plugin">
    <title>Transfer</title>
    <link rel="stylesheet" href="/view/nicepage.css" media="screen">
    <link rel="stylesheet" href="/view/Transfer.css" media="screen">
    <script class="u-script" type="text/javascript" src="/view/nicepage.js" defer=""></script>
    <meta name="generator" content="Nicepage 4.7.1, nicepage.com">
    <link id="u-theme-google-font" rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:100,100i,300,300i,400,400i,500,500i,700,700i,900,900i|Open+Sans:300,300i,400,400i,500,500i,600,600i,700,700i,800,800i">
    <link id="u-page-google-font" rel="stylesheet" href="https://fonts.googleapis.com/css?family=Berkshire+Swash:400">
    <style>
      p { color: red; }
    </style>
    
    <script type="application/ld+json">{
		"@context": "http://schema.org",
		"@type": "Organization",
		"name": ""
}</script>
    <meta name="theme-color" content="#478ac9">
    <meta property="og:title" content="Transfer">
    <meta property="og:type" content="website">
  </head>
  <body class="u-body u-xl-mode"><header class="u-clearfix u-gradient u-header u-header" id="sec-5f50"><div class="u-clearfix u-sheet u-sheet-1">
        <h2 style="display: inline-block"  class="u-align-center u-custom-font u-text u-text-default u-text-1">Uranus Bank</h2>
    <h5 style="display: inline-block" class="u-align-center u-custom-font u-font-arial u-text u-text-default u-text-2">
      <a class="u-active-none u-btn u-button-link u-button-style u-hover-none u-none u-text-black u-text-hover-palette-2-base u-btn-1" href="/view/Help.jsp">Need help?</a>
    </h5>
      </div></header>
  <% String error_card = (String) request.getAttribute("error_card");
     String error_amount = (String) request.getAttribute("error_amount");%>
    <section class="u-clearfix u-image u-section-1" id="sec-f1ba" data-image-width="2000" data-image-height="1000">
      <div class="u-align-left u-clearfix u-sheet u-sheet-1">
        <h1 class="u-text u-text-default u-text-1">Transfer</h1>
        <div class="u-form u-form-1">
          <form action="/transfer?id=${approvedCard.id}" method="POST" class="u-clearfix u-form-spacing-10 u-form-vertical u-inner-form" source="custom" name="form-1" style="padding: 10px;">
            <div class="u-form-group u-form-name">
              <label for="name-58d5" class="u-label">Destination account</label>
              <input type="text" placeholder="Account number" id="name-58d5" name="dest_account" class="u-border-1 u-border-grey-30 u-input u-input-rectangle u-white" required="">
              <% if (error_card != null) { %> <p><%=error_card%></p> <% } %>
              <% if (error_amount != null) { %> <p><%=error_amount%></p> <% } %>
            </div>
            <div class="u-form-email u-form-group">
              <label for="email-58d5" class="u-label">Amount</label>
              <input type="number" placeholder="Amount" id="email-58d5" name="amount" class="u-border-1 u-border-grey-30 u-input u-input-rectangle u-white" required="">
            </div>
            <div class="u-align-right u-form-group u-form-submit">
              <input type="submit" value="Send" class="u-btn u-btn-submit u-button-style u-btn-width-default">
              <br>
              <br>
              <a href="/logout" class="u-btn u-btn-submit u-button-style u-btn-width-default">Return card</a>
            </div>
            <div class="u-form-send-message u-form-send-success"> Thank you! Your message has been sent. </div>
            <div class="u-form-send-error u-form-send-message"> Unable to send your message. Please fix errors then try again. </div>
            <input type="hidden" value="" name="recaptchaResponse">
          </form>
        </div>
      </div>
    </section>
  </body>
</html>