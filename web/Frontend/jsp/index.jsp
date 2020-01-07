<%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 03/01/2020
  Time: 12:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType=
                 "text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Index</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta name="keywords">
    <meta name="description">

    <!-- Favicons -->
    <link href="${pageContext.request.contextPath}/Frontend/img/favicon.png" rel="icon">
    <link href="${pageContext.request.contextPath}/Frontend/img/apple-touch-icon.png" rel="apple-touch-icon">

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,700,700i|Poppins:300,400,500,700"
          rel="stylesheet">

    <!-- Bootstrap CSS File -->
    <link href="${pageContext.request.contextPath}/Frontend/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Libraries CSS Files -->
    <link href="${pageContext.request.contextPath}/Frontend/lib/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/Frontend/lib/animate/animate.min.css" rel="stylesheet">

    <!-- Main Stylesheet File -->
    <link href="${pageContext.request.contextPath}/Frontend/css/style.css" rel="stylesheet">
</head>
<body>
<%@ include file="header.jsp" %>
<section id="hero">
    <div class="hero-container">

        <% if (u == null) { %>

        <h1>Benvenuto</h1>
        <a href="${pageContext.request.contextPath}/Frontend/jsp/login.jsp" class="btn-get-started">Accedi</a>

        <% } else { %>

        <h1>Ciao, ${u.nome}</h1>

        <% } %>
    </div>
</section>
<section id="facts">
    <div class="container wow fadeIn">
        <div class="section-header">
            <h3 class="section-title">Disponibilità</h3>
        </div>
        <br>
        <div class="row counters">
            <div class="col-lg-3 col-6 text-center">
                <span data-toggle="counter-up">3</span>
                <p>Edifici</p>
            </div>
            <div class="col-lg-3 col-6 text-center">
                <span data-toggle="counter-up">65</span>
                <p>Aule</p>
            </div>
            <div class="col-lg-3 col-6 text-center">
                <span data-toggle="counter-up">1,463</span>
                <p>Posti</p>
            </div>
            <div class="col-lg-3 col-6 text-center">
                <span data-toggle="counter-up">456</span>
                <p>Computer</p>
            </div>
        </div>
    </div>
</section>
<section id="services">
    <div class="container wow fadeIn">
        <div class="section-header">
            <h3 class="section-title">Scegli dove studiare</h3>
            <p class="section-description">Prenotare posti non è mai stato così semplice con UniSeat!</p>
        </div>

        <div class="row counters">
            <div class="col-lg-4 col-md-6 wow fadeInUp" data-wow-delay="0.2s">
                <div class="box">
                    <div class="icon"><i class="fa fa-building"></i></div>
                    <h4 class="title">Edificio F</h4>
                    <div class="counters">200 posti disponibili</div>
                    <div>
                        <br>
                        <button type="button" class="btn btn-primary"><a href="aule.html">Prenota</a></button>
                    </div>
                </div>
            </div>
            <div class="col-lg-4 col-md-6 wow fadeInUp" data-wow-delay="0.3s">
                <div class="box">
                    <div class="icon"><i class="fa fa-building"></i></div>
                    <h4 class="title">Edificio F2</h4>
                    <div class="counters">450 posti disponibili</div>
                    <div>
                        <br>
                        <button type="button" class="btn btn-primary"><a href="aule.html">Prenota</a></button>
                    </div>
                </div>
            </div>
            <div class="col-lg-4 col-md-6 wow fadeInUp" data-wow-delay="0.4s">
                <div class="box">
                    <div class="icon"><i class="fa fa-building"></i></div>
                    <h4 class="title">Edificio F3</h4>
                    <div class="counters">800 posti disponibili</div>
                    <div>
                        <br>
                        <button type="button" class="btn btn-primary"><a href="aule.html">Prenota</a></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<%@ include file="contattaci.jsp" %>

<%@ include file="footer.jsp" %>
<!-- JavaScript Libraries -->
<script src="${pageContext.request.contextPath}/Frontend/lib/jquery/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/Frontend/lib/jquery/jquery-migrate.min.js"></script>
<script src="${pageContext.request.contextPath}/Frontend/lib/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/Frontend/lib/easing/easing.min.js"></script>
<script src="${pageContext.request.contextPath}/Frontend/lib/wow/wow.min.js"></script>
<script src="${pageContext.request.contextPath}/Frontend/lib/waypoints/waypoints.min.js"></script>
<script src="${pageContext.request.contextPath}/Frontend/lib/counterup/counterup.min.js"></script>
<script src="${pageContext.request.contextPath}/Frontend/lib/superfish/hoverIntent.js"></script>
<script src="${pageContext.request.contextPath}/Frontend/lib/superfish/superfish.min.js"></script>
<!-- Contact Form JavaScript File -->
<script src="${pageContext.request.contextPath}/Frontend/contactform/contactform.js"></script>
<!-- Template Main Javascript File -->
<script src="${pageContext.request.contextPath}/Frontend/js/main.js"></script>
<script src="${pageContext.request.contextPath}/Frontend/js/edifici.js"></script>

</body>
</html>