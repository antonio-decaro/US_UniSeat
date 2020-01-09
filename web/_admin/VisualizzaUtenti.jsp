<%@ page import="model.dao.UtenteDAO" %>
<%@ page import="model.database.DBUtenteDAO" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 09/01/2020
  Time: 19:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Utente u = SessionManager.getUtente(session);
    String errorMessage = null;
    errorMessage = SessionManager.getError(session);
    if (errorMessage != null) {
        SessionManager.cleanError(session);
    }
    if (u == null) {
        response.sendRedirect(request.getContextPath() + "/_comuni/login.jsp");
    }
    if (!u.getTipoUtente().equals(TipoUtente.ADMIN)) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
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
<!--==========================
Header
============================-->


<section id="hero">
    <div class="hero-container  ">
        <div class="container  wow fadeInUp">
            <div class="section-header">
                <h3 class="section-title">Utenti registrati</h3>
            </div>
            <table class="table table-dark" >
                <thead>
                <tr>
                    <th scope="col">Nome</th>
                    <th scope="col">Cognome</th>
                    <th scope="col">Tipo</th>
                    <th scope="col">E-mail</th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>

                <%
                    UtenteDAO utenteDAO = DBUtenteDAO.getInstance();
                    List<Utente> lista = utenteDAO.retriveAll();
                    for (Utente ut : lista) {%>

                <tr>
                    <th scope="row"><%=ut.getNome()%></th>
                    <td><%=ut.getCognome()%></td>
                    <td><%=ut.getTipoUtente()%></td>
                    <td><%=ut.getEmail()%></td>
                    <td>
                        <button name="toggle_eliminazioneUtente" id="bottoneElimina" type="button" class="btn btn-primary" data-toggle="modal"
                                data-target="#eliminaut" value="<%=ut.getEmail()%>">Elimina
                        </button>
                    </td>
                </tr>
                <% } %>

                </tbody>
            </table>

        </div>
        <div class="modal fade" id="eliminaut">
            <form id="eliminazione_utente_form" name="eliminaUtente">
                <input id="idEmailUtente" name="email_utente" type="hidden"/>
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <!-- Conferma Header -->
                        <div class="modal-header">
                            <h4 id="confermaEmail">Eliminare l'utente?</h4>
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                        </div>
                        <!-- Conferma footer -->
                        <div class="modal-footer">
                            <button id="submit_button" type="button" class="btn btn-primary" data-dismiss="modal">Conferma</button>
                            <button type="button" class="btn btn-outline-primary" data-dismiss="modal">Chiudi</button>
                        </div>
                    </div>
                </div>

            </form>

        </div>
    </div>
</section>

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
<script src="${pageContext.request.contextPath}/js/eliminazioneUtente.js"></script>

</body>
</html>
