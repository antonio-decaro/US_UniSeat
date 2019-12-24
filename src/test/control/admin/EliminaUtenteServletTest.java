package control.admin;

import control.utili.EmailManager;
import control.utili.SessionManager;
import model.dao.UtenteDAO;
import model.database.UtenteDAOStub;
import model.pojo.TipoUtente;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import javax.jms.Session;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
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
    private UtenteDAO utenteDAO = new UtenteDAOStub();
    private EliminaUtenteServlet servlet;
    private Map<String,Object> attributes = new HashMap<>();
    private SessionManager sm = new SessionManager();

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        servlet = new EliminaUtenteServlet();
        when(req.getServletContext()).thenReturn(ctx);
        when(ctx.getAttribute(EliminaUtenteServlet.UTENTE_DAO_PARAM)).thenReturn(utenteDAO);
        when(req.getSession()).thenReturn(session);
        when(ctx.getContextPath()).thenReturn("");
        when(session.isNew()).thenReturn(false);
        doNothing().when(res).sendRedirect(anyString());
        doNothing().when(emailManager).inviaEmailConferma(any());

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
    void TC_5_1() throws Exception {
        when(req.getParameter("email_utente")).thenReturn(null);
        servlet.doPost(req, res);
        assertEquals("Utente non selezionato",
                req.getParameter("erroreEliminazioneUtente"));
    }

    @Test
    void TC_5_2() throws Exception {
        when(req.getParameter("email_utente")).thenReturn("g.spinelli18@studenti.unisa.it");
        servlet.doPost(req, res);
        assertEquals(null, utenteDAO.retriveByEmail("g.spinelli18@studenti.unisa.it"));
    }

    @Test
    void TC_5_3() throws Exception {
        when(req.getSession().isNew()).thenReturn(true);
        servlet.doPost(req, res);
    }

    @Test
    void TC_5_4() throws Exception {
        when(req.getSession()).thenReturn(session);
        when(sm.getUtente(session).getTipoUtente()).thenReturn(TipoUtente.STUDENTE);
        servlet.doPost(req, res);
    }

}