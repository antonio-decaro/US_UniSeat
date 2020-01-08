package control.autenticazione;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


class LogoutServletTest  extends Mockito {

    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private HttpSession session;
    @Mock private ServletContext context;
    @Mock private RequestDispatcher rd;
    private LogoutServlet servlet;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        servlet = new LogoutServlet();
        when(request.getSession()).thenReturn(session);
        when(request.getServletContext()).thenReturn(context);
        when(context.getContextPath()).thenReturn("");
        when(request.getRequestDispatcher("/comuni/index.jsp")).thenReturn(rd);
        doNothing().when(response).sendRedirect(anyString());
        doNothing().when(session).invalidate();
    }

    @Test
    void testLogoutServlet() throws Exception {
        servlet.doGet(request, response);

    }

}