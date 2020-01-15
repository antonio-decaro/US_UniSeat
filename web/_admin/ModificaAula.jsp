<%--
  Created by IntelliJ IDEA.
  User: Gianluca Spinelli
  Date: 09/01/2020
  Time: 10:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.database.DBAulaDAO" %>
<%@ page import="model.database.DBEdificioDAO" %>
<%@ page import="model.pojo.*" %>
<%
    Utente u = SessionManager.getUtente(session);
    if (u == null) {
        response.sendRedirect(request.getContextPath() + "/_comuni/login.jsp");
        return;
    }
    if (!u.getTipoUtente().equals(TipoUtente.ADMIN)) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
    String id = request.getParameter("id");
    if (id == null || !id.strip().matches("[0-9]+")) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }

    Aula a = DBAulaDAO.getInstance().retriveById(Integer.parseInt(id));
    if (a == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
    String template = "{\"intervalli\": [\n\t[],\n\t[],\n\t[],\n\t[],\n\t[],\n\t[],\n\t[]\n]}";
    String regex = "\\{\\s*?[\"']intervalli[\"']\\s*?:\\s*?\\[\\s*?(\\[(\\[\\s*?(\"[0-2][0-9]:[0-6][0-9]\")\\s*?," +
            "\\s*?(\"[0-2][0-9]:[0-6][0-6]\")\\s*?],?\\s*?)*],?\\s*){7}\\s*]\\s*?}";
    if (!a.getDisponibilita().matches(regex)) {
        a.setDisponibilita(template);
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
        <div class="container  wow fadeInUp">
            <div class="container wow fadeInUp mt-5">
                <div class="row justify-content-center">
                    <div class="col-12 col-md-8 col-lg-6">
                        <div class="card card-signin my-5">
                            <div class="card-body">
                                <div class="form">
                                    <div id="exercitationrormessage"></div>
                                    <form method="post" role="form" name="formModA" class="contactForm" id="formModAula">
                                        <div class="form-group">
                                            <label for="selectEdificio">Edificio</label>
                                            <select name="edificio" class="form-control" id="selectEdificio">
                                                <% for (Edificio e : DBEdificioDAO.getInstance().retriveAll()) { %>
                                                <option value="<%=e.getNome()%>" <%=e.getNome().equals(a.getEdificio().getNome()) ? "selected" : ""%>>
                                                    <%=e.getNome()%>
                                                </option>
                                                <% } %>
                                            </select>
                                            <h6 id="errEdAula" style="color: #bd2130"></h6>
                                        </div>
                                        <div class="form-group">
                                            <label for="nomeAula">Nome</label>
                                            <input type="text" name="nome_aula" class="form-control" id="nomeAula"
                                                   placeholder="Nome" value="<%=a.getNome()%>"/>
                                            <h6 id="errNomeAula" style="color: #bd2130"></h6>
                                        </div>
                                        <div class="form-group">
                                            <label for="postiAula">Posti Aula</label>
                                            <input type="number" name="numero_posti" class="form-control"
                                                   onclick="blankLabel('errPostiAula')" id="postiAula"
                                                   placeholder="Posti" value="<%=a.getPosti()%>">
                                            <h6 id="errPostiAula" style="color: #bd2130"></h6>
                                        </div>
                                        <div class="form-group">
                                            <label for="disponibilita">Disponibilit&agrave;</label>
                                            <textarea class="form-control" name="disp_aula" id="disponibilita"
                                                      onclick="blankLabel('errDispAula')" rows="7"
                                                      style="resize: none"><%=a.getDisponibilita()%></textarea>
                                            <h6 id="errDispAula" style="color: #bd2130"></h6>
                                        </div>
                                        <div class="form-group container-fluid">
                                            <div class="row">
                                                <div class="col-4">
                                                    <label><b>Servizi: </b></label>
                                                </div>
                                                <div class="col-4" id="divPreseIns">
                                                    <label for="preseAula"></label>
                                                    <input style="display: none" type="checkbox"
                                                           name="servizi_extra_prese" class="form-control"
                                                           id="preseAula" value="PRESE" <%=a.getServizi().contains(Servizio.PRESE) ? "checked" : ""%>/>
                                                    <button class="btn" type="button" id="setPrese"
                                                            title="L'aula è dotata di prese sul banco">
                                                        <span style="color: black; font-size: large">
                                                            <i id="servizi_prese" class="fa fa-bolt"
                                                               data-toggle="tooltip"></i>
                                                        </span>
                                                    </button>
                                                </div>
                                                <div class="col-4" id="divPcIns">
                                                    <label for="pcAula"></label>
                                                    <input style="display: none" type="checkbox"
                                                           name="servizi_extra_computer" class="form-control"
                                                           id="pcAula" value="COMPUTER" <%=a.getServizi().contains(Servizio.COMPUTER) ? "checked" : ""%>/>
                                                    <button class="btn" type="button" id="setComputer"
                                                            title="L'aula è dotata di postazioni pc">
                                                        <span style="color: black; font-size: large">
                                                            <i id="servizi_computer" class="fa fa-laptop"
                                                               data-toggle="tooltip"></i>
                                                        </span>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                        <button type="button" class="btn btn-lg btn-primary btn-block text-uppercase"
                                                onclick="controllaModAula(formModA)" id="submitButton">Modifica
                                        </button>

                                    </form>
                                </div>
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
