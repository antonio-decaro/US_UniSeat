package control.admin;

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

class IscrizioneDocenteServletTest {

    @Mock
    private HttpServletRequest req;
    @Mock private HttpServletResponse res;
    @Mock private ServletContext ctx;
    @Mock private HttpSession session;
    private UtenteDAO utenteDAO = new StubUtenteDAO();
    private IscrizioneDocenteServlet servlet;
    private Map<String,Object> attributes = new HashMap<>();

    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        servlet = new IscrizioneDocenteServlet();
        when(req.getServletContext()).thenReturn(ctx);
        when(ctx.getAttribute(IscrizioneDocenteServlet.UTENTE_DAO_PARAM)).thenReturn(utenteDAO);
        when(req.getSession()).thenReturn(session);
        when(ctx.getContextPath()).thenReturn("");
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

        Utente admin = new Utente();
        admin.setTipoUtente(TipoUtente.ADMIN);
        SessionManager.autentica(session, admin);
        Utente u = new Utente("m.rossi12@studenti.unisa.it", "Mario", "Rossi", PasswordEncrypter.criptaPassword("MarioRossi12"), TipoUtente.STUDENTE);
        Utente u2 = new Utente("a.decaro@studenti.unisa.it", "Antonio", "De Caro", "Antonio2", TipoUtente.STUDENTE);
        Utente u3 = new Utente("c.gravino@unisa.it", "Carmine", "Gravino", "Carmine2", TipoUtente.DOCENTE);
        utenteDAO.insert(u);
        utenteDAO.insert(u2);
        utenteDAO.insert(u3);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void TC_1_1() throws Exception {
        when(req.getParameter("nome")).thenReturn("");
        when(req.getParameter("cognome")).thenReturn("Rossi");
        when(req.getParameter("email")).thenReturn("m.rossi13@unisa.it");
        when(req.getParameter("password")).thenReturn("MarioRossi12");
        when(req.getParameter("confPassword")).thenReturn("MarioRossi12");
        servlet.doPost(req, res);
        assertEquals("Il campo Nome non rispetta la lunghezza",
                SessionManager.getError(session));
        assertNull(utenteDAO.retriveByEmail("m.rossi13@unisa.it"));
    }

    @Test
    void TC_1_2() throws Exception {
        when(req.getParameter("nome")).thenReturn(null);
        when(req.getParameter("cognome")).thenReturn("Rossi");
        when(req.getParameter("email")).thenReturn("m.rossi13@unisa.it");
        when(req.getParameter("password")).thenReturn("MarioRossi12");
        when(req.getParameter("confPassword")).thenReturn("MarioRossi12");
        servlet.doPost(req, res);
        assertEquals("Il campo Nome non rispetta la lunghezza",
                SessionManager.getError(session));
        assertNull(utenteDAO.retriveByEmail("m.rossi13@unisa.it"));
    }

    @Test
    void TC_1_3() throws Exception {
        when(req.getParameter("nome")).thenReturn("Mario");
        when(req.getParameter("cognome")).thenReturn(null);
        when(req.getParameter("email")).thenReturn("m.rossi13@unisa.it");
        when(req.getParameter("password")).thenReturn("MarioRossi12");
        when(req.getParameter("confPassword")).thenReturn("MarioRossi12");
        servlet.doPost(req, res);
        assertEquals("Il campo Cognome non rispetta la lunghezza",
                SessionManager.getError(session));
        assertNull(utenteDAO.retriveByEmail("m.rossi13@unisa.it"));
    }

