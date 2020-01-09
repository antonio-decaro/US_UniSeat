package control.studente;

import control.utili.SessionManager;
import model.dao.AulaDAO;
import model.dao.EdificioDAO;
import model.dao.PrenotazioneDAO;
import model.daostub.StubAulaDAO;
import model.daostub.StubEdificioDAO;
import model.daostub.StubPrenotazioneDAO;
import model.pojo.Aula;
import model.pojo.Edificio;
import model.pojo.TipoUtente;
import model.pojo.Utente;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Clock;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class PrenotaPostoServletBlackBoxTest {

    @Mock private HttpServletRequest req;
    @Mock private HttpServletResponse res;
    @Mock private ServletContext ctx;
    @Mock private HttpSession session;
    private static Utente utente;
    private AulaDAO aulaDAO = new StubAulaDAO();
    private PrenotazioneDAO prenotazioneDAO = new StubPrenotazioneDAO();
    private EdificioDAO edificioDAO = new StubEdificioDAO();
    private PrenotaPostoServlet servlet;
    private Map<String,Object> attributes = new HashMap<>();

    @BeforeAll
    static void init(){
        utente = new Utente();
        utente.setTipoUtente(TipoUtente.STUDENTE);
    }

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        servlet = new PrenotaPostoServlet();
        StringWriter stringWriter = new StringWriter();
        when(req.getServletContext()).thenReturn(ctx);
        when(ctx.getAttribute(PrenotaPostoServlet.AULA_DAO)).thenReturn(aulaDAO);
        when(ctx.getAttribute(PrenotaPostoServlet.EDIFICIO_DAO)).thenReturn(edificioDAO);
        when(ctx.getAttribute(PrenotaPostoServlet.PRENOTAZIONE_DAO)).thenReturn(prenotazioneDAO);
        when(ctx.getAttribute(PrenotaPostoServlet.CLOCK)).thenReturn(Clock.systemDefaultZone());
        when(req.getSession()).thenReturn(session);
        when(req.getContextPath()).thenReturn("");
        when(ctx.getContextPath()).thenReturn("");
        when(session.isNew()).thenReturn(false);
        when(res.getWriter()).thenReturn(new PrintWriter(stringWriter));
        doNothing().when(res).setStatus(anyInt());
        doNothing().when(res).sendRedirect(anyString());

        Mockito.doAnswer((Answer<Object>) invocation -> {
            try {
                String key = (String) invocation.getArguments()[0];
                return attributes.get(key);
            } catch (NullPointerException e) {
                return null;
            }
        }).when(session).getAttribute(anyString());

        Mockito.doAnswer((Answer<Object>) invocation -> {
            String key = (String) invocation.getArguments()[0];
            Object value = invocation.getArguments()[1];
            attributes.put(key, value);
            return null;
        }).when(session).setAttribute(anyString(), any());

        SessionManager.setError(session, "");
        SessionManager.autentica(session, utente);

        Edificio ed = edificioDAO.retriveByName("F3");
        String dispP3 = Files.readString(Paths.get("./src/test/resources/TC_3/disp_aulaP3.json"));
        String dispP4 = Files.readString(Paths.get("./src/test/resources/TC_3/disp_aulaP4.json"));
        Aula aulaP3 = new Aula("P3", 70, 100, dispP3, ed);
        Aula aulaP4 = new Aula("P4", 0, 100, dispP4, ed);
        aulaP3.setId(1);
        aulaP4.setId(2);
        ed.getAule().add(aulaP3);
        ed.getAule().add(aulaP4);
        aulaDAO.insert(aulaP3);
        aulaDAO.insert(aulaP4);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void TC_3_1() throws Exception {
        when(req.getParameter("edificio")).thenReturn("");
        when(req.getParameter("aula")).thenReturn("3");
        when(req.getParameter("durata")).thenReturn("60");
        servlet.doPost(req, res);
        assertEquals("Edificio non valido", SessionManager.getError(session));
    }

    @Test
    void TC_3_2() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("aula")).thenReturn("");
        when(req.getParameter("durata")).thenReturn("60");
        servlet.doPost(req, res);
        assertEquals("Aula non valida", SessionManager.getError(session));
    }

    @Test
    void TC_3_3() throws Exception {

        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("aula")).thenReturn("2");
        when(req.getParameter("durata")).thenReturn("60");
        servlet.doPost(req, res);
        assertEquals("Aula non disponibile", SessionManager.getError(session));
    }

    @Test
    void TC_3_4() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("durata")).thenReturn("");
        servlet.doPost(req, res);
        assertEquals("Durata non valida", SessionManager.getError(session));
    }

    @Test
    void TC_3_5() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("durata")).thenReturn("60");
        servlet.doPost(req, res);
        assertEquals("", SessionManager.getError(session));
        assertEquals(1, prenotazioneDAO.retriveAll().size());
    }
}