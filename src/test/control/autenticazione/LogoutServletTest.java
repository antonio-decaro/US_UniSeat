package control.autenticazione;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;


class LogoutServletTest  extends Mockito {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher rd;

    /**
     * Questo metodo testa LogoutServlet nel caso in cui si vuole effettuare il
     * logout.
     */

    @Test
    public void testLogoutServlet() throws Exception {

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("/index.jsp")).thenReturn(rd);

        new LogoutServlet().doGet(request, response);

        session.invalidate();

    }

}