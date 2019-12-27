package control.admin;

import model.dao.AulaDAO;
import model.dao.EdificioDAO;
import model.database.StubAulaDAO;
import model.database.StubEdificioDAO;
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
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class ModificaAulaServletBlackBoxTest {

    @Mock
    private HttpServletRequest req;
    @Mock private HttpServletResponse res;
    @Mock private ServletContext ctx;
    @Mock private HttpSession session;
    private AulaDAO aulaDAO = new StubAulaDAO();
    private EdificioDAO edificioDAO = new StubEdificioDAO();
    private InserisciAulaServlet servlet;
    private Map<String,Object> attributes = new HashMap<String,Object>();

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        servlet = new InserisciAulaServlet();
        when(req.getServletContext()).thenReturn(ctx);
        when(ctx.getAttribute(InserisciAulaServlet.AULA_DAO_PARAM)).thenReturn(aulaDAO);
        when(ctx.getAttribute(InserisciAulaServlet.EDIFICIO_DAO_PARAM)).thenReturn(edificioDAO);
        when(req.getSession()).thenReturn(session);
        when(ctx.getContextPath()).thenReturn("");
        when(session.isNew()).thenReturn(false);
        doNothing().when(res).getWriter().print(anyString());


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
        when(req.getParameter("edificio")).thenReturn(null);
        when(req.getParameter("nome_aula")).thenReturn("P22");
        when(req.getParameter("numero_posti")).thenReturn("150");
        when(req.getParameter("disp_aula")).thenReturn("Lunedì:11:00-16:00");
        when(req.getParameter("servizi_extra_prese")).thenReturn("PRESE");
        servlet.doPost(req, res);
        assertEquals("Edificio non selezionato",
                req.getParameter("erroreInserimentoAula"));
    }

    @Test
    void TC_5_2() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("nome_aula")).thenReturn("P22");
        when(req.getParameter("numero_posti")).thenReturn("15");
        when(req.getParameter("disp_aula")).thenReturn("Lunedì:11:00-16:00");
        when(req.getParameter("servizi_extra_prese")).thenReturn("PRESE");
        servlet.doPost(req, res);
        assertEquals("Numero posti non corretto",
                req.getParameter("erroreInserimentoAula"));
    }

    @Test
    void TC_5_3() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("nome_aula")).thenReturn("P22");
        when(req.getParameter("numero_posti")).thenReturn("900");
        when(req.getParameter("disp_aula")).thenReturn("Lunedì:11:00-16:00");
        when(req.getParameter("servizi_extra_prese")).thenReturn("PRESE");
        servlet.doPost(req, res);
        assertEquals("Numero posti non corretto",
                req.getParameter("erroreInserimentoAula"));
    }

    @Test
    void TC_5_4() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("nome_aula")).thenReturn("P22");
        when(req.getParameter("numero_posti")).thenReturn("70 posti circa");
        when(req.getParameter("disp_aula")).thenReturn("Lunedì:11:00-16:00");
        when(req.getParameter("servizi_extra_prese")).thenReturn("PRESE");
        servlet.doPost(req, res);
        assertEquals("Formato numero posti non valido",
                req.getParameter("erroreInserimentoAula"));
    }

    @Test
    void TC_5_5() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("nome_aula")).thenReturn("P22");
        when(req.getParameter("numero_posti")).thenReturn("150");
        when(req.getParameter("disp_aula")).thenReturn("Lunedì:11:00-16:00");
        when(req.getParameter("servizi_extra_prese")).thenReturn("Ciambelle gratis!");
        when(req.getParameter("servizi_extra_computer")).thenReturn("COMPUTER");
        servlet.doPost(req, res);
        assertEquals("Servizi non validi",
                req.getParameter("erroreInserimentoAula"));
    }

    @Test
    void TC_5_6() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("nome_aula")).thenReturn("P22");
        when(req.getParameter("numero_posti")).thenReturn("150");
        when(req.getParameter("disp_aula")).thenReturn(null);
        when(req.getParameter("servizi_extra_prese")).thenReturn("PRESE");
        when(req.getParameter("servizi_extra_computer")).thenReturn("COMPUTER");
        servlet.doPost(req, res);
        assertEquals("Orari di disponibilità errati",
                req.getParameter("erroreInserimentoAula"));
    }

    @Test
    void TC_5_7() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("nome_aula")).thenReturn(null);
        when(req.getParameter("numero_posti")).thenReturn("150");
        when(req.getParameter("disp_aula")).thenReturn("Lunedì:11:00-16:00");
        when(req.getParameter("servizi_extra_prese")).thenReturn("PRESE");
        when(req.getParameter("servizi_extra_computer")).thenReturn("COMPUTER");
        servlet.doPost(req, res);
        assertEquals("Nome aula non valido",
                req.getParameter("erroreInserimentoAula"));
    }

    @Test
    void TC_5_8() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("nome_aula")).thenReturn("ppppppppppppppppppp11155555");
        when(req.getParameter("numero_posti")).thenReturn("150");
        when(req.getParameter("disp_aula")).thenReturn("Lunedì:11:00-16:00");
        when(req.getParameter("servizi_extra_prese")).thenReturn("PRESE");
        when(req.getParameter("servizi_extra_computer")).thenReturn("COMPUTER");
        servlet.doPost(req, res);
        assertEquals("Nome aula non valido",
                req.getParameter("erroreInserimentoAula"));
    }

    @Test
    void TC_5_9() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("nome_aula")).thenReturn("P22@12");
        when(req.getParameter("numero_posti")).thenReturn("150");
        when(req.getParameter("disp_aula")).thenReturn("Lunedì:11:00-16:00");
        when(req.getParameter("servizi_extra_prese")).thenReturn("PRESE");
        when(req.getParameter("servizi_extra_computer")).thenReturn("COMPUTER");
        servlet.doPost(req, res);
        assertEquals("Nome aula non rispetta il formato",
                req.getParameter("erroreInserimentoAula"));
    }

    @Test
    void TC_5_10() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("nome_aula")).thenReturn("P11");
        when(req.getParameter("numero_posti")).thenReturn("150");
        when(req.getParameter("disp_aula")).thenReturn("Lunedì:11:00-16:00");
        when(req.getParameter("servizi_extra_prese")).thenReturn("PRESE");
        when(req.getParameter("servizi_extra_computer")).thenReturn("COMPUTER");
        servlet.doPost(req, res);
        assertEquals("Aula già esistente!",
                req.getParameter("erroreInserimentoAula"));
    }

    @Test
    void TC_5_11() throws Exception {
        when(req.getParameter("edificio")).thenReturn("F3");
        when(req.getParameter("nome_aula")).thenReturn("P22");
        when(req.getParameter("numero_posti")).thenReturn("150");
        when(req.getParameter("disp_aula")).thenReturn("Lunedì:11:00-16:00");
        when(req.getParameter("servizi_extra_prese")).thenReturn("PRESE");
        when(req.getParameter("servizi_extra_computer")).thenReturn("COMPUTER");
        servlet.doPost(req, res);
        assertEquals("P22", aulaDAO.retriveByName("P22").getNome());
    }
}