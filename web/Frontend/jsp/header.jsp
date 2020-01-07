
<%@ page import="control.utili.SessionManager" %>
<%@ page import="model.pojo.TipoUtente" %>
<%@ page import="model.pojo.Utente" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<% Utente u = SessionManager.getUtente(session); %>
<header id="header">
    <div class="container">
        <div id="logo" class="pull-left">
            <h1><a href="${pageContext.request.contextPath}/Frontend/jsp/index.jsp">Uni Seat</a></h1>
        </div>

        <% if (u == null) {%>
        <nav id="nav-menu-container">
            <ul class="nav-menu">
                <li><a href="${pageContext.request.contextPath}/Frontend/jsp/index.jsp">Home</a></li>
                <li><a href="${pageContext.request.contextPath}/Frontend/jsp/login.jsp">Login</a></li>
            </ul>
        </nav>

        <% } else if (u.getTipoUtente().toString().equals(TipoUtente.DOCENTE.toString())) { %>

        <nav id="nav-menu-container">
            <ul class="nav-menu">
                <li><a href="#hero">Home</a></li>
                <li><a href="#services">Prenota</a></li>
                <li><a href="prenotazioniDocente.html">Le mie prenotazioni</a></li>
                <li><a href="settings.html">Impostazioni</a></li>
                <li><a href="login.html">Logout</a></li>
            </ul>
        </nav>

        <% } else if (u.getTipoUtente().toString().equals(TipoUtente.ADMIN.toString())) { %>

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

        <% } if (u != null && u.getTipoUtente().toString().equals(TipoUtente.STUDENTE.toString())) { %>
        <nav id="nav-menu-container">
            <ul class="nav-menu">
                <li><a href="${pageContext.request.contextPath}/Frontend/jsp/index.jsp">Home</a></li>
                <li><a href="">Prenota</a></li>
                <li><a href="">La mia prenotazione</a></li>
                <li><a href="">Impostazioni</a></li>
                <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
            </ul>
        </nav>
        <% } %>
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
