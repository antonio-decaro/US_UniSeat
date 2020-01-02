package control.docente;

import control.utili.PassowrdEncrypter;
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
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

class ControllaDisponibilitaServletTest {

    @Mock private HttpServletRequest req;
    @Mock private HttpSession session;
    @Mock private HttpServletResponse res;
    @Mock private ServletContext ctx;
    private StringWriter stringWriter;
    private Utente utente;
    private AulaDAO aulaDAO = new StubAulaDAO();
    private PrenotazioneDAO prenotazioneDAO = new StubPrenotazioneDAO();
    private EdificioDAO edificioDAO = new StubEdificioDAO();
    private ControllaDisponibilitaServlet servlet;
    private Map<String,Object> attributes = new HashMap<>();
    private int responseStatus;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        servlet = new ControllaDisponibilitaServlet();
        stringWriter = new StringWriter();

        when(req.getServletContext()).thenReturn(ctx);
        when(ctx.getAttribute(ControllaDisponibilitaServlet.AULA_DAO)).thenReturn(aulaDAO);
        when(ctx.getAttribute(ControllaDisponibilitaServlet.PRENOTAZIONE_DAO)).thenReturn(prenotazioneDAO);
        when(req.getSession()).thenReturn(session);
        when(req.getContextPath()).thenReturn("");
        when(ctx.getContextPath()).thenReturn("");
        when(res.getWriter()).thenReturn(new PrintWriter(stringWriter));

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

        Mockito.doAnswer((Answer<Object>) invocation -> responseStatus).when(res).getStatus();

        Mockito.doAnswer((Answer<Object>) invocation -> {
            responseStatus = (Integer) invocation.getArguments()[0];
            return null;
        }).when(res).setStatus(anyInt());

