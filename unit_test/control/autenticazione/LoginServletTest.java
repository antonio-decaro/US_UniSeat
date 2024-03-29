package control.autenticazione;

import control.utili.PasswordEncrypter;
import control.utili.SessionManager;
import model.dao.UtenteDAO;
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
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class LoginServletTest {

    @Mock private HttpServletRequest req;
    @Mock private HttpServletResponse res;
    @Mock private ServletContext ctx;
    @Mock private HttpSession session;
    private UtenteDAO utenteDAO = new StubUtenteDAO();
    private LoginServlet servlet;
    private Map<String,Object> attributes = new HashMap<>();

    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        servlet = new LoginServlet();
        when(req.getServletContext()).thenReturn(ctx);
        when(ctx.getAttribute(LoginServlet.UTENTE_DAO_PARAM)).thenReturn(utenteDAO);
        when(req.getSession()).thenReturn(session);
        when(ctx.getContextPath()).thenReturn("");
        when(session.isNew()).thenReturn(true);
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
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void TC_2_1() throws Exception {
        when(req.getParameter("email")).thenReturn(null);
        when(req.getParameter("password")).thenReturn("MarioRossi12");
        servlet.doPost(req, res);
        assertEquals("Il campo email non rispetta la lunghezza",
                SessionManager.getError(session));
        assertNull(SessionManager.getUtente(session));
    }

    @Test
    void TC_2_2() throws Exception {
        when(req.getParameter("email")).thenReturn("abcdefghilmnopqrstuvz1234567890@studenti.unisa.it");
        when(req.getParameter("password")).thenReturn("MarioRossi12");
        servlet.doPost(req, res);
        assertEquals("Il campo email non rispetta la lunghezza",
                SessionManager.getError(session));
        assertNull(SessionManager.getUtente(session));
    }

    @Test
    void TC_2_3() throws Exception {
        when(req.getParameter("email")).thenReturn("m.rossi12@comain.com");
        when(req.getParameter("password")).thenReturn("MarioRossi12");
        servlet.doPost(req, res);
        assertEquals("Il campo E-mail non rispetta il formato",
                SessionManager.getError(session));
        assertNull(SessionManager.getUtente(session));
    }

    @Test
    void TC_2_4() throws Exception {
        when(req.getParameter("email")).thenReturn("m.rossi13@studenti.unisa.it");
        when(req.getParameter("password")).thenReturn("MarioRossi12");
        servlet.doPost(req, res);
        assertEquals("Credenziali non corrette",
                SessionManager.getError(session));
        assertNull(SessionManager.getUtente(session));
    }

    @Test
    void TC_2_5() throws Exception {
        when(req.getParameter("email")).thenReturn("m.rossi12@studenti.unisa.it");
        when(req.getParameter("password")).thenReturn("MarioRo");
        servlet.doPost(req, res);
        assertEquals("Il campo Password non rispetta la lunghezza",
                SessionManager.getError(session));
        assertNull(SessionManager.getUtente(session));
    }

    @Test
    void TC_2_6() throws Exception {
        when(req.getParameter("email")).thenReturn("m.rossi12@studenti.unisa.it");
        when(req.getParameter("password")).thenReturn("Abcdefghilmnopqrstuvwxyz1234567890");
        servlet.doPost(req, res);
        assertEquals("Il campo Password non rispetta la lunghezza",
                SessionManager.getError(session));
        assertNull(SessionManager.getUtente(session));
    }

    @Test
    void TC_2_7() throws Exception {
        when(req.getParameter("email")).thenReturn("m.rossi12@studenti.unisa.it");
        when(req.getParameter("password")).thenReturn("MarioRossi");
        servlet.doPost(req, res);
        assertEquals("Il campo Password non rispetta il formato",
                SessionManager.getError(session));
        assertNull(SessionManager.getUtente(session));
    }

    @Test
    void TC_2_8() throws Exception {
        when(req.getParameter("email")).thenReturn("m.rossi12@studenti.unisa.it");
        when(req.getParameter("password")).thenReturn("MarioRossi42");
        servlet.doPost(req, res);
        assertEquals("Credenziali non corrette",
                SessionManager.getError(session));
        assertNull(SessionManager.getUtente(session));
    }

    @Test
    void TC_2_9() throws Exception {
        Utente utente = new Utente("m.rossi12@studenti.unisa.it", "Mario", "Rossi",
                PasswordEncrypter.criptaPassword("MarioRossi12"), TipoUtente.STUDENTE);
        utenteDAO.insert(utente);
        when(req.getParameter("email")).thenReturn("m.rossi12@studenti.unisa.it");
        when(req.getParameter("password")).thenReturn("MarioRossi12");
        servlet.doPost(req, res);
        assertNotNull(SessionManager.getUtente(session));
        assertEquals(SessionManager.getUtente(session),
                utenteDAO.retriveByEmail("m.rossi12@studenti.unisa.it"));
    }

    @Test
    void testNewSession() throws Exception {
        when(session.isNew()).thenReturn(false);
        servlet.doPost(req, res);
    }

    @Test
    void testBadCredentials() throws Exception {
        Utente utente = new Utente("m.rossi12@studenti.unisa.it", "Mario", "Rossi",
                PasswordEncrypter.criptaPassword("MarioRossi13"), TipoUtente.STUDENTE);
        utenteDAO.insert(utente);
        when(req.getParameter("email")).thenReturn("m.rossi12@studenti.unisa.it");
        when(req.getParameter("password")).thenReturn("MarioRossi12");
        servlet.doGet(req, res);
        assertEquals("Credenziali non corrette",
                SessionManager.getError(session));
        assertNull(SessionManager.getUtente(session));
    }

    @Test
    void testNonConfirmedUser() throws Exception {
        Utente utente = new Utente("m.rossi12@studenti.unisa.it", "Mario", "Rossi",
                PasswordEncrypter.criptaPassword("MarioRossi12"), TipoUtente.STUDENTE);
        utente.setCodiceVerifica(1);
        utenteDAO.insert(utente);
        when(req.getParameter("email")).thenReturn("m.rossi12@studenti.unisa.it");
        when(req.getParameter("password")).thenReturn("MarioRossi12");
        servlet.doGet(req, res);
        assertEquals("Devi confermare l'email prima di poter accedere",
                SessionManager.getError(session));
        assertNull(SessionManager.getUtente(session));
    }

    @Test
    void testEmptyEmail() throws Exception {
        when(req.getParameter("email")).thenReturn("");
        when(req.getParameter("password")).thenReturn("MarioRossi12");
        servlet.doPost(req, res);
        assertEquals("Il campo email non rispetta la lunghezza",
                SessionManager.getError(session));
        assertNull(SessionManager.getUtente(session));
    }
}