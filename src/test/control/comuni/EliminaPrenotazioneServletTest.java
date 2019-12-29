package control.comuni;

import control.utili.SessionManager;
import model.dao.AulaDAO;
import model.dao.PrenotazioneDAO;
import model.dao.UtenteDAO;
import model.database.StubAulaDAO;
import model.database.StubPrenotazioneDAO;
import model.database.StubUtenteDAO;

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

public class EliminaPrenotazioneServletTest {
    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse res;
    @Mock
    private ServletContext ctx;
    @Mock
    private HttpSession session;
    private UtenteDAO utenteDAO = new StubUtenteDAO();
    private PrenotazioneDAO prenotazioneDAO = new StubPrenotazioneDAO();
    private AulaDAO aulaDAO= new StubAulaDAO();
    private EliminaPrenotazioneServlet servlet;
    private Map<String, Object> attributes = new HashMap<>();


    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        servlet = new EliminaPrenotazioneServlet();
        when(req.getServletContext()).thenReturn(ctx);
        when(ctx.getAttribute(EliminaPrenotazioneServlet.PRENOTAZIONE_DAO_PARAM)).thenReturn(prenotazioneDAO);
        when(ctx.getAttribute(EliminaPrenotazioneServlet.AULA_DAO_PARAM)).thenReturn(aulaDAO);
        when(req.getSession()).thenReturn(session);
        when(req.getContextPath()).thenReturn("");
        when(ctx.getContextPath()).thenReturn("");
        when(SessionManager.isAlradyAuthenticated(session)).thenReturn(true);
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
    void testPostAndAuth() throws Exception {
        servlet.doPost(req, res);
        assertEquals("LogIn non effettuato",
                session.getAttribute("SessionManager.error"));
    }

    @Test
    void testExc() throws Exception {
        SessionManager.autentica(session, utenteDAO.retriveAll().get(0));
        String id = "-1";
        when(req.getParameter("id_prenotazione")).thenReturn(id);
        servlet.doGet(req, res);
        assertEquals("L'id " + id + " non è valido.",
                session.getAttribute("SessionManager.error"));
    }

    @Test
    void TC_1_1() throws Exception {
        SessionManager.autentica(session, utenteDAO.retriveAll().get(1));
        when(req.getParameter("id_prenotazione")).thenReturn("104");
        servlet.doGet(req, res);
        assertEquals("Prenotazione non presente nel DB",
                session.getAttribute("SessionManager.error"));
    }

    @Test
    void TC_1_2() throws Exception {
        SessionManager.autentica(session, utenteDAO.retriveAll().get(1));
        when(req.getParameter("id_prenotazione")).thenReturn("1");
        servlet.doGet(req, res);
        assertNull(session.getAttribute("SessionManager.error"));
    }

    @Test
    void TC_1_3() throws Exception {
        SessionManager.autentica(session, utenteDAO.retriveAll().get(2));
        when(req.getParameter("id_prenotazione")).thenReturn("2");
        servlet.doGet(req, res);
        assertNull(session.getAttribute("SessionManager.error"));
    }
}