        Edificio ed = edificioDAO.retriveByName("F3");
        String dispAula = Files.readString(Paths.get("./src/test/resources/DisponibiltaManagerRes/aula3.json"));
        Aula aula = new Aula(1, "P3", 70, 100, dispAula, ed);
        aula.setId(1);
        ed.getAule().add(aula);
        aulaDAO.insert(aula);
        utente = new Utente("c.gravino@unisa.it", "Carmine", "Gravino",
                PassowrdEncrypter.criptaPassword("CarmineGravino1"), TipoUtente.DOCENTE);
        SessionManager.setError(session, "");
        SessionManager.autentica(session, utente);
    }

    @AfterEach
    void tearDown() throws Exception{
        stringWriter.close();
    }

    @Test
    void testAulaDisponibile() throws Exception {
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn("2019-12-30");
        when(req.getParameter("oraInizio")).thenReturn("14:00:00");
        when(req.getParameter("durata")).thenReturn("1");
        servlet.doPost(req, res);
        assertEquals(HttpServletResponse.SC_OK, res.getStatus());
        assertEquals("false", stringWriter.toString());
    }

    @Test
    void testAulaNonDisponibile() throws Exception {
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn("2019-12-31");
        when(req.getParameter("oraInizio")).thenReturn("14:00:00");
        when(req.getParameter("durata")).thenReturn("1");
        servlet.doPost(req, res);
        assertEquals(HttpServletResponse.SC_OK, res.getStatus());
        assertEquals("true", stringWriter.toString());
    }

    @Test
    void testNoUtente() throws Exception {
        SessionManager.autentica(session, null);
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn("2019-12-31");
        when(req.getParameter("oraInizio")).thenReturn("14:00:00");
        when(req.getParameter("durata")).thenReturn("1");
        servlet.doPost(req, res);
        assertEquals(HttpServletResponse.SC_FORBIDDEN, res.getStatus());
        assertEquals("Utente non loggato", stringWriter.toString());
    }

    @Test
    void testUtenteNonAutorizzato() throws Exception {
        utente.setTipoUtente(TipoUtente.STUDENTE);
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn("2019-12-31");
        when(req.getParameter("oraInizio")).thenReturn("14:00:00");
        when(req.getParameter("durata")).thenReturn("1");
        servlet.doPost(req, res);
        assertEquals(HttpServletResponse.SC_FORBIDDEN, res.getStatus());
        assertEquals("Non hai i permessi per accedere a questa risorsa", stringWriter.toString());
    }

    @Test
    void testBadAula() throws Exception {
        when(req.getParameter("aula")).thenReturn("-1");
        when(req.getParameter("data")).thenReturn("2019-12-31");
        when(req.getParameter("oraInizio")).thenReturn("14:00:00");
        when(req.getParameter("durata")).thenReturn("1");
        servlet.doPost(req, res);
        assertEquals(HttpServletResponse.SC_BAD_REQUEST, res.getStatus());
        assertEquals("Aula non valida", stringWriter.toString());
    }

     @Test
    void testEmptyAula() throws Exception {
        when(req.getParameter("aula")).thenReturn("");
        when(req.getParameter("data")).thenReturn("2019-12-31");
        when(req.getParameter("oraInizio")).thenReturn("14:00:00");
        when(req.getParameter("durata")).thenReturn("1");
        servlet.doPost(req, res);
        assertEquals(HttpServletResponse.SC_BAD_REQUEST, res.getStatus());
        assertEquals("Aula non valida", stringWriter.toString());
    }

     @Test
    void testNoAula() throws Exception {
        when(req.getParameter("aula")).thenReturn(null);
        when(req.getParameter("data")).thenReturn("2019-12-31");
        when(req.getParameter("oraInizio")).thenReturn("14:00:00");
        when(req.getParameter("durata")).thenReturn("1");
        servlet.doPost(req, res);
        assertEquals(HttpServletResponse.SC_BAD_REQUEST, res.getStatus());
        assertEquals("Aula non valida", stringWriter.toString());
    }

    @Test
    void testBadDurata() throws Exception {
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn("2019-12-31");
        when(req.getParameter("oraInizio")).thenReturn("14:00:00");
        when(req.getParameter("durata")).thenReturn("-1");
        servlet.doPost(req, res);
        assertEquals(HttpServletResponse.SC_BAD_REQUEST, res.getStatus());
        assertEquals("Durata non valida", stringWriter.toString());
    }

    @Test
    void testEmptyDurata() throws Exception {
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn("2019-12-31");
        when(req.getParameter("oraInizio")).thenReturn("14:00:00");
        when(req.getParameter("durata")).thenReturn("");
        servlet.doPost(req, res);
        assertEquals(HttpServletResponse.SC_BAD_REQUEST, res.getStatus());
        assertEquals("Durata non valida", stringWriter.toString());
    }

    @Test
    void testNoDurata() throws Exception {
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn("2019-12-31");
        when(req.getParameter("oraInizio")).thenReturn("14:00:00");
        when(req.getParameter("durata")).thenReturn(null);
        servlet.doPost(req, res);
        assertEquals(HttpServletResponse.SC_BAD_REQUEST, res.getStatus());
        assertEquals("Durata non valida", stringWriter.toString());
    }

    @Test
    void testBadData() throws Exception {
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn("31-12-2019");
        when(req.getParameter("oraInizio")).thenReturn("14:00:00");
        when(req.getParameter("durata")).thenReturn("1");
        servlet.doPost(req, res);
        assertEquals(HttpServletResponse.SC_BAD_REQUEST, res.getStatus());
        assertEquals("Formato data non valido", stringWriter.toString());
    }

    @Test
    void testEmptyData() throws Exception {
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn("");
        when(req.getParameter("oraInizio")).thenReturn("14:00:00");
        when(req.getParameter("durata")).thenReturn("1");
        servlet.doPost(req, res);
        assertEquals(HttpServletResponse.SC_BAD_REQUEST, res.getStatus());
        assertEquals("Data non valida", stringWriter.toString());
    }

    @Test
    void testNoData() throws Exception {
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn(null);
        when(req.getParameter("oraInizio")).thenReturn("14:00:00");
        when(req.getParameter("durata")).thenReturn("1");
        servlet.doPost(req, res);
        assertEquals(HttpServletResponse.SC_BAD_REQUEST, res.getStatus());
        assertEquals("Data non valida", stringWriter.toString());
    }

    @Test
    void testBadOra() throws Exception {
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn("2019-12-31");
        when(req.getParameter("oraInizio")).thenReturn("14pm");
        when(req.getParameter("durata")).thenReturn("1");
        servlet.doPost(req, res);
        assertEquals(HttpServletResponse.SC_BAD_REQUEST, res.getStatus());
        assertEquals("Formato ora non valido", stringWriter.toString());
    }

    @Test
    void testEmptyOra() throws Exception {
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn("2019-12-31");
        when(req.getParameter("oraInizio")).thenReturn("");
        when(req.getParameter("durata")).thenReturn("1");
        servlet.doPost(req, res);
        assertEquals(HttpServletResponse.SC_BAD_REQUEST, res.getStatus());
        assertEquals("Ora non valida", stringWriter.toString());
    }

    @Test
    void testNoOra() throws Exception {
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn("2019-12-31");
        when(req.getParameter("oraInizio")).thenReturn(null);
        when(req.getParameter("durata")).thenReturn("1");
        servlet.doPost(req, res);
        assertEquals(HttpServletResponse.SC_BAD_REQUEST, res.getStatus());
        assertEquals("Ora non valida", stringWriter.toString());
    }
}