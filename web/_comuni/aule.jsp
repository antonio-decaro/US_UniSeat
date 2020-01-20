<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.dao.EdificioDAO" %>
<%@ page import="model.database.DBEdificioDAO" %>
<%@ page import="model.pojo.Aula" %>
<%@ page import="model.pojo.Edificio" %>
<%@ page import="control.utili.DisponibilitaManager" %>

<%
    Utente u = SessionManager.getUtente(session);
    EdificioDAO edificioDAO = DBEdificioDAO.getInstance();
    String strEdificio = request.getParameter("edificio");
    if (strEdificio == null || strEdificio.strip().equals("") || edificioDAO.retriveByName(strEdificio) == null) {
        SessionManager.setError(session, "Edificio non valido");
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
    Edificio edificio = edificioDAO.retriveByName(strEdificio);

    boolean isDocente = u != null && u.getTipoUtente().equals(TipoUtente.DOCENTE);
    boolean isAdmin = u != null && u.getTipoUtente().equals(TipoUtente.ADMIN);

    String errorMessage = SessionManager.getError(session);
    if (errorMessage != null) {
        SessionManager.cleanError(session);
    }
%>


<html>
<head>
    <title>UniSeat - Edificio <%=edificio.getNome()%>
    </title>
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
<section id="hero">
    <div class="hero-container">

        <% if (u == null) { %>

        <h1>Scegli un'aula</h1>
        <a href="${pageContext.request.contextPath}/_comuni/login.jsp" class="btn-get-started">Accedi</a>

        <% } else { %>

        <h1><%=u.getNome()%>, Scegli un'aula</h1>

        <% } %>
    </div>
</section>
<section id="services">
    <div class="container wow fadeIn">
<%--        ERRORE SESSIONE         --%
<%--        <% if (errorMessage != null) { %>--%>
<%--        <div class="alert alert-danger" role="alert">--%>
<%--            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>--%>
<%--            <%=errorMessage%>--%>
<%--        </div>--%>
<%--        <% } %>--%>
        <div class="section-header">
            <h3 class="section-title">Scegli dove studiare</h3>
            <p class="section-description">Prenotare posti non è mai stato così semplice con UniSeat!</p>
        </div>
        <div class="row counters" id="edifici">
            <% for (Aula a : edificio.getAule()) { %>
            <div class="col-lg-4 col-md-6 wow fadeInUp" data-wow-delay="0.2s">
                <div class="box">
                    <div class="icon"><i class="fa fa-pencil"></i></div>
                    <h4 class="title"><%=a.getNome()%>
                    </h4>
                    <div class="counters">
                        <%= new DisponibilitaManager(a, null).isInManutenzione() ? 0 : a.getPosti() - a.getPostiOccupati()%> posti disponibili</div>
                    <div>
                        <br>
                        <%if (new DisponibilitaManager(a, null).isInManutenzione()) { %>
                            <% if (isAdmin) { %>
                            <button type="button" class="btn btn-primary disabled">
                                <a style="color: white" href="${pageContext.request.contextPath}/_admin/ModificaAula.jsp?id=<%=a.getId()%>">Non disponibile</a>
                            </button>
                            <% } else {%>
                            <button type="button" class="btn btn-primary disabled">Non disponibile</button>
                            <% } %>
                        <% } else { %>
                        <button name="toggle_prenotazione" type="button" class="btn btn-primary" data-toggle="modal"
                                data-target="#prenotazione" value="<%=a.getId()%>">
                            <%= u == null || isAdmin ? "Info" : ("Prenota " + (isDocente ? "Aula" : "Posto"))%>
                        </button>
                        <% } %>
                    </div>
                </div>
            </div>
            <% } %>
        </div>
        <!-- info -->
        <%if (u == null || isAdmin) { %>
            <%@include file="infoAula.jsp"%>
        <% } else if (isDocente) { %>
            <%@include file="../_docente/prenotaAula.jsp"%>
        <% } else { %>
            <%@include file="../_studente/prenotaPosto.jsp"%>
        <% } %>
        <!-- info -->
    </div>
</section>
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
<script src="${pageContext.request.contextPath}/js/prenota.js"></script>
</body>
</html>
