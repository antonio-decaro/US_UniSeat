<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 07/01/2020
  Time: 15:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType=
                 "text/html;charset=UTF-8" pageEncoding="UTF-8" %><!DOCTYPE html>

<!DOCTYPE html>
<%
    String errorMessage = null;
    if (session != null) {
        errorMessage = SessionManager.getError(session);
        if (errorMessage != null) {
            SessionManager.cleanError(session);
        }
        if (!SessionManager.isAlradyAuthenticated(session)) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
        }
    }
%>
<html lang="en">
<head>
    <title>Profilo</title>
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
<!--==========================
Header
============================-->

<section id="hero">
    <div class="hero-container">
        <div class="container  wow fadeInUp">
            <br>
            <div id="contact">
                <h3 class="section-title">Impostazioni</h3>
                <div class="container wow fadeInUp mt-5">
                    <div class="row justify-content-center">
                        <div class="col-lg-4 col-md-4">
                            <div class="form">
                                <div id="exercitationrormessage"></div>
                                <form name="formModifica" method="post" class="form-signin">
                                    <% if (errorMessage != null) { %>
                                    <div id="alertErrorNome" class="alert alert-danger" role="alert">
                                        <%=errorMessage%>
                                    </div>
                                    <% } %>
                                    <div class="form-label-group">
                                        <input name="nome" type="text" id="inputNome" class="form-control" onClick="blankLabel('errN')" value=<%=u.getNome()%> readonly>
                                        <label for="inputNome">Nome</label>
                                    </div>
                                    <h6 id="errN" style="color: #bd2130"></h6>

                                    <% if (errorMessage != null) { %>
                                    <div id="alertErrorCognome" class="alert alert-danger" role="alert">
                                        <%=errorMessage%>
                                    </div>
                                    <% } %>
                                    <div class="form-label-group">
                                        <input name="cognome" type="text" id="inputCognome" class="form-control" onClick="blankLabel('errC')" value="<%=u.getCognome()%>"
                                               readonly>
                                        <label for="inputCognome">Cognome</label>

                                    </div>
                                    <h6 id="errC" style="color: #bd2130"></h6>
                                    <div class="form-label-group">
                                        <input name="email" type="email" id="inputEmail" class="form-control" value="<%=u.getEmail()%>"
                                               readonly>
                                        <label for="inputEmail">E-Mail</label>
                                    </div>
                                    <% if (errorMessage != null) { %>
                                    <div id="alertErrorPass" class="alert alert-danger" role="alert">
                                        <%=errorMessage%>
                                    </div>
                                    <% } %>
                                    <div class="form-label-group" id="divPassHide" style="display:none">
                                        <input name="password" type="password" id="inputPassword" class="form-control" onClick="blankLabel('errP')"
                                               value="<%=u.getPassword()%>" readonly>
                                        <label for="inputPassword">Password</label>
                                    </div>
                                    <div class="form-label-group" id="divConfPassHide" style="display:none">
                                        <input name="confPassword" type="password" id="inputConfPassword" class="form-control" onClick="blankLabel('errP')"
                                               value="<%=u.getPassword()%>" readonly>
                                        <label for="inputConfPassword">Conferma Password</label>

                                    </div>
                                    <h6 id="errP" style="color: #bd2130"></h6>
                                    <button id="buttonModifica" style="position: center" type="button" class="btn btn-lg btn-primary btn-block text-uppercase" onclick="setEditabili()">Modifica</button>
                                     <button id="buttonConfermaModifica" style="position: center;display:none" type="button" class="btn btn-lg btn-primary btn-block text-uppercase" onclick="controllaFormModifica(formModifica)">Conferma</button>
                                </form>
                            </div>
                        </div>

                    </div>
                </div>
            </div>

        </div>
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
<script src="${pageContext.request.contextPath}/js/edifici.js"></script>
<script src="${pageContext.request.contextPath}/js/ModificaProfilo.js"></script>


</body>
</html>


