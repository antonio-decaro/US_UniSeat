
<%@ page import="control.utili.SessionManager" %>
<%@ page import="model.pojo.TipoUtente" %>
<%@ page import="model.pojo.Utente" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<% Utente u = SessionManager.getUtente(session); %>
<header id="header">
    <div class="container">
        <div id="logo" class="pull-left">
            <h1><a href="${pageContext.request.contextPath}/index.jsp">Uni Seat</a></h1>
        </div>

        <% if (u == null) {%>
        <nav id="nav-menu-container">
            <ul class="nav-menu">
                <li><a href="${pageContext.request.contextPath}/index.jsp">Home</a></li>
                <li><a href="${pageContext.request.contextPath}/jsp/login.jsp">Login</a></li>
                <li><a href="${pageContext.request.contextPath}/jsp/registrazione.jsp">Registrazione</a></li>
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
                <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
            </ul>
        </nav>

        <% } else { %>
        <nav id="nav-menu-container">
            <ul class="nav-menu">
                <li><a href="${pageContext.request.contextPath}/index.jsp">Home</a></li>
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
