<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.pojo.Edificio" %>
<%@ page import="model.pojo.Aula" %>
<%@ page import="model.dao.EdificioDAO" %>
<%@ page import="model.database.DBEdificioDAO" %>
<%
    EdificioDAO edificioDAO = DBEdificioDAO.getInstance();
    String strEdificio = request.getParameter("edificio");
    if (strEdificio == null || strEdificio.strip().equals("") || edificioDAO.retriveByName(strEdificio) == null) {
        response.sendRedirect(request.getContextPath() + "/Frontend/jsp/index.jsp");
        return;
    }
    Edificio edificio = edificioDAO.retriveByName(strEdificio);
%>

<html>
<head>
    <title>UniSeat - Edificio <%=edificio.getNome()%></title>
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
<%@ include file="header.jsp" %>
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
                    <h4 class="title">Edificio <%=a.getNome()%>
                    </h4>
                    <div class="counters"><%=a.getPosti() - a.getPostiOccupati()%> posti disponibili</div>
                    <div>
                        <br>
                        <button type="button" class="btn btn-primary">
                            <a href="${pageContext.request.contextPath}/Frontend/jsp/prenotazione.jsp?aula=<%=a.getId()%>">Seleziona</a>
                        </button>
                    </div>
                </div>
            </div>
            <% } %>
        </div>
    </div>
</section>
<%@ include file="contattaci.jsp" %>

<%@ include file="footer.jsp" %>
</body>
</html>
