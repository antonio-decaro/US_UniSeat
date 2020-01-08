<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 07/01/2020
  Time: 15:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType=
                 "text/html;charset=UTF-8" pageEncoding="UTF-8" %><!DOCTYPE html>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Profilo</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta name="keywords">
    <meta name="description">

    <!-- Favicons -->
    <link href="${pageContext.request.contextPath}/img/favicon.png" rel="icon">
    <link href="${pageContext.request.contextPath}/img/apple-touch-icon.png" rel="apple-touch-icon">

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,700,700i|Poppins:300,400,500,700"
          rel="stylesheet">

    <!-- Bootstrap CSS File -->
    <link href="${pageContext.request.contextPath}/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Libraries CSS Files -->
    <link href="${pageContext.request.contextPath}/lib/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/lib/animate/animate.min.css" rel="stylesheet">

    <!-- Main Stylesheet File -->
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>

<body>
<%@ include file="header.jsp" %>
<!--==========================
Header
============================-->

<section id="hero">
    <div class="hero-container">
        <div class="container  wow fadeInUp">
            <br>
            <div id="contact">
                <h3 class="section-title">Impostazioni</h3>
                <div class="container wow fadeInUp mt-5">
                    <div class="row justify-content-center">
                        <div class="col-lg-4 col-md-4">
                            <div class="form">
                                <div id="exercitationrormessage"></div>
                                <form action="" method="post" role="form" class="contactForm">
                                    <div class="form-group">
                                        <span class="input"><%=u.getNome()%></span>
                                    </div>
                                    <div class="form-group">
                                        <span class="input"><%=u.getCognome()%></span>
                                    </div>
                                    <div class="form-group">
                                        <span class="input"><%=u.getEmail()%>></span>
                                    </div>
                                    <div class="form-group">
                                        <span class="input" type="password"><%=u.getPassword()%>></span>
                                    </div>
                                    <div class="form-group">
                                        <span class="input" type="Password">Verifica Password</span>
                                    </div>
                                </form>
                            </div>
                        </div>

                    </div>
                </div>
                <div class="form form-group toggle"><button type="submit">Modifica</button></div>
            </div>

        </div>
    </div>
</section>
<%@ include file="contattaci.jsp" %>

<%@ include file="footer.jsp" %>
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
<script src="${pageContext.request.contextPath}/js/edifici.js"></script>

</body>
</html>


