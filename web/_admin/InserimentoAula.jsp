<%@ page import="control.utili.SessionManager" %>
<%@ page import="model.dao.UtenteDAO" %>
<%@ page import="model.database.DBUtenteDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="model.pojo.*" %>
<%@ page import="model.database.DBEdificioDAO" %><%--
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
        <div class="container  wow fadeInUp" style="visibility:visible;">
            <br>
            <div id="contact" style="border-radius:3%; margin-bottom:25%; margin-left: 20%; margin-right: 20%; margin-top: 20%; padding-top:0%;padding-bottom: 0%; position: relative; top: 3%; bottom: 3%">
                <div class="container wow fadeInUp mt-5">
                    <div class="row justify-content-center">
                        <div class="col-lg-5" style="width: 80%">
                            <div class="form">
                                <div id="exercitationrormessage"></div>
                                <h3 class="section-title">Inserisci Aula</h3>
                                <form method="post" role="form" name="formInsA" class="contactForm">
                                    <div class="form-group">
                                        <label for="selectEdificio">Edificio</label>
                                        <select class="form-control" id="selectEdificio">
                                            <% for (Edificio e : DBEdificioDAO.getInstance().retriveAll()) { %>
                                                <option value="<%=e.getNome()%>"><%=e.getNome()%></option>
                                            <% } %>
                                        </select>
                                        <h6 id="errEdAula" style="color: #bd2130"></h6>
                                    </div>
                                    <div class="form-group">
                                        <label for="nomeAula">Nome</label>
                                        <input type="text" name="nome_aula" class="form-control" onclick="blankLabel('errNomeAula')" id="nomeAula" placeholder="Nome">
                                        <h6 id="errNomeAula" style="color: #bd2130"></h6>
                                    </div>
                                    <div class="form-group">
                                        <label for="postiAula">Posti Aula</label>
                                        <input type="number" name="numero_posti" class="form-control" onclick="blankLabel('errPostiAula')" id="postiAula" placeholder="Posti">
                                        <h6 id="errPostiAula" style="color: #bd2130"></h6>
                                    </div>
                                    <div class="form-group">
                                        <label for="disponibilita">Disponibilit&agrave;</label>
                                        <textarea class="form-control" name="disp_aula" id="disponibilita" onclick="blankLabel('errDispAula')" rows="4"></textarea>
                                        <h6 id="errDispAula" style="color: #bd2130"></h6>
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
                                    <button type="button" class="btn btn-lg btn-primary btn-block text-uppercase" onclick="controllaInsAula(formInsA)">Registra
                                    </button>

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
