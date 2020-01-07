<%@ page import="model.pojo.Utente" %>
<%@ page import="control.utili.SessionManager" %>
<%@ page import="model.pojo.TipoUtente" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<% Utente u = SessionManager.getUtente(request.getSession());%>
<html>
<head>
    <meta charset="utf-8">
    <title>Header</title>
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
<header id="header">
    <div class="container">
        <div id="logo" class="pull-left">
            <h1><a href="${pageContext.request.contextPath}/Frontend/jsp/index.jsp">Uni Seat</a></h1>
        </div>

        <%
            if (u == null) {
        %>
        <nav id="nav-menu-container">
            <ul class="nav-menu">
                <li><a href="${pageContext.request.contextPath}/Frontend/jsp/index.jsp">Home</a></li>
                <li><a href="${pageContext.request.contextPath}/Frontend/jsp/login.jsp">Login</a></li>
            </ul>
        </nav>

        <%
        } else if (u.getTipoUtente().toString().equals(TipoUtente.DOCENTE.toString())) {
        %>

        <nav id="nav-menu-container">
            <ul class="nav-menu">
                <li><a href="#hero">Home</a></li>
                <li><a href="#services">Prenota</a></li>
                <li><a href="prenotazioniDocente.html">Le mie prenotazioni</a></li>
                <li><a href="settings.html">Impostazioni</a></li>
                <li><a href="login.html">Logout</a></li>
            </ul>
        </nav>

        <%
        } else if (u.getTipoUtente().toString().equals(TipoUtente.ADMIN.toString())) {
        %>

        <nav id="nav-menu-container">
            <ul class="nav-menu">
                <li><a href="#hero">Home</a></li>
                <li class="menu-has-children"><a href="#">Inserisci</a>
                    <ul>
                        <li><a href="inserisciDocente.html">Inserisci docente</a></li>
                        <li><a href="#">Inserisci aula</a></li>
                    </ul>
                <li class="menu-has-children"><a href="#">Visualizza</a>
                    <ul>
                        <li><a href="utentiRegistratiAdmin.html">Visualizza utenti registrati</a></li>
                        <li><a href="visualizzaPrenotazioniAdmin.html">Visualizza prenotazioni</a></li>
                        <li><a href="#services">Visualizza edifici</a></li>
                    </ul>
                <li><a href="settings.html">Impostazioni</a></li>
                <li><a href="login.html">Logout</a></li>
            </ul>
        </nav>

        <%
            }
            assert u != null;
            if (u.getTipoUtente().toString().equals(TipoUtente.STUDENTE.toString())) {
        %>

        <nav id="nav-menu-container">
            <ul class="nav-menu">
                <li><a href="index.jsp">Home</a></li>
                <li><a href="">Prenota</a></li>
                <li><a href="prenotazione.jsp">La mia prenotazione</a></li>
                <li><a href="settings.jsp">Impostazioni</a></li>
                <li><a href="login.jsp">Logout</a></li>
            </ul>
        </nav>

        <%
            }
        %>

    </div>
</header>
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
</body>
</html>
