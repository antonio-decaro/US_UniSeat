package control.admin;

import control.utili.SessionManager;
import model.dao.AulaDAO;
import model.dao.EdificioDAO;
import model.daostub.StubAulaDAO;
import model.daostub.StubEdificioDAO;
import model.pojo.Aula;
import model.pojo.Edificio;
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

class ModificaAulaServletTest {

    @Mock
    private HttpServletRequest req;
    @Mock private HttpServletResponse res;
    @Mock private ServletContext ctx;
    @Mock private HttpSession session;
    private AulaDAO aulaDAO = new StubAulaDAO();
    private EdificioDAO edificioDAO = new StubEdificioDAO();
    private ModificaAulaServlet servlet;
    private Map<String,Object> attributes = new HashMap<String,Object>();
    private SessionManager sm = new SessionManager();

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        servlet = new ModificaAulaServlet();

        when(req.getServletContext()).thenReturn(ctx);
        when(ctx.getAttribute(ModificaAulaServlet.AULA_DAO_PARAM)).thenReturn(aulaDAO);
        when(ctx.getAttribute(ModificaAulaServlet.EDIFICIO_DAO_PARAM)).thenReturn(edificioDAO);
        when(req.getSession()).thenReturn(session);
        when(ctx.getContextPath()).thenReturn("");

        doNothing().when(res).sendRedirect(anyString());
        StringWriter stringWriter = new StringWriter();
        when(res.getWriter()).thenReturn(new PrintWriter(stringWriter));


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
        session.setAttribute("SessionManager.user", u);

        Edificio ed = edificioDAO.retriveByName("F3");
        String dispP3 = "11:00-15:00";
        String dispP4 = "11:00-15:00";
        Aula aulaP3 = new Aula("P3", 70, 100, dispP3, ed);
        Aula aulaP4 = new Aula("P4", 0, 100, dispP4, ed);
        aulaP3.setId(1);
        aulaP4.setId(2);
        aulaDAO.insert(aulaP3);
        aulaDAO.insert(aulaP4);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void TC_5_1() throws Exception {
        when(req.getParameter("edificio")).thenReturn(null);
        when(req.getParameter("nome_aula")).thenReturn("P3");
        when(req.getParameter("numero_posti")).thenReturn("150");
        when(req.getParameter("disp_aula")).thenReturn("Lunedì:11:00-16:00");
        when(req.getParameter("servizi_extra_prese")).thenReturn("PRESE");
        servlet.doPost(req, res);
        assertEquals("Edificio non selezionato",
                session.getAttribute("SessionManager.error"));
    }

