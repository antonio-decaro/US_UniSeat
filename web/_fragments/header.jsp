<%@ page import="control.utili.SessionManager" %>
<%@ page import="model.pojo.TipoUtente" %>
<%@ page import="model.pojo.Utente" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%! Utente u;%>
<header id="header">
    <div class="container">
        <div id="logo" class="pull-left">
            <h1><a href="${pageContext.request.contextPath}/index.jsp">Uni Seat</a></h1>
        </div>

        <nav id="nav-menu-container">
            <ul class="nav-menu">
                <li><a id="homeLink" href="${pageContext.request.contextPath}/index.jsp">Home</a></li>
                <% if (u == null) {%>
                    <li><a id="loginLink" href="${pageContext.request.contextPath}/_comuni/login.jsp">Login</a></li>
                    <li><a id="signInLink" href="${pageContext.request.contextPath}/_studente/registrazione.jsp">Registrazione</a></li>
                <% } else { %>
                    <% if (u.getTipoUtente().toString().equals(TipoUtente.ADMIN.toString())) { %>
                        <li class="menu-has-children"><a href="#">Inserisci</a>
                            <ul>
                                <li><a id="inserisciDocenteLink" href="${pageContext.request.contextPath}/_admin/inserisciDocente.jsp">Inserisci docente</a></li>
                                <li><a id="inserisciAulaLink" href="${pageContext.request.contextPath}/_admin/InserimentoAula.jsp">Inserisci aula</a></li>
                            </ul>
                        </li>
                        <li class="menu-has-children"><a href="#">Visualizza</a>
                            <ul>
                                <li><a id="visualizzaUtentiRegistratiLink" href="${pageContext.request.contextPath}/_admin/VisualizzaUtenti.jsp">Visualizza utenti registrati</a></li>
                                <li><a id="visualizzaPrenotazioniLink" href="${pageContext.request.contextPath}/_comuni/prenotazione.jsp">Visualizza prenotazioni</a></li>
                            </ul>
                        </li>
                    <% } else { %>
                        <li><a href="${pageContext.request.contextPath}/_comuni/prenotazione.jsp">La mia prenotazione</a></li>
                        <% if (u.getTipoUtente().equals(TipoUtente.STUDENTE)) {%>
                            <li><a href="${pageContext.request.contextPath}/_studente/VisualizzaProfilo.jsp">Impostazioni</a></li>
                        <% } %>
                    <% } %>
                    <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
                <% } %>
            </ul>
        </nav>
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
