<%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 04/01/2020
  Time: 21:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <title>UniSeat</title>
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
    <link href=${pageContext.request.contextPath}\Frontend\lib\font-awesome\css\font-awesome.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/Frontend/lib/animate/animate.min.css" rel="stylesheet">

    <!-- Main Stylesheet File -->
    <link href="${pageContext.request.contextPath}/Frontend/css/style.css" rel="stylesheet">
</head>
<body>
<header id="header">
    <div class="container">

        <div id="logo" class="pull-left">
            <h1><a href="${pageContext.request.contextPath}/Frontend/jsp/registrazione.jsp">Uni Seat</a></h1>
        </div>
        <nav id="nav-menu-container">
        </nav>
    </div>
</header>
<section id="hero">
    <div class="hero-container">
        <div class="container wow fadeInUp">
            <div class="row">
                <div class="col-sm-12 col-md-7 col-lg-5 mx-auto">
                    <div class="card card-signin my-5">
                        <div class="card-body">
                            <h5 class="card-title text-center">Registrati</h5>
                            <form class="form-signin">
                                <div class="form-label-group">
                                    <input type="text" id="inputName" class="form-control" placeholder="Name" required
                                           autofocus>
                                    <label for="inputName">Nome</label>
                                </div>
                                <div class="form-label-group">
                                    <input type="text" id="inputSurname" class="form-control" placeholder="Surname"
                                           required autofocus>
                                    <label for="inputSurname">Cognome</label>
                                </div>
                                <div class="form-label-group">
                                    <input type="text" id="inputMatricola" class="form-control"
                                           placeholder="Matricola" required autofocus>
                                    <label for="inputMatricola">Matricola</label>
                                </div>

                                <div class="form-label-group">
                                    <input type="password" id="inputPassword" class="form-control"
                                           placeholder="Password" required>
                                    <label for="inputPassword">Password</label>
                                </div>

                                <div class="form-label-group">
                                    <input type="password" id="verificaPassword" class="form-control"
                                           placeholder="verifica Password" required>
                                    <label for="verificaPassword">Verifica password</label>
                                </div>

                                <div class="custom-control mb-3">
                                    <a href="${pageContext.request.contextPath}/Frontend/jsp/login.jsp">Sei già
                                        registrato?</a>
                                </div>
                                <button class="btn btn-lg btn-primary btn-block text-uppercase center-block"
                                        type="submit">Registrati
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
<script src="${pageContext.request.contextPath}/Frontend/lib/jquery/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/Frontend/lib/jquery/jquery-migrate.min.js"></script>
<script src="${pageContext.request.contextPath}/Frontend/lib/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/Frontend/lib/easing/easing.min.js"></script>
<script src="${pageContext.request.contextPath}/Frontend/lib/wow/wow.min.js"></script>
<script src="${pageContext.request.contextPath}/Frontend/lib/waypoints/waypoints.min.js"></script>
<script src="${pageContext.request.contextPath}/Frontend/lib/counterup/counterup.min.js"></script>
<script src="${pageContext.request.contextPath}/Frontend/lib/superfish/hoverIntent.js"></script>
<script src="${pageContext.request.contextPath}/Frontend/lib/superfish/superfish.min.js"></script>
<!-- Contact Form JavaScript File -->
<script src="${pageContext.request.contextPath}/Frontend/contactform/contactform.js"></script>
<!-- Template Main Javascript File -->
<script src="${pageContext.request.contextPath}/Frontend/js/main.js"></script>

</body>
</html>