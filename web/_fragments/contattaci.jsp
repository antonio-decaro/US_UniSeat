<%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 04/01/2020
  Time: 20:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"  %>
<html>
<head>
    <meta charset="utf-8">
    <title>Contattaci Uniseat</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="keywords">
    <meta content="" name="description">

    <!-- Favicons -->
    <link href="${pageContext.request.contextPath}/img/favicon.png" rel="icon">
    <link href="${pageContext.request.contextPath}/img/apple-touch-icon.png" rel="apple-touch-icon">

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,700,700i|Poppins:300,400,500,700" rel="stylesheet">

    <!-- Bootstrap CSS File -->
    <link href="${pageContext.request.contextPath}/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Libraries CSS Files -->
    <link href="${pageContext.request.contextPath}/lib/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/lib/animate/animate.min.css" rel="stylesheet">

    <!-- Main Stylesheet File -->
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body>
<section id="contact">
    <div class="container wow fadeInUp">
        <div class="section-header">
            <h3 class="section-title">Problemi?</h3>
            <p class="section-description">Se riscontri qualche problema contattaci compilando l'apposito modulo
                qui sotto.</p>
        </div>
    </div>

    <div class="container wow fadeInUp mt-5">
        <div class="row justify-content-center">

            <div class="col-lg-3 col-md-4">

                <div class="info">
                    <div>
                        <i class="fa fa-map-marker"></i>
                        <p>Università degli Studi di Salerno - Via Giovanni Paolo II<br>Fisciano, SA 84084</p>
                    </div>

                    <div>
                        <i class="fa fa-envelope"></i>
                        <p>info@uniseat.com</p>
                    </div>

                    <div>
                        <i class="fa fa-phone"></i>
                        <p>+1 333 555 3322</p>
                    </div>
                </div>

                <div class="social-links">
                    <a href="#" class="twitter"><i class="fa fa-twitter"></i></a>
                    <a href="#" class="facebook"><i class="fa fa-facebook"></i></a>
                    <a href="#" class="instagram"><i class="fa fa-instagram"></i></a>
                    <a href="#" class="google-plus"><i class="fa fa-google-plus"></i></a>
                    <a href="#" class="linkedin"><i class="fa fa-linkedin"></i></a>
                </div>

            </div>

            <div class="col-lg-5 col-md-8">
                <div class="form">
                    <div id="sendmessage">Il tuo messaggio è stato inviato!</div>
                    <div id="exercitationrormessage"></div>
                    <form action="" method="post" role="form" class="contactForm">
                        <div class="form-group">
                            <input type="text" name="name" class="form-control" id="name" placeholder="Nome"
                                   data-rule="minlen:4" data-msg="Inserisci almeno 4 caratteri"/>
                            <div class="validation"></div>
                        </div>
                        <div class="form-group">
                            <input type="email" class="form-control" name="email" id="email" placeholder="Email"
                                   data-rule="email" data-msg="Inserisci un'email valida"/>
                            <div class="validation"></div>
                        </div>
                        <div class="form-group">
                            <input type="text" class="form-control" name="subject" id="subject"
                                   placeholder="Oggetto" data-rule="minlen:4"
                                   data-msg="Inserisci almeno 8 caratteri nell'oggetto"/>
                            <div class="validation"></div>
                        </div>
                        <div class="form-group">
                                    <textarea class="form-control" name="message" rows="5" data-rule="required"
                                              data-msg="Scrivi qualcosa qui" placeholder="Messaggio"></textarea>
                            <div class="validation"></div>
                        </div>
                        <div class="text-center">
                            <button type="submit">Invia messaggio</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- JavaScript Libraries -->
<script src="${pageContext.request.contextPath}/lib/jquery/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/lib/jquery/jquery-migrate.min.js"></script>
<script src="${pageContext.request.contextPath}/lib/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/lib/easing/easing.min.js"></script>
<script src="${pageContext.request.contextPath}/lib/wow/wow.min.js"></script>
<script src="${pageContext.request.contextPath}/lib/waypoints/waypoints.min.js"></script>
<script src="${pageContext.request.contextPath}/lib/counterup/counterup.min.js"></script>
<script src="${pageContext.request.contextPath}/lib/superfish/hoverIntent.js"></script>
<script src="${pageContext.request.contextPath}/lib/superfish/superfish.min.js"></script>
<!-- Contact Form JavaScript File -->
<script src="${pageContext.request.contextPath}/contactform/contactform.js"></script>
<!-- Template Main Javascript File -->
<script src="${pageContext.request.contextPath}/js/main.js"></script>

</body>
</html>
