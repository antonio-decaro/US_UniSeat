package control.admin;

import control.utili.EmailManager;
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
import java.io.IOException;
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

/**
 * Classe JUnit di White-Box testing della servlet EliminaUtente
 * @author Spinelli Gianluca
 */
class EliminaUtenteServletTest {

    @Mock
    private HttpServletRequest req;
    @Mock private HttpServletResponse res;
    @Mock private ServletContext ctx;
    @Mock private HttpSession session;
    @Mock private EmailManager emailManager;
    private UtenteDAO utenteDAO = new StubUtenteDAO();
    private EliminaUtenteServlet servlet;
    private Map<String,Object> attributes = new HashMap<>();

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        servlet = new EliminaUtenteServlet();
        when(req.getServletContext()).thenReturn(ctx);
        when(ctx.getAttribute(EliminaUtenteServlet.UTENTE_DAO_PARAM)).thenReturn(utenteDAO);
        when(req.getSession()).thenReturn(session);
        when(ctx.getContextPath()).thenReturn("");
        when(res.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
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

        Utente u = new Utente();
        u.setTipoUtente(TipoUtente.ADMIN);
        SessionManager.autentica(session, u);

        Utente u1 = new Utente("m.rossi12@studenti.unisa.it", "Mario", "Rossi",
                PasswordEncrypter.criptaPassword("MarioRossi12"), TipoUtente.STUDENTE);
        Utente u2 = new Utente("a.decaro@studenti.unisa.it", "Antonio", "De Caro",
                PasswordEncrypter.criptaPassword("Antonio2"), TipoUtente.STUDENTE);
        Utente u3 = new Utente("c.gravino@studenti.unisa.it", "Carmine", "Gravino",
                PasswordEncrypter.criptaPassword("Gravino1"), TipoUtente.DOCENTE);
        utenteDAO.insert(u1);
        utenteDAO.insert(u2);
        utenteDAO.insert(u3);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void TC_5_1() throws Exception {
        when(req.getParameter("email_utente")).thenReturn(null);
        servlet.doPost(req, res);
        assertEquals("Utente non selezionato",
                SessionManager.getError(session));
    }

    @Test
    void TC_5_2() throws Exception {
        when(req.getParameter("email_utente")).thenReturn("m.rossi12@studenti.unisa.it");
        servlet.doPost(req, res);
        assertNull(utenteDAO.retriveByEmail("m.rossi12@studenti.unisa.it"));
    }

    @Test
    void TC_5_3() throws Exception {
        when(req.getParameter("email_utente")).thenReturn("m.rossi115@studenti.unisa.it");
        servlet.doPost(req, res);
        assertNull(utenteDAO.retriveByEmail("m.rossi115@studenti.unisa.it"));
    }

    @Test
    void isNotAuthenticate() throws Exception {
        when(SessionManager.getUtente(session)).thenReturn(null);
        servlet.doPost(req, res);
    }

    @Test
    void isNotAdmin() throws Exception {
        when(req.getSession()).thenReturn(session);
        Utente u = new Utente();
        u.setTipoUtente(TipoUtente.STUDENTE);
        SessionManager.autentica(session, u);
        servlet.doPost(req, res);
    }
}