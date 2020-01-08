<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.dao.EdificioDAO" %>
<%@ page import="model.database.DBEdificioDAO" %>
<%@ page import="model.pojo.Aula" %>
<%@ page import="model.pojo.Edificio" %>
<%
    EdificioDAO edificioDAO = DBEdificioDAO.getInstance();
    String strEdificio = request.getParameter("edificio");
    if (strEdificio == null || strEdificio.strip().equals("") || edificioDAO.retriveByName(strEdificio) == null) {
        response.sendRedirect(request.getContextPath() + "/Frontend/jsp/index.jsp");
        return;
    }
    Edificio edificio = edificioDAO.retriveByName(strEdificio);
%>

<%!Utente u;%>

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
<%@ include file="header.jsp" %>
<section id="hero">
    <div class="hero-container">

        <% if (u == null) { %>

        <h1>Benvenuto</h1>
        <a href="${pageContext.request.contextPath}/jsp/login.jsp" class="btn-get-started">Accedi</a>

        <% } else { %>

        <h1>Ciao, <%=u.getNome()%>
        </h1>

        <% } %>
    </div>
</section>
<section id="services">
    <div class="container wow fadeIn">
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
                    <div class="counters"><%=a.getPosti() - a.getPostiOccupati()%> posti disponibili</div>
                    <div>
                        <br>
                        <button id="toogle_prenotazione" type="button" class="btn btn-primary" data-toggle="modal" data-target="#prenotazionePosto" value="<%=a.getId()%>">
                            <%= u == null || u.getTipoUtente().equals(TipoUtente.ADMIN) ? "Info" : (u.getTipoUtente().equals(TipoUtente.STUDENTE) ? "Posto" : "Aula")%>
                        </button>
                    </div>
                </div>
            </div>
            <% } %>
        </div>
        <!-- info -->
        <div class="modal fade" id="prenotazionePosto">
            <form name="prenota_posto">
                <input name="aula" id="id_aula" type="hidden"/>
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <!-- info Header -->
                        <div class="modal-header">
                            <h4 id="nome_aula" class="modal-title">Aula P1</h4>
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                        </div>
                        <!-- info body -->
                        <div class="modal-body">
                            <div class="form">
                                <div id="exercitationrormessage"></div>
                                <form action="" method="post" role="form" class="contactForm">
                                    <div class="form-group">
                                        <label>Posti</label>
                                        <span class="input"></span>
                                    </div>
                                    <div class="form-group">
                                        <label>Computer</label>
                                        <span class="input"></span>
                                    </div>
                                    <div class="form-group">
                                        <label>Altro</label>
                                        <span class="input"></span>
                                    </div>
                                    <div><label for="toggle-one">Disponibile</label><input id="toggle-one" checked type="checkbox"/></div>
                                    <hr>
                                    <div class="form form-group toggle"><a href="#">Modifica</a></div>
                                </form>
                            </div>
                        </div>
                        <!-- info footer -->
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Chiudi</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
        <!-- info -->
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
<script src="${pageContext.request.contextPath}/js/prenota.js"></script>
</body>
</html>
