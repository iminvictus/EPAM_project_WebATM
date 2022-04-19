<!DOCTYPE html>
<html style="font-size: 16px;">
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <meta name="page_type" content="np-template-header-footer-from-plugin">
    <title>LoginPage</title>
    <link rel="stylesheet" href="view/nicepage.css" media="screen">
<link rel="stylesheet" href="view/LoginPage.css" media="screen">
    <script class="u-script" type="text/javascript" src="jquery.js" defer=""></script>
    <script class="u-script" type="text/javascript" src="nicepage.js" defer=""></script>
    <meta name="generator" content="Nicepage 4.7.1, nicepage.com">
    <link id="u-theme-google-font" rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:100,100i,300,300i,400,400i,500,500i,700,700i,900,900i|Open+Sans:300,300i,400,400i,500,500i,600,600i,700,700i,800,800i">
    <link id="u-page-google-font" rel="stylesheet" href="https://fonts.googleapis.com/css?family=Architects+Daughter:400">
    
    
    <script type="application/ld+json">{
		"@context": "http://schema.org",
		"@type": "Organization",
		"name": ""
}</script>
    <meta name="theme-color" content="#478ac9">
    <meta property="og:title" content="LoginPage">
    <meta property="og:type" content="website">
  </head>
  <body class="u-body u-xl-mode"><header class="u-clearfix u-gradient u-header u-header" id="sec-5f50"><div class="u-clearfix u-sheet u-sheet-1">
      <h2 class="u-align-center u-custom-font u-text u-text-default u-text-1">Uranus Bank</h2>
  </div></header>
  <section class="u-clearfix u-image u-section-1" id="sec-2be8" data-image-width="2000" data-image-height="1000">
      <div class="u-clearfix u-sheet u-valign-middle u-sheet-1">
        <div class="u-border-1 u-border-grey-75 u-form u-gradient u-login-control u-radius-36 u-form-1">
          <form action="#" method="POST" class="u-clearfix u-form-custom-backend u-form-spacing-9 u-form-vertical u-inner-form" source="custom" name="form" style="padding: 11px;">
            <div class="u-form-group u-form-name">
                <p style="color: red;">${errorMessage}</p>
              <label for="username-a30d" class="u-label">Card number *</label>
              <input type="text" placeholder="Enter your card number" id="username-a30d" name="account" class="u-border-grey-30 u-input u-input-rectangle u-input-1" required="">
            </div>
            <div class="u-form-group u-form-password">
              <label for="password-a30d" class="u-label">PIN *</label>
              <input type="text" placeholder="Enter your Password" id="password-a30d" name="pincode" class="u-border-grey-30 u-input u-input-rectangle u-input-2" required="">
            </div>
             <div class="u-align-left u-form-group u-form-submit">
              <input type="submit" value="Login" class="u-btn u-btn-round u-btn-submit u-button-style u-radius-20">
            </div>
            <input type="hidden" value="" name="recaptchaResponse">
          </form>
        </div>
      </div>
    </section>
  </body>
</html>