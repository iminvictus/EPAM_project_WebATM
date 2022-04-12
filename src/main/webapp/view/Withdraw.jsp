<%@ page import="models.User" %>
<!DOCTYPE html>
<html style="font-size: 16px;">
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <meta name="keywords" content="Uranus Bank, Withdraw">
    <meta name="description" content="">
    <meta name="page_type" content="np-template-header-footer-from-plugin">
    <title>Withdraw</title>
    <link rel="stylesheet" href="/view/nicepage.css" media="screen">
<link rel="stylesheet" href="/view/Withdraw.css" media="screen">
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
    <meta property="og:title" content="Withdraw">
    <meta property="og:type" content="website">
  </head>
  <body class="u-body u-xl-mode"><header class="u-clearfix u-gradient u-header u-header" id="sec-5f50"><div class="u-clearfix u-sheet u-sheet-1">
        <h2 class="u-align-center u-custom-font u-text u-text-default u-text-1">Uranus Bank</h2>
      </div></header> 
    <section class="u-clearfix u-image u-section-1" id="sec-191c" data-image-width="2000" data-image-height="1000">
      <div class="u-align-left u-clearfix u-sheet u-sheet-1">
        <h1 class="u-text u-text-default u-text-1">Withdraw</h1>
        <div class="u-form u-form-1">
            <%User user = (User) request.getSession().getAttribute("user");%>
          <form action="/withdraw?id=${user.id}" method="POST" class="u-clearfix u-form-spacing-10 u-form-vertical u-inner-form" source="custom" name="form" style="padding: 10px;">
            <div class="u-form-group u-form-name">
              <label for="name-d860" class="u-label">Amount</label>
              <input type="text" placeholder="Withdraw amount" id="name-d860" name="amount" class="u-border-1 u-border-grey-30 u-input u-input-rectangle u-white">
            </div>
              <div class="u-align-right u-form-group u-form-submit">
                  <input type="submit" value="Withdraw" class="u-btn u-btn-submit u-button-style">
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