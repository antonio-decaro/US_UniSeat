<%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 04/01/2020
  Time: 20:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="control.utili.SessionManager" %>
<%
    String errorMessage = null;
    if (session != null) {
        errorMessage = SessionManager.getError(session);
        if (errorMessage != null) {
            SessionManager.cleanError(session);
        }
        if (SessionManager.isAlradyAuthenticated(session)) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }
%>
<html>
<head>
    <meta charset="utf-8">
    <title>Login</title>
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
<header id="header">
    <div class="container">

        <div id="logo" class="pull-left">
            <h1><a href="${pageContext.request.contextPath}/index.jsp">Uni Seat</a></h1>
        </div>
        <nav id="nav-menu-container">
        </nav>
    </div>
</header>
<section id="hero">
    <div class="hero-container">
        <div class="container">
            <div class="row">
                <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
                    <div class="card cap  img-fluid card-signin my-5">
                        <div class="card-body">
                            <h5 class="card-title text-center">Accedi</h5>
                            <form class="form-signin" action="${pageContext.request.contextPath}/login" method="post">
                                <% if (errorMessage != null) { %>
                                <div class="alert alert-danger" role="alert">
                                    <%=errorMessage%>
                                </div>
                                <% } %>
                                <div class="form-label-group">
                                    <input name="email" type="email" id="inputEmail" class="form-control" placeholder="Email address"
                                           required autofocus>
                                    <label for="inputEmail">E-Mail</label>
                                </div>
                                <div class="form-label-group">
                                    <input name="password" type="password" id="inputPassword" class="form-control"
                                           placeholder="Password" required>
                                    <label for="inputPassword">Password</label>
                                </div>
                                <div class="custom-control mb-3">
                                    <a href="${pageContext.request.contextPath}/_studente/registrazione.jsp">Non hai un account?</a>
                                </div>
                                <button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">
                                    Accedi
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

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
</body>
</html>
