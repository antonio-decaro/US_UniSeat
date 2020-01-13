package control.comuni;

import com.google.gson.Gson;
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
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

class PrelevaUtentiServletTest {

    @Mock private HttpServletRequest req;
    @Mock private HttpServletResponse resp;
    @Mock private ServletContext ctx;
    @Mock private HttpSession session;
    private UtenteDAO utenteDAO = new StubUtenteDAO();
    private HashMap<String, Object> attributes = new HashMap<>();
    private PrelevaUtentiServlet servlet = new PrelevaUtentiServlet();
    private StringWriter stringWriter;
    private String stringError;
    private int codeError;

    @BeforeEach
    void setUp() throws IOException {
        stringWriter = new StringWriter();
        MockitoAnnotations.initMocks(this);
        when(req.getSession()).thenReturn(session);
        when(resp.getWriter()).thenReturn(new PrintWriter(stringWriter));
        when(req.getServletContext()).thenReturn(ctx);
        when(ctx.getAttribute(PrelevaUtentiServlet.UTENTE_DAO_PARAM)).thenReturn(utenteDAO);

        Mockito.doAnswer((Answer<Object>) invocation -> {
            codeError = (Integer) invocation.getArguments()[0];
            stringError = (String) invocation.getArguments()[1];
            return null;
        }).when(resp).sendError(anyInt(), anyString());

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
    void testNoUtente() throws Exception {
        servlet.doPost(req, resp);
        assertEquals(403, codeError);
        assertEquals("Non hai i permessi per accedere a questa risorsa", stringError);
    }

    @Test
    void testNoAdmin() throws Exception {
        SessionManager.autentica(session, new Utente("noa.admin@unisa.it", "NoAdmin", "NoAdmin",
                "NoAdmin", TipoUtente.DOCENTE));
        servlet.doPost(req, resp);
        assertEquals(403, codeError);
        assertEquals("Non hai i permessi per accedere a questa risorsa", stringError);
    }

    @Test
    void testWithUsers() throws Exception {
        SessionManager.autentica(session, new Utente("a.admin@unisa.it", "Admin", "Admin",
                "Admin", TipoUtente.ADMIN));
        List<Utente> utenti = new LinkedList<>();
        utenti.add(new Utente("a.decaro36@studenti.unisa.it", "Antonio", "De Caro",
                "Passoword1",  TipoUtente.STUDENTE));
        utenti.add(new Utente("g.spinelli1@studenti.unisa.it", "Gianluca", "Spinelli",
                "Passoword2",  TipoUtente.STUDENTE));
        utenti.add(new Utente("c.gravino@unisa.it", "Carmine", "Gravino",
                "Passoword3",  TipoUtente.DOCENTE));
        for (Utente u : utenti)
            utenteDAO.insert(u);

        Gson gson = new Gson();
        servlet.doPost(req, resp);
        assertEquals(gson.toJson(utenti), stringWriter.toString());
    }
}