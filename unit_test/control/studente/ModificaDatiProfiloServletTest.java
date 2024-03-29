package control.studente;

import control.utili.PasswordEncrypter;
import control.utili.SessionManager;
import model.daostub.StubUtenteDAO;
import model.pojo.TipoUtente;
import model.pojo.Utente;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class ModificaDatiProfiloServletTest {

    @Mock private HttpServletRequest req;
    @Mock private HttpServletResponse res;
    @Mock private ServletContext ctx;
    @Mock private HttpSession session;
    private StubUtenteDAO utenteDAO = new StubUtenteDAO();
    private ModificaDatiProfiloServlet servlet;
    private Map<String,Object> attributes = new HashMap<>();
    private StringWriter stringWriter;

    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        servlet = new ModificaDatiProfiloServlet();
        stringWriter = new StringWriter();

        when(req.getServletContext()).thenReturn(ctx);
        when(ctx.getAttribute(ModificaDatiProfiloServlet.UTENTE_DAO_PARAM)).thenReturn(utenteDAO);
        when(req.getSession()).thenReturn(session);
        when(ctx.getContextPath()).thenReturn("");
        when(SessionManager.isAlradyAuthenticated(session)).thenReturn(true);
        when(res.getWriter()).thenReturn(new PrintWriter(stringWriter));
        doNothing().when(res).sendRedirect(anyString());

        Mockito.doAnswer((Answer<Object>) invocation -> {
            String key = (String) invocation.getArguments()[0];
            return attributes.get(key);
        }).when(session).getAttribute(anyString());

        Mockito.doAnswer((Answer<Object>) invocation -> {
            String key = (String) invocation.getArguments()[0];
            Object value = invocation.getArguments()[1];
            attributes.put(key, value);
            return null;
        }).when(session).setAttribute(anyString(), any());

        Utente u = new Utente("m.rossi12@studenti.unisa.it", "Mario", "Rossi",
                PasswordEncrypter.criptaPassword("MarioRossi12"), TipoUtente.STUDENTE);
        utenteDAO.insert(u);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void TC_1_1() throws Exception {
        when(req.getParameter("nome")).thenReturn("Mario");
        when(req.getParameter("cognome")).thenReturn("Rossi");
        when(req.getParameter("password")).thenReturn("MarioRossi12");
        when(req.getParameter("confPassword")).thenReturn("MarioRossi12");
        servlet.doPost(req, res);
        assertEquals("LogIn non effettuato",
                SessionManager.getError(session));
    }
    @Test
    void TC_1_2() throws Exception {
        SessionManager.autentica(session, utenteDAO.retriveAll().get(0));

        when(req.getParameter("nome")).thenReturn("");
        when(req.getParameter("cognome")).thenReturn("Rossi");
        when(req.getParameter("password")).thenReturn("MarioRossi12");
        when(req.getParameter("confPassword")).thenReturn("MarioRossi12");
        servlet.doPost(req, res);
        assertEquals("Il campo Nome non rispetta la lunghezza",
                SessionManager.getError(session));
    }
    @Test
    void TC_1_3() throws Exception {
        SessionManager.autentica(session, utenteDAO.retriveAll().get(0));

        when(req.getParameter("nome")).thenReturn("abcdefghilmnopqrstuvz");
        when(req.getParameter("cognome")).thenReturn("Rossi");
        when(req.getParameter("password")).thenReturn("MarioRossi12");
        when(req.getParameter("confPassword")).thenReturn("MarioRossi12");
        servlet.doPost(req, res);
        assertEquals("Il campo Nome non rispetta la lunghezza",
                SessionManager.getError(session));
    }
    @Test
    void TC_1_4() throws Exception {
        SessionManager.autentica(session, utenteDAO.retriveAll().get(0));

        when(req.getParameter("nome")).thenReturn("Mar10");
        when(req.getParameter("cognome")).thenReturn("Rossi");
        when(req.getParameter("password")).thenReturn("MarioRossi12");
        when(req.getParameter("confPassword")).thenReturn("MarioRossi12");
        servlet.doPost(req, res);
        assertEquals("Il campo Nome non rispetta il formato",
                SessionManager.getError(session));
    }
    @Test
    void TC_1_5() throws Exception {
        SessionManager.autentica(session, utenteDAO.retriveAll().get(0));

        when(req.getParameter("nome")).thenReturn("Mario");
        when(req.getParameter("cognome")).thenReturn("");
        when(req.getParameter("password")).thenReturn("MarioRossi12");
        when(req.getParameter("confPassword")).thenReturn("MarioRossi12");
        servlet.doPost(req, res);
        assertEquals("Il campo Cognome non rispetta la lunghezza",
                SessionManager.getError(session));
    }
    @Test
    void TC_1_6() throws Exception {
        SessionManager.autentica(session, utenteDAO.retriveAll().get(0));

        when(req.getParameter("nome")).thenReturn("Mario");
        when(req.getParameter("cognome")).thenReturn("abcdefhghilmnopqrstuvz");
        when(req.getParameter("password")).thenReturn("MarioRossi12");
        when(req.getParameter("confPassword")).thenReturn("MarioRossi12");
        servlet.doPost(req, res);
        assertEquals("Il campo Cognome non rispetta la lunghezza",
                SessionManager.getError(session));
    }
    @Test
    void TC_1_7() throws Exception {
        SessionManager.autentica(session, utenteDAO.retriveAll().get(0));

        when(req.getParameter("nome")).thenReturn("Mario");
        when(req.getParameter("cognome")).thenReturn("R00ss1");
        when(req.getParameter("password")).thenReturn("MarioRossi12");
        when(req.getParameter("confPassword")).thenReturn("MarioRossi12");
        servlet.doPost(req, res);
        assertEquals("Il campo Cognome non rispetta il formato",
                SessionManager.getError(session));
    }
    @Test
    void TC_1_8() throws Exception {
        SessionManager.autentica(session, utenteDAO.retriveAll().get(0));

        when(req.getParameter("nome")).thenReturn("Mario");
        when(req.getParameter("cognome")).thenReturn("Rossi");
        when(req.getParameter("password")).thenReturn("Abc1");
        when(req.getParameter("confPassword")).thenReturn("MarioRossi12");
        servlet.doPost(req, res);
        assertEquals("Il campo Password non rispetta la lunghezza",
                SessionManager.getError(session));
    }
    @Test
    void TC_1_9() throws Exception {
        SessionManager.autentica(session, utenteDAO.retriveAll().get(0));

        when(req.getParameter("nome")).thenReturn("Mario");
        when(req.getParameter("cognome")).thenReturn("Rossi");
        when(req.getParameter("password")).thenReturn("mariorossi");
        when(req.getParameter("confPassword")).thenReturn("MarioRossi12");
        servlet.doPost(req, res);
        assertEquals("Il campo Password non rispetta il formato",
                SessionManager.getError(session));
    }
    @Test
    void TC_1_10() throws Exception {
        SessionManager.autentica(session, utenteDAO.retriveAll().get(0));

        when(req.getParameter("nome")).thenReturn("Mario");
        when(req.getParameter("cognome")).thenReturn("Rossi");
        when(req.getParameter("password")).thenReturn("MarioRossi12");
        when(req.getParameter("confPassword")).thenReturn("LuigiVerdi14");
        servlet.doPost(req, res);
        assertEquals("Le Password non corrispondono",
                SessionManager.getError(session));
    }

    @Test
    void TC_1_11() throws Exception {
        SessionManager.autentica(session, utenteDAO.retriveAll().get(0));

        when(req.getParameter("nome")).thenReturn("Maria");
        when(req.getParameter("cognome")).thenReturn("Rossi");
        when(req.getParameter("password")).thenReturn("MariaRossi12");
        when(req.getParameter("confPassword")).thenReturn("MariaRossi12");
        servlet.doPost(req, res);
        assertNull(SessionManager.getError(session));
    }

//    @Test
//    void TC_1_12() throws Exception {
//        SessionManager.autentica(session, utenteDAO.retriveAll().get(0));
//
//        when(req.getParameter("nome")).thenReturn("Maria");
//        when(req.getParameter("cognome")).thenReturn("Rossi");
//        when(req.getParameter("password")).thenReturn("MariaRossi12");
//        when(req.getParameter("confPassword")).thenReturn("MariaRossi12");
//        servlet.doPost(req, res);
//        assertNull(SessionManager.getError(session));
//    }

    @Test
    void testGet() throws Exception {
        SessionManager.autentica(session, new Utente());
        servlet.doGet(req, res);
    }
}