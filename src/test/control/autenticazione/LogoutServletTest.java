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
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    HttpSession session;

    @Mock
    RequestDispatcher rd;

    /**
     * Questo metodo testa ServletLogout nel caso in cui si vuole effettuare il
     * logout.
     */

    @Test
    public void testServletLogout() throws Exception {

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        // RequestDispatcher rd=mock(RequestDispatcher.class);

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("index.jsp")).thenReturn(rd);

        new LogoutServlet().doPost(request, response);

        session.invalidate();

    }

}