    @Test
    void TC_1_4() throws Exception {
        when(req.getParameter("nome")).thenReturn("abcdefghilmnopqrstuvz");
        when(req.getParameter("cognome")).thenReturn("Rossi");
        when(req.getParameter("email")).thenReturn("m.rossi13@unisa.it");
        when(req.getParameter("password")).thenReturn("MarioRossi12");
        when(req.getParameter("confPassword")).thenReturn("MarioRossi12");
        servlet.doPost(req, res);
        assertEquals("Il campo Nome non rispetta la lunghezza",
                SessionManager.getError(session));
        assertNull(utenteDAO.retriveByEmail("m.rossi13@unisa.it"));
    }
    @Test
    void TC_1_5() throws Exception {
        when(req.getParameter("nome")).thenReturn("Mar10");
        when(req.getParameter("cognome")).thenReturn("Rossi");
        when(req.getParameter("email")).thenReturn("m.rossi13@unisa.it");
        when(req.getParameter("password")).thenReturn("MarioRossi12");
        when(req.getParameter("confPassword")).thenReturn("MarioRossi12");
        servlet.doPost(req, res);
        assertEquals("Il campo Nome non rispetta il formato",
                SessionManager.getError(session));
        assertNull(utenteDAO.retriveByEmail("m.rossi13@unisa.it"));
    }
    @Test
    void TC_1_6() throws Exception {
        when(req.getParameter("nome")).thenReturn("Mario");
        when(req.getParameter("cognome")).thenReturn("");
        when(req.getParameter("email")).thenReturn("m.rossi13@unisa.it");
        when(req.getParameter("password")).thenReturn("MarioRossi12");
        when(req.getParameter("confPassword")).thenReturn("MarioRossi12");
        servlet.doPost(req, res);
        assertEquals("Il campo Cognome non rispetta la lunghezza",
                SessionManager.getError(session));
        assertNull(utenteDAO.retriveByEmail("m.rossi13@unisa.it"));
    }
    @Test
    void TC_1_7() throws Exception {
        when(req.getParameter("nome")).thenReturn("Mario");
        when(req.getParameter("cognome")).thenReturn("abcdefhghilmnopqrstuvz");
        when(req.getParameter("email")).thenReturn("m.rossi13@unisa.it");
        when(req.getParameter("password")).thenReturn("MarioRossi12");
        when(req.getParameter("confPassword")).thenReturn("MarioRossi12");
        servlet.doPost(req, res);
        assertEquals("Il campo Cognome non rispetta la lunghezza",
                SessionManager.getError(session));
        assertNull(utenteDAO.retriveByEmail("m.rossi13@unisa.it"));
    }
    @Test
    void TC_1_8() throws Exception {
        when(req.getParameter("nome")).thenReturn("Mario");
        when(req.getParameter("cognome")).thenReturn("R00ss1");
        when(req.getParameter("email")).thenReturn("m.rossi13@unisa.it");
        when(req.getParameter("password")).thenReturn("MarioRossi12");
        when(req.getParameter("confPassword")).thenReturn("MarioRossi12");
        servlet.doPost(req, res);
        assertEquals("Il campo Cognome non rispetta il formato",
                SessionManager.getError(session));
        assertNull(utenteDAO.retriveByEmail("m.rossi13@unisa.it"));
    }
    @Test
    void TC_1_9() throws Exception {
        when(req.getParameter("nome")).thenReturn("Mario");
        when(req.getParameter("cognome")).thenReturn("Rossi");
        when(req.getParameter("email")).thenReturn("@unisa.it");
        when(req.getParameter("password")).thenReturn("MarioRossi12");
        when(req.getParameter("confPassword")).thenReturn("MarioRossi12");
        servlet.doPost(req, res);
        assertEquals("Il campo E-mail non rispetta la lunghezza",
                SessionManager.getError(session));
        assertNull(utenteDAO.retriveByEmail("m.rossi13@unisa.it"));
    }
    @Test
    void TC_1_10() throws Exception {
        when(req.getParameter("nome")).thenReturn("Mario");
        when(req.getParameter("cognome")).thenReturn("Rossi");
        when(req.getParameter("email")).thenReturn("m.rossi13@domain.com");
        when(req.getParameter("password")).thenReturn("MarioRossi12");
        when(req.getParameter("confPassword")).thenReturn("MarioRossi12");
        servlet.doPost(req, res);
        assertEquals("Il campo E-mail non rispetta il formato",
                SessionManager.getError(session));
        assertNull(utenteDAO.retriveByEmail("m.rossi13@unisa.it"));
    }
    @Test
    void TC_1_11() throws Exception {
        when(req.getParameter("nome")).thenReturn("Mario");
        when(req.getParameter("cognome")).thenReturn("Rossi");
        when(req.getParameter("email")).thenReturn("m.rossi13@unisa.it");
        when(req.getParameter("password")).thenReturn("Abc1");
        when(req.getParameter("confPassword")).thenReturn("MarioRossi12");
        servlet.doPost(req, res);
        assertEquals("Il campo Password non rispetta la lunghezza",
                SessionManager.getError(session));
        assertNull(utenteDAO.retriveByEmail("m.rossi13@unisa.it"));
    }
    @Test
    void TC_1_12() throws Exception {
        when(req.getParameter("nome")).thenReturn("Mario");
        when(req.getParameter("cognome")).thenReturn("Rossi");
        when(req.getParameter("email")).thenReturn("m.rossi13@unisa.it");
        when(req.getParameter("password")).thenReturn("mariorossi");
        when(req.getParameter("confPassword")).thenReturn("MarioRossi12");
        servlet.doPost(req, res);
        assertEquals("Il campo Password non rispetta il formato",
                SessionManager.getError(session));
        assertNull(utenteDAO.retriveByEmail("m.rossi13@unisa.it"));
    }
    @Test
    void TC_1_13() throws Exception {
        when(req.getParameter("nome")).thenReturn("Mario");
        when(req.getParameter("cognome")).thenReturn("Rossi");
        when(req.getParameter("email")).thenReturn("m.rossi13@unisa.it");
        when(req.getParameter("password")).thenReturn("MarioRossi12");
        when(req.getParameter("confPassword")).thenReturn("LuigiVerdi14");
        servlet.doPost(req, res);
        assertEquals("Le password non corrispondono",
                SessionManager.getError(session));
        assertNull(utenteDAO.retriveByEmail("m.rossi13@unisa.it"));
    }
    @Test
    void TC_1_14() throws Exception {
        when(req.getParameter("nome")).thenReturn("Mario");
        when(req.getParameter("cognome")).thenReturn("Rossi");
        when(req.getParameter("email")).thenReturn("m.rossi13@unisa.it");
        when(req.getParameter("password")).thenReturn("MarioRossi12");
        when(req.getParameter("confPassword")).thenReturn("MarioRossi12");
        servlet.doPost(req, res);
        assertNull(SessionManager.getError(session));
        assertNotNull(utenteDAO.retriveByEmail("m.rossi13@unisa.it"));
    }
    @Test
    void testGetAndAlradyAuth() throws Exception {
        Utente u = new Utente();
        u.setTipoUtente(TipoUtente.STUDENTE);
        SessionManager.autentica(session, u);
        servlet.doGet(req, res);
    }
    @Test
    void testAlradyExist() throws Exception {
        when(req.getParameter("nome")).thenReturn("Mario");
        when(req.getParameter("cognome")).thenReturn("Rossi");
        when(req.getParameter("email")).thenReturn("c.gravino@unisa.it");
        when(req.getParameter("password")).thenReturn("MarioRossi12");
        when(req.getParameter("confPassword")).thenReturn("MarioRossi12");
        servlet.doPost(req, res);
        assertEquals("Utente già esistente", SessionManager.getError(session));
        assertNotNull(utenteDAO.retriveByEmail("c.gravino@unisa.it"));
    }
}