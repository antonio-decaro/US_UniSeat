package control.docente;

import control.utili.EmailManager;
import control.utili.PassowrdEncrypter;
import control.utili.SessionManager;
import model.dao.AulaDAO;
import model.dao.EdificioDAO;
import model.dao.PrenotazioneDAO;
import model.daostub.StubAulaDAO;
import model.daostub.StubEdificioDAO;
import model.daostub.StubPrenotazioneDAO;
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
import java.sql.Time;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

class PrenotaAulaServletTest {

    @Mock private HttpServletRequest req;
    @Mock private HttpServletResponse res;
    @Mock private ServletContext ctx;
    @Mock private HttpSession session;
    @Mock private EmailManager emailManager;
    @Mock private Clock clock;
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
        when(ctx.getAttribute(PrenotaAulaServlet.CLOCK)).thenReturn(Clock.systemDefaultZone());
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

    @Test
    void testMartedi() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn("MARTEDI");
        when(req.getParameter("oraInizio")).thenReturn("15:00:00");
        when(req.getParameter("durata")).thenReturn("2");
        servlet.doPost(req, res);
        assertEquals("", SessionManager.getError(session));
        assertEquals(1, prenotazioneDAO.retriveAll().size());
        Prenotazione p = prenotazioneDAO.retriveAll().get(0);
        assertEquals(utente.getTipoUtente(), p.getUtente().getTipoUtente());
        assertEquals(utente.getEmail(), p.getUtente().getEmail());
    }

    @Test
    void testMercoledi() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn("MERCOLEDI");
        when(req.getParameter("oraInizio")).thenReturn("15:00:00");
        when(req.getParameter("durata")).thenReturn("2");
        servlet.doPost(req, res);
        assertEquals("", SessionManager.getError(session));
        assertEquals(1, prenotazioneDAO.retriveAll().size());
        Prenotazione p = prenotazioneDAO.retriveAll().get(0);
        assertEquals(utente.getTipoUtente(), p.getUtente().getTipoUtente());
        assertEquals(utente.getEmail(), p.getUtente().getEmail());
    }

    @Test
    void testGiovedi() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn("GIOVEDI");
        when(req.getParameter("oraInizio")).thenReturn("15:00:00");
        when(req.getParameter("durata")).thenReturn("2");
        servlet.doPost(req, res);
        assertEquals("", SessionManager.getError(session));
        assertEquals(1, prenotazioneDAO.retriveAll().size());
        Prenotazione p = prenotazioneDAO.retriveAll().get(0);
        assertEquals(utente.getTipoUtente(), p.getUtente().getTipoUtente());
        assertEquals(utente.getEmail(), p.getUtente().getEmail());
    }

    @Test
    void testVenerdi() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn("VENERDI");
        when(req.getParameter("oraInizio")).thenReturn("15:00:00");
        when(req.getParameter("durata")).thenReturn("2");
        servlet.doPost(req, res);
        assertEquals("", SessionManager.getError(session));
        assertEquals(1, prenotazioneDAO.retriveAll().size());
        Prenotazione p = prenotazioneDAO.retriveAll().get(0);
        assertEquals(utente.getTipoUtente(), p.getUtente().getTipoUtente());
        assertEquals(utente.getEmail(), p.getUtente().getEmail());
    }

    @Test
    void testSabato() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn("SABATO");
        when(req.getParameter("oraInizio")).thenReturn("15:00:00");
        when(req.getParameter("durata")).thenReturn("2");
        servlet.doPost(req, res);
        assertEquals("Data non valida", SessionManager.getError(session));
    }

    @Test
    void testUserAsStudente() throws Exception {
        utente.setTipoUtente(TipoUtente.STUDENTE);
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn("LUNEDI");
        when(req.getParameter("oraInizio")).thenReturn("15:00:00");
        when(req.getParameter("durata")).thenReturn("2");
        servlet.doPost(req, res);
        assertEquals("Non hai i permessi per accedere a questa funzionalità", SessionManager.getError(session));
    }

    @Test
    void testNoUser() throws Exception {
        SessionManager.autentica(session, null);
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn("LUNEDI");
        when(req.getParameter("oraInizio")).thenReturn("15:00:00");
        when(req.getParameter("durata")).thenReturn("2");
        servlet.doPost(req, res);
        assertEquals("Utente non loggato", SessionManager.getError(session));
    }

    @Test
    void testNoAula() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("aula")).thenReturn(null);
        when(req.getParameter("data")).thenReturn("LUNEDI");
        when(req.getParameter("oraInizio")).thenReturn("15:00:00");
        when(req.getParameter("durata")).thenReturn("2");
        servlet.doPost(req, res);
        assertEquals("Aula non valida", SessionManager.getError(session));
    }

    @Test
    void testEmptyAula() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("aula")).thenReturn("");
        when(req.getParameter("data")).thenReturn("LUNEDI");
        when(req.getParameter("oraInizio")).thenReturn("15:00:00");
        when(req.getParameter("durata")).thenReturn("2");
        servlet.doPost(req, res);
        assertEquals("Aula non valida", SessionManager.getError(session));
    }

    @Test
    void testNoEdificio() throws Exception {
        when(req.getParameter("edificio")).thenReturn(null);
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn("LUNEDI");
        when(req.getParameter("oraInizio")).thenReturn("15:00:00");
        when(req.getParameter("durata")).thenReturn("2");
        servlet.doPost(req, res);
        assertEquals("Edificio non valido", SessionManager.getError(session));
    }

    @Test
    void testNoData() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn(null);
        when(req.getParameter("oraInizio")).thenReturn("15:00:00");
        when(req.getParameter("durata")).thenReturn("2");
        servlet.doPost(req, res);
        assertEquals("Data non valida", SessionManager.getError(session));
    }

    @Test
    void testNoOra() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn("LUNEDI");
        when(req.getParameter("oraInizio")).thenReturn(null);
        when(req.getParameter("durata")).thenReturn("2");
        servlet.doPost(req, res);
        assertEquals("Ora non valida", SessionManager.getError(session));
    }

    @Test
    void testNoDurata() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn("LUNEDI");
        when(req.getParameter("oraInizio")).thenReturn("15:00:00");
        when(req.getParameter("durata")).thenReturn(null);
        servlet.doPost(req, res);
        assertEquals("Durata non valida", SessionManager.getError(session));
    }

    @Test
    void testNonMatchingAulaEdificio() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F2");
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn("LUNEDI");
        when(req.getParameter("oraInizio")).thenReturn("15:00:00");
        when(req.getParameter("durata")).thenReturn("2");
        servlet.doPost(req, res);
        assertEquals("Aula non valida", SessionManager.getError(session));
    }

    @Test
    void testBadOraFormat() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn("LUNEDI");
        when(req.getParameter("oraInizio")).thenReturn("15.00.00");
        when(req.getParameter("durata")).thenReturn("2");
        servlet.doPost(req, res);
        assertEquals("Formato ora non valido", SessionManager.getError(session));
    }

    @Test
    void testDurataLessThanZero() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn("LUNEDI");
        when(req.getParameter("oraInizio")).thenReturn("15:00:00");
        when(req.getParameter("durata")).thenReturn("-1");
        servlet.doPost(req, res);
        assertEquals("Durata non valida", SessionManager.getError(session));
    }

    @Test
    void testBadFormatDurata() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn("LUNEDI");
        when(req.getParameter("oraInizio")).thenReturn("15:00:00");
        when(req.getParameter("durata")).thenReturn("2h");
        servlet.doPost(req, res);
        assertEquals("Durata non valida", SessionManager.getError(session));
    }

    @Test
    void testOverlaps1() throws Exception {
        Utente studente = new Utente("m.rossi12@studenti.unisa.it", "Mario", "Rossi",
                PassowrdEncrypter.criptaPassword("MarioRossi12"), TipoUtente.STUDENTE);
        LocalDate date = LocalDate.now();
        while (!date.getDayOfWeek().equals(DayOfWeek.of(1))) {
            date = date.plusDays(1);
        }
        Clock fixedClock = Clock.fixed(date.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();
        when(ctx.getAttribute(PrenotaAulaServlet.CLOCK)).thenReturn(fixedClock);

        Prenotazione p = new Prenotazione(1, Date.valueOf(date), Time.valueOf("14:00:00"), Time.valueOf("16:00:00"),
                TipoPrenotazione.POSTO, aulaDAO.retriveById(1), studente);
        prenotazioneDAO.insert(p);

        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn("LUNEDI");
        when(req.getParameter("oraInizio")).thenReturn("15:00:00");
        when(req.getParameter("durata")).thenReturn("2");
        servlet.doPost(req, res);

        p = prenotazioneDAO.retriveById(1);
        assertEquals(p.getOraFine(), prenotazioneDAO.retriveById(0).getOraInizio());
    }

    @Test
    void testOverlaps2() throws Exception {
        Utente studente = new Utente("m.rossi12@studenti.unisa.it", "Mario", "Rossi",
                PassowrdEncrypter.criptaPassword("MarioRossi12"), TipoUtente.STUDENTE);

        LocalDate date = LocalDate.now();
        while (!date.getDayOfWeek().equals(DayOfWeek.of(1))) {
            date = date.plusDays(1);
        }
        Clock fixedClock = Clock.fixed(date.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();
        when(ctx.getAttribute(PrenotaAulaServlet.CLOCK)).thenReturn(fixedClock);

        Prenotazione p = new Prenotazione(1, Date.valueOf(date), Time.valueOf("16:00:00"), Time.valueOf("17:00:00"),
                TipoPrenotazione.POSTO, aulaDAO.retriveById(1), studente);
        prenotazioneDAO.insert(p);

        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn("LUNEDI");
        when(req.getParameter("oraInizio")).thenReturn("15:00:00");
        when(req.getParameter("durata")).thenReturn("2");
        servlet.doPost(req, res);

        p = prenotazioneDAO.retriveById(1);
        assertNull(p);
    }

    @Test
    void testOverlaps3() throws Exception {
        Utente studente = new Utente("m.rossi12@studenti.unisa.it", "Mario", "Rossi",
                PassowrdEncrypter.criptaPassword("MarioRossi12"), TipoUtente.STUDENTE);

        LocalDate date = LocalDate.now();
        while (!date.getDayOfWeek().equals(DayOfWeek.of(1))) {
            date = date.plusDays(1);
        }
        Clock fixedClock = Clock.fixed(date.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();
        when(ctx.getAttribute(PrenotaAulaServlet.CLOCK)).thenReturn(fixedClock);

        Prenotazione p = new Prenotazione(1, Date.valueOf(date), Time.valueOf("14:00:00"), Time.valueOf("18:00:00"),
                TipoPrenotazione.POSTO, aulaDAO.retriveById(1), studente);
        prenotazioneDAO.insert(p);

        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn("LUNEDI");
        when(req.getParameter("oraInizio")).thenReturn("15:00:00");
        when(req.getParameter("durata")).thenReturn("2");
        servlet.doPost(req, res);

        p = prenotazioneDAO.retriveById(1);
        assertEquals(prenotazioneDAO.retriveById(0).getOraInizio(), p.getOraFine());
    }

    @Test
    void testOverlaps4() throws Exception {
        Utente studente = new Utente("m.rossi12@studenti.unisa.it", "Mario", "Rossi",
                PassowrdEncrypter.criptaPassword("MarioRossi12"), TipoUtente.STUDENTE);

        LocalDate date = LocalDate.now();
        while (!date.getDayOfWeek().equals(DayOfWeek.of(1))) {
            date = date.plusDays(1);
        }
        Clock fixedClock = Clock.fixed(date.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();
        when(ctx.getAttribute(PrenotaAulaServlet.CLOCK)).thenReturn(fixedClock);

        Prenotazione p = new Prenotazione(1, Date.valueOf(date), Time.valueOf("13:00:00"), Time.valueOf("14:00:00"),
                TipoPrenotazione.POSTO, aulaDAO.retriveById(1), studente);
        prenotazioneDAO.insert(p);

        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("aula")).thenReturn("1");
        when(req.getParameter("data")).thenReturn("LUNEDI");
        when(req.getParameter("oraInizio")).thenReturn("15:00:00");
        when(req.getParameter("durata")).thenReturn("2");
        servlet.doPost(req, res);

        p = prenotazioneDAO.retriveById(1);
        assertEquals(Time.valueOf("13:00:00"), p.getOraInizio());
        assertEquals(Time.valueOf("14:00:00"), p.getOraFine());
    }
}