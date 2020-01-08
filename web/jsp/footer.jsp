<%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 04/01/2020
  Time: 16:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>

<%
    String message = SessionManager.getError(session);
    if (message != null) {
        SessionManager.cleanError(session);
    }
%>

<footer id="footer">
    <div class="container">
        <div class="copyright">
            &copy; Copyright <strong>Uni Seat</strong>. All Rights Reserved
        </div>
    </div>
    <% if (message != null) { %>
        <script>
            alert(<%=message%>);
        </script>
    <% } %>
</footer>
<a href="#" class="back-to-top"><i class="fa fa-chevron-up"></i></a>