    @Test
    void TC_5_2() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("nome_aula")).thenReturn("P3");
        when(req.getParameter("numero_posti")).thenReturn("70 posti circa");
        when(req.getParameter("disp_aula")).thenReturn("Lunedì:11:00-16:00");
        when(req.getParameter("servizi_extra_prese")).thenReturn("PRESE");
        servlet.doPost(req, res);
        assertEquals("Formato numero posti non valido",
                session.getAttribute("SessionManager.error"));
    }

    @Test
    void TC_5_3() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("nome_aula")).thenReturn("P3");
        when(req.getParameter("numero_posti")).thenReturn("15");
        when(req.getParameter("disp_aula")).thenReturn("Lunedì:11:00-16:00");
        when(req.getParameter("servizi_extra_prese")).thenReturn("PRESE");
        servlet.doPost(req, res);
        assertEquals("Numero posti non corretto",
                session.getAttribute("SessionManager.error"));
    }

    @Test
    void TC_5_4() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("nome_aula")).thenReturn("P3");
        when(req.getParameter("numero_posti")).thenReturn("900");
        when(req.getParameter("disp_aula")).thenReturn("Lunedì:11:00-16:00");
        when(req.getParameter("servizi_extra_prese")).thenReturn("PRESE");
        servlet.doPost(req, res);
        assertEquals("Numero posti non corretto",
                session.getAttribute("SessionManager.error"));
    }

    @Test
    void TC_5_5() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F4");
        when(req.getParameter("nome_aula")).thenReturn("P3");
        when(req.getParameter("numero_posti")).thenReturn("100");
        when(req.getParameter("disp_aula")).thenReturn("Lunedì:11:00-16:00");
        when(req.getParameter("servizi_extra_prese")).thenReturn("PRESE");
        servlet.doPost(req, res);
        assertEquals("Edificio non trovato",
                session.getAttribute("SessionManager.error"));
    }

    @Test
    void TC_5_6() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("nome_aula")).thenReturn("P3");
        when(req.getParameter("numero_posti")).thenReturn("150");
        when(req.getParameter("disp_aula")).thenReturn("Lunedì:11:00-16:00");
        when(req.getParameter("servizi_extra_prese")).thenReturn("Ciambelle gratis!");
        when(req.getParameter("servizi_extra_computer")).thenReturn("COMPUTER");
        servlet.doPost(req, res);
        assertEquals("Servizi non validi",
                session.getAttribute("SessionManager.error"));
    }

    @Test
    void TC_5_7() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("nome_aula")).thenReturn("P3");
        when(req.getParameter("numero_posti")).thenReturn("150");
        when(req.getParameter("disp_aula")).thenReturn(null);
        when(req.getParameter("servizi_extra_prese")).thenReturn("PRESE");
        when(req.getParameter("servizi_extra_computer")).thenReturn("COMPUTER");
        servlet.doPost(req, res);
        assertEquals("Orari di disponibilità errati",
                session.getAttribute("SessionManager.error"));
    }

    @Test
    void TC_5_8() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("nome_aula")).thenReturn("");
        when(req.getParameter("numero_posti")).thenReturn("150");
        when(req.getParameter("disp_aula")).thenReturn("Lunedì:11:00-16:00");
        when(req.getParameter("servizi_extra_prese")).thenReturn("PRESE");
        when(req.getParameter("servizi_extra_computer")).thenReturn("COMPUTER");
        servlet.doPost(req, res);
        assertEquals("Nome aula non valido",
                session.getAttribute("SessionManager.error"));
    }

    @Test
    void TC_5_9() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("nome_aula")).thenReturn("ppppppppppppppppppp11155555");
        when(req.getParameter("numero_posti")).thenReturn("150");
        when(req.getParameter("disp_aula")).thenReturn("Lunedì:11:00-16:00");
        when(req.getParameter("servizi_extra_prese")).thenReturn("PRESE");
        when(req.getParameter("servizi_extra_computer")).thenReturn("COMPUTER");
        servlet.doPost(req, res);
        assertEquals("Nome aula non valido",
                session.getAttribute("SessionManager.error"));
    }

    @Test
    void TC_5_10() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("nome_aula")).thenReturn("P22@12");
        when(req.getParameter("numero_posti")).thenReturn("150");
        when(req.getParameter("disp_aula")).thenReturn("Lunedì:11:00-16:00");
        when(req.getParameter("servizi_extra_prese")).thenReturn("PRESE");
        when(req.getParameter("servizi_extra_computer")).thenReturn("COMPUTER");
        servlet.doPost(req, res);
        assertEquals("Nome aula non rispetta il formato",
                session.getAttribute("SessionManager.error"));
    }

    @Test
    void TC_5_11() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("nome_aula")).thenReturn("P45");
        when(req.getParameter("numero_posti")).thenReturn("150");
        when(req.getParameter("disp_aula")).thenReturn("Lunedì:11:00-16:00");
        when(req.getParameter("servizi_extra_prese")).thenReturn("PRESE");
        when(req.getParameter("servizi_extra_computer")).thenReturn("COMPUTER");
        servlet.doPost(req, res);
        assertEquals("Aula non esistente",
                session.getAttribute("SessionManager.error"));
    }

    @Test
    void TC_5_12() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("nome_aula")).thenReturn("P4");
        when(req.getParameter("numero_posti")).thenReturn("160");
        when(req.getParameter("disp_aula")).thenReturn("Lunedì:11:00-16:00");
        when(req.getParameter("servizi_extra_prese")).thenReturn("PRESE");
        when(req.getParameter("servizi_extra_computer")).thenReturn("COMPUTER");
        servlet.doPost(req, res);
        assertEquals("P4", aulaDAO.retriveByName("P4").getNome());
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
        session.setAttribute("SessionManager.user", u);
        servlet.doPost(req, res);
    }

    @Test
    void TC_5_13() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("nome_aula")).thenReturn("P4");
        when(req.getParameter("numero_posti")).thenReturn("150");
        when(req.getParameter("disp_aula")).thenReturn("Lunedì:11:00-16:00");
        when(req.getParameter("servizi_extra_prese")).thenReturn("PRESE");
        when(req.getParameter("servizi_extra_computer")).thenReturn("COMPUTER");
        servlet.doGet(req, res);
        assertEquals("P4", aulaDAO.retriveByName("P4").getNome());
    }
}