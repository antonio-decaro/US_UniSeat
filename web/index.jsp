<%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 03/01/2020
  Time: 12:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="model.database.DBEdificioDAO" %>
<%@ page import="model.pojo.Aula" %>
<%@ page import="model.pojo.Edificio" %>
<%@ page import="model.pojo.Servizio" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page contentType= "text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%
    Utente u = SessionManager.getUtente(session);
    List<Edificio> edifici = DBEdificioDAO.getInstance().retriveAll();
    HashMap<Edificio, Integer> postiDisponibili = new HashMap<>();
    int nAule = 0;
    int nPosti = 0;
    int nComputers = 0;
    for (Edificio e : edifici) {
        int val = 0;
        for (Aula a : e.getAule()) {
            nAule += 1;
            nPosti += a.getPosti();
            if (a.getServizi().contains(Servizio.COMPUTER)) {
                nComputers += a.getPosti();
            }
            val += a.getPosti() - a.getPostiOccupati();
        }
        postiDisponibili.put(e, val);
    }

    String errorMessage = SessionManager.getError(session);
    if (errorMessage != null) {
        SessionManager.cleanError(session);
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>UniSeat - Home</title>
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
<%@ include file="_fragments/header.jsp" %>
<section id="hero">
    <div class="hero-container">

        <% if (u == null) { %>

        <h1>Benvenuto</h1>
        <a href="${pageContext.request.contextPath}/_comuni/login.jsp" class="btn-get-started">Accedi</a>

        <% } else { %>
        <h1>Ciao, <%=u.getNome()%></h1>
        <% } %>
    </div>
</section>
<section id="facts">
    <div class="container wow fadeIn">
<%--        ERRORE SESSIONE         --%>
<%--        <% if (errorMessage != null) { %>--%>
<%--        <div class="alert alert-danger" role="alert">--%>
<%--            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>--%>
<%--            <%=errorMessage%>--%>
<%--        </div>--%>
<%--        <% } %>--%>
        <div class="section-header">
            <h3 class="section-title">Disponibilità</h3>
        </div>
        <br>
        <div class="row counters">
            <div class="col-lg-3 col-6 text-center">
                <span data-toggle="counter-up"><%=edifici.size()%></span>
                <p>Edifici</p>
            </div>
            <div class="col-lg-3 col-6 text-center">
                <span data-toggle="counter-up"><%=nAule%></span>
                <p>Aule</p>
            </div>
            <div class="col-lg-3 col-6 text-center">
                <span data-toggle="counter-up"><%=nPosti%></span>
                <p>Posti</p>
            </div>
            <div class="col-lg-3 col-6 text-center">
                <span data-toggle="counter-up"><%=nComputers%></span>
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
        <div class="row counters" id="edifici">
            <% for (Edificio e : edifici) { %>
            <div class="col-lg-4 col-md-6 wow fadeInUp" data-wow-delay="0.2s">
                <div class="box">
                    <div class="icon"><i class="fa fa-building"></i></div>
                    <h4 class="title"><%=e.getNome()%></h4>
                    <%if (u != null && !u.getTipoUtente().equals(TipoUtente.STUDENTE)) { %>
                    <div class="counters"><%=e.getAule().size()%> aule</div>
                    <% } else { %>
                    <div class="counters"><%=postiDisponibili.get(e)%> posti disponibili</div>
                    <% } %>
                    <div>
                        <br>
                        <button type="button" class="btn btn-primary">
                            <a href="${pageContext.request.contextPath}/_comuni/aule.jsp?edificio=<%=e.getNome()%>">Seleziona</a>
                        </button>
                    </div>
                </div>
            </div>
            <% } %>
        </div>
    </div>
</section>
<%@ include file="/_fragments/contattaci.jsp" %>

<%@ include file="/_fragments/footer.jsp" %>
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