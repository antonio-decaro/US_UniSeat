package control.docente;

import control.utili.EmailManager;
import control.utili.PassowrdEncrypter;
import control.utili.SessionManager;
import model.dao.AulaDAO;
import model.dao.EdificioDAO;
import model.dao.PrenotazioneDAO;
import model.database.StubAulaDAO;
import model.database.StubEdificioDAO;
import model.database.StubPrenotazioneDAO;
import model.pojo.*;
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class PrenotaAulaServletBlackBoxTest {

    @Mock
    private HttpServletRequest req;
    @Mock private HttpServletResponse res;
    @Mock private ServletContext ctx;
    @Mock private HttpSession session;
    @Mock private EmailManager emailManager;
    private Utente utente;
    private AulaDAO aulaDAO = new StubAulaDAO();
    private PrenotazioneDAO prenotazioneDAO = new StubPrenotazioneDAO();
    private EdificioDAO edificioDAO = new StubEdificioDAO();
    private PrenotaAulaServlet servlet;
    private Map<String,Object> attributes = new HashMap<>();

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        servlet = new PrenotaAulaServlet();
        when(req.getServletContext()).thenReturn(ctx);
        when(ctx.getAttribute(PrenotaAulaServlet.AULA_DAO)).thenReturn(aulaDAO);
        when(ctx.getAttribute(PrenotaAulaServlet.EDIFICIO_DAO)).thenReturn(edificioDAO);
        when(ctx.getAttribute(PrenotaAulaServlet.PRENOTAZIONE_DAO)).thenReturn(prenotazioneDAO);
        when(ctx.getAttribute(PrenotaAulaServlet.EMAIL_MANAGER)).thenReturn(emailManager);
        when(req.getSession()).thenReturn(session);
        when(req.getContextPath()).thenReturn("");
        when(ctx.getContextPath()).thenReturn("");
        when(session.isNew()).thenReturn(false);
        doNothing().when(res).sendRedirect(anyString());
        doNothing().when(emailManager).inviaEmailConferma(any());

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

        Edificio ed = edificioDAO.retriveByName("F3");
        String dispP3 = Files.readString(Paths.get("./src/test/resources/TC_4/disp_aulaP3.json"));
        Aula aulaP3 = new Aula("P3", 70, 100, dispP3, ed);
        aulaP3.setId(1);
        ed.getAule().add(aulaP3);
        utente = new Utente("c.gravino@unisa.it", "Carmine", "Gravino",
                PassowrdEncrypter.criptaPassword("CarmineGravino1"), TipoUtente.DOCENTE);
        aulaDAO.insert(aulaP3);

        SessionManager.setError(session, "");
        SessionManager.autentica(session, utente);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void TC_4_3() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("aula")).thenReturn("2");
        when(req.getParameter("data")).thenReturn("LUNEDI");
        when(req.getParameter("oraInizio")).thenReturn("15:00:00");
        when(req.getParameter("durata")).thenReturn("2");
        servlet.doPost(req, res);
        assertEquals("Aula non valida", SessionManager.getError(session));
    }

    @Test
    void TC_4_4() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn("");
        when(req.getParameter("oraInizio")).thenReturn("15:00:00");
        when(req.getParameter("durata")).thenReturn("2");
        servlet.doPost(req, res);
        assertEquals("Data non valida", SessionManager.getError(session));
    }

    @Test
    void TC_4_5() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn("LUNEDI");
        when(req.getParameter("oraInizio")).thenReturn("");
        when(req.getParameter("durata")).thenReturn("2");
        servlet.doPost(req, res);
        assertEquals("Ora non valida", SessionManager.getError(session));
    }

    @Test
    void TC_4_6() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn("LUNEDI");
        when(req.getParameter("oraInizio")).thenReturn("21:00:00");
        when(req.getParameter("durata")).thenReturn("2");
        servlet.doPost(req, res);
        assertEquals("Aula non disponibile", SessionManager.getError(session));
    }

    @Test
    void TC_4_7() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn("LUNEDI");
        when(req.getParameter("oraInizio")).thenReturn("15:00:00");
        when(req.getParameter("durata")).thenReturn("");
        servlet.doPost(req, res);
        assertEquals("Durata non valida", SessionManager.getError(session));
    }

    @Test
    void TC_4_8() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn("LUNEDI");
        when(req.getParameter("oraInizio")).thenReturn("15:00:00");
        when(req.getParameter("durata")).thenReturn("2");
        servlet.doPost(req, res);
        assertEquals("", SessionManager.getError(session));
        assertEquals(1, prenotazioneDAO.retriveAll().size());
        Prenotazione p = prenotazioneDAO.retriveAll().get(0);
        assertEquals(utente.getTipoUtente(), p.getUtente().getTipoUtente());
        assertEquals(utente.getEmail(), p.getUtente().getEmail());
    }
}