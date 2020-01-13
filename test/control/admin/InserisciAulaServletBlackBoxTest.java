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



/**
 * Classe per testare la servlet InserisciAula con la tecnica del category partition
 * @author Spinelli Gianluca
 */

class InserisciAulaServletBlackBoxTest {

    private static String dispAula = "{\"intervalli\": [" +
            "[[\"10:30\", \"11:30\"]]," +
            "[]," +
            "[]," +
            "[]," +
            "[]" +
            "]}";

    @Mock private HttpServletRequest req;
    @Mock private HttpServletResponse res;
    @Mock private ServletContext ctx;
    @Mock private HttpSession session;
    private AulaDAO aulaDAO = new StubAulaDAO();
    private EdificioDAO edificioDAO = new StubEdificioDAO();
    private InserisciAulaServlet servlet;
    private Map<String,Object> attributes = new HashMap<>();

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        servlet = new InserisciAulaServlet();

        when(req.getServletContext()).thenReturn(ctx);
        when(ctx.getAttribute(InserisciAulaServlet.AULA_DAO_PARAM)).thenReturn(aulaDAO);
        when(ctx.getAttribute(InserisciAulaServlet.EDIFICIO_DAO_PARAM)).thenReturn(edificioDAO);
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
        SessionManager.autentica(session, u);

        Edificio ed = new StubEdificioDAO().retriveByName("F3");
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
        when(req.getParameter("edificio")).thenReturn("");
        when(req.getParameter("nome_aula")).thenReturn("P9");
        when(req.getParameter("numero_posti")).thenReturn("100");
        when(req.getParameter("disp_aula")).thenReturn(dispAula);
        when(req.getParameter("servizi_extra_prese")).thenReturn("COMPUTER");
        servlet.doPost(req, res);
        assertEquals("Edificio non selezionato",
                SessionManager.getError(session));
        assertNull(aulaDAO.retriveByName("P22"));
    }

    @Test
    void TC_5_2() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("nome_aula")).thenReturn("");
        when(req.getParameter("numero_posti")).thenReturn("100");
        when(req.getParameter("disp_aula")).thenReturn(dispAula);
        when(req.getParameter("servizi_extra_computer")).thenReturn("COMPUTER");
        servlet.doPost(req, res);
        assertEquals("Nome aula non valido",
                SessionManager.getError(session));
        assertNull(aulaDAO.retriveByName("P22"));
    }

    @Test
    void TC_5_3() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("nome_aula")).thenReturn("nomenonvalidoperaula");
        when(req.getParameter("numero_posti")).thenReturn("100");
        when(req.getParameter("disp_aula")).thenReturn(dispAula);
        when(req.getParameter("servizi_extra_computer")).thenReturn("COMPUTER");
        servlet.doPost(req, res);
        assertEquals("Nome aula non valido",
                SessionManager.getError(session));
        assertNull(aulaDAO.retriveByName("P22"));
    }

    @Test
    void TC_5_4() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("nome_aula")).thenReturn("P@9");
        when(req.getParameter("numero_posti")).thenReturn("100");
        when(req.getParameter("disp_aula")).thenReturn(dispAula);
        when(req.getParameter("servizi_extra_computer")).thenReturn("COMPUTER");
        servlet.doPost(req, res);
        assertEquals("Nome aula non rispetta il formato",
                SessionManager.getError(session));
        assertNull(aulaDAO.retriveByName("P22"));
    }

    @Test
    void TC_5_5() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("nome_aula")).thenReturn("P3");
        when(req.getParameter("numero_posti")).thenReturn("100");
        when(req.getParameter("disp_aula")).thenReturn(dispAula);
        when(req.getParameter("servizi_extra_computer")).thenReturn("COMPUTER");
        servlet.doPost(req, res);
        assertEquals("Aula già esistente!",
                SessionManager.getError(session));
    }

    @Test
    void TC_5_6() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("nome_aula")).thenReturn("P9");
        when(req.getParameter("numero_posti")).thenReturn("20 posti");
        when(req.getParameter("disp_aula")).thenReturn(dispAula);
        when(req.getParameter("servizi_extra_computer")).thenReturn("COMPUTER");
        servlet.doPost(req, res);
        assertEquals("Formato numero posti non valido",
                SessionManager.getError(session));
        assertNull(aulaDAO.retriveByName("P22"));
    }

    @Test
    void TC_5_7() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("nome_aula")).thenReturn("P9");
        when(req.getParameter("numero_posti")).thenReturn("10");
        when(req.getParameter("disp_aula")).thenReturn(dispAula);
        when(req.getParameter("servizi_extra_computer")).thenReturn("COMPUTER");
        servlet.doPost(req, res);
        assertEquals("Numero posti non corretto",
                SessionManager.getError(session));
        assertNull(aulaDAO.retriveByName("P22"));
    }

    @Test
    void TC_5_8() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("nome_aula")).thenReturn("P9");
        when(req.getParameter("numero_posti")).thenReturn("1000");
        when(req.getParameter("disp_aula")).thenReturn(dispAula);
        when(req.getParameter("servizi_extra_computer")).thenReturn("COMPUTER");
        servlet.doPost(req, res);
        assertEquals("Numero posti non corretto",
                SessionManager.getError(session));
        assertNull(aulaDAO.retriveByName("P22"));
    }

    @Test
    void TC_5_9() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("nome_aula")).thenReturn("P9");
        when(req.getParameter("numero_posti")).thenReturn("100");
        when(req.getParameter("disp_aula")).thenReturn(dispAula);
        when(req.getParameter("servizi_extra_prese")).thenReturn("CIAMBELLE GRATIS");
        servlet.doPost(req, res);
        assertEquals("Servizi non validi",
                SessionManager.getError(session));
        assertNull(aulaDAO.retriveByName("P22"));
    }

    @Test
    void TC_5_10() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("nome_aula")).thenReturn("P9");
        when(req.getParameter("numero_posti")).thenReturn("100");
        when(req.getParameter("disp_aula")).thenReturn(null);
        when(req.getParameter("servizi_extra_computer")).thenReturn("COMPUTER");
        servlet.doPost(req, res);
        assertEquals("Orari di disponibilità errati",
                SessionManager.getError(session));
        assertNull(aulaDAO.retriveByName("P22"));
    }



    @Test
    void TC_5_11() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("nome_aula")).thenReturn("P9");
        when(req.getParameter("numero_posti")).thenReturn("100");
        when(req.getParameter("disp_aula")).thenReturn(dispAula);
        when(req.getParameter("servizi_extra_computer")).thenReturn("COMPUTER");
        servlet.doPost(req, res);
        assertEquals("P9",aulaDAO.retriveByName("P9").getNome());
    }


}