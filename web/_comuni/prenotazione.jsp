<%@ page import="model.database.DBPrenotazioneDAO" %>
<%@ page import="model.pojo.Prenotazione" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.sql.Time" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 07/01/2020
  Time: 09:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    Utente u = SessionManager.getUtente(session);
    if (u == null) {
        response.sendRedirect(request.getContextPath() + "/_comuni/login.jsp");
        return;
    }

    List<Prenotazione> prenotazioniAttive = DBPrenotazioneDAO.getInstance().retriveByUtente(u);
    prenotazioniAttive.removeIf(p -> p.getData().before(Date.valueOf(LocalDate.now())));
    prenotazioniAttive.removeIf(p -> p.getData().equals(Date.valueOf(LocalDate.now())) &&
            p.getOraFine().before(Time.valueOf(LocalTime.now())));

    String errorMessage = SessionManager.getError(session);
    if (errorMessage != null) {
        SessionManager.cleanError(session);
    }
%>
<html>
<head>
    <title>UniSeat - Prenotazioni</title>
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
<%@ include file="../_fragments/header.jsp" %>
<main>
    <section id="hero">
        <div class="hero-container  ">
            <div class="container wow fadeInUp">
                <div class="card cap  img-fluid card-signin my-5">
                    <div class="card-body">
                        <div class="section-header">
                            <% if (!prenotazioniAttive.isEmpty()) { %>
                            <h3 class="section-title">La mia prenotazione</h3>
                            <% } else { %>
                            <h3 class="section-title">Non ci sono prenotazioni attive</h3>
                            <% } %>
                            <% if (errorMessage != null) { %>--%>
                            <div class="alert alert-danger" role="alert">
                                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                <%=errorMessage%>
                            </div>
                            <% } %>
                        </div>
                        <% if (!prenotazioniAttive.isEmpty()) { %>
                        <table class="table table-light table-responsive-md">
                            <thead>
                            <tr>
                                <th scope="col">Edificio</th>
                                <th scope="col">Aula</th>
                                <th scope="col">Data</th>
                                <th scope="col">Ora inizio</th>
                                <th scope="col">Ora fine</th>
                                <th scope="col"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <% for (Prenotazione p : prenotazioniAttive) { %>
                            <tr>
                                <th scope="row"><%=p.getAula().getEdificio().getNome()%>
                                </th>
                                <td><%=p.getAula().getNome()%>
                                </td>
                                <td><%=p.getData()%>
                                </td>
                                <td><%=p.getOraInizio()%>
                                </td>
                                <td><%=p.getOraFine()%>
                                </td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/eliminaPrenotazione" method="get">
                                        <input name="id_prenotazione" type="hidden" value="<%=p.getId()%>"/>
                                        <button type="submit" class="btn btn-secondary btn-sm">
                                            Elimina prenotazione
                                        </button>
                                    </form>
                                </td>

                            </tr>
                            <% } %>
                            </tbody>
                        </table>
                        <% } %>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>
<%@ include file="../_fragments/contattaci.jsp" %>
<%@ include file="../_fragments/footer.jsp" %>
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
