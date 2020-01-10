<%@ page import="control.utili.SessionManager" %>
<%@ page import="model.pojo.Utente" %>
<%@ page import="model.pojo.TipoUtente" %>
<%@ page import="model.dao.UtenteDAO" %>
<%@ page import="model.database.DBUtenteDAO" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Gianluca Spinelli
  Date: 09/01/2020
  Time: 10:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Utente u = SessionManager.getUtente(session);
    String errorMessage = null;
    errorMessage = SessionManager.getError(session);
    if (errorMessage != null) {
        SessionManager.cleanError(session);
    }
    if (u == null) {
        response.sendRedirect(request.getContextPath() + "/_comuni/login.jsp");
    }
    if (!u.getTipoUtente().equals(TipoUtente.ADMIN)) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
%>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>UniSeat</title>
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

<!--==========================
Header
============================-->
<%@ include file="/_fragments/header.jsp" %>


<section id="hero">
    <div class="hero-container">
        <div class="container  wow fadeInUp">
            <br>
            <div id="contact">
                <h3 class="section-title">Inserisci Aula</h3>
                <div class="container wow fadeInUp mt-5">
                    <div class="row justify-content-center">
                        <div class="col-lg-4 col-md-4">
                            <div class="form">
                                <div id="exercitationrormessage"></div>
                                <form method="post" role="form" name="formInsA" class="contactForm">
                                    <div class="form-group">
                                        <input type="text" name="edificio" class="form-control" id="edAula" placeholder="Edificio">

                                    </div>
                                    <div class="form-group">
                                        <input type="text" name="nome_aula" class="form-control" id="nomeAula" placeholder="Nome">

                                    </div>
                                    <div class="form-group">
                                        <input type="text" name="numero_posti" class="form-control" id="postiAula" placeholder="Posti">

                                    </div>
                                    <div class="form-group">
                                        <input type="text" name="disp_aula" class="form-control" id="dispAula" placeholder="Orario disponibilità">

                                    </div>
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col">
                                                <label class="col-2"><b>Servizi: </b></label>
                                            </div>
                                            <div class="col" id="divPreseIns">
                                                <span style="color: black; font-size: medium">
                                                    <input type="checkbox" name="servizi_extra_prese" class="form-control" id="preseAula" value="PRESE">
                                                    <i id="servizi_prese" class="fa fa-bolt" data-toggle="tooltip" title="L'aula è dotata di prese sul banco"></i>
                                                </span>
                                            </div>
                                            <div class="col" id="divPcIns">
                                                <span style="color: black; font-size: medium">
                                                    <input type="checkbox" name="servizi_extra_computer" class="form-control" id="pcAula" value="COMPUTER">
                                                    <i id="servizi_computer" class="fa fa-laptop" data-toggle="tooltip" title="L'aula è dotata di postazioni pc"></i>
                                                </span>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form form-group "><button onclick="controlloInserimentoAula(formInsA)" type="submit">Registra</button></div>

                                </form>
                            </div>
                        </div>

                    </div>
                </div>
            </div>

        </div>
    </div>
</section>


<!-- Footer -->
<%@ include file="/_fragments/contattaci.jsp" %>

<%@ include file="/_fragments/footer.jsp" %>

<a href="#" class="back-to-top"><i class="fa fa-chevron-up"></i></a>

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
<script src="${pageContext.request.contextPath}/js/inserisciAula.js"></script>
</body>
</html>
