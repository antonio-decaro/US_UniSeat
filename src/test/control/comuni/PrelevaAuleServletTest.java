package control.comuni;

import com.google.gson.Gson;
import model.dao.AulaDAO;
import model.dao.EdificioDAO;
import model.daostub.StubAulaDAO;
import model.daostub.StubEdificioDAO;
import model.pojo.Aula;
import model.pojo.Edificio;
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
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

class PrelevaAuleServletTest {
    @Mock private HttpServletRequest req;
    @Mock private HttpServletResponse resp;
    @Mock private ServletContext ctx;
    @Mock private HttpSession session;
    private EdificioDAO edificioDAO = new StubEdificioDAO();
    private AulaDAO aulaDAO = new StubAulaDAO();
    private HashMap<String, Object> attributes = new HashMap<>();
    private PrelevaAuleServlet servlet = new PrelevaAuleServlet();
    private StringWriter stringWriter;

    @BeforeEach
    void setUp() throws IOException {
        stringWriter = new StringWriter();
        MockitoAnnotations.initMocks(this);
        when(req.getSession()).thenReturn(session);
        when(resp.getWriter()).thenReturn(new PrintWriter(stringWriter));
        when(req.getServletContext()).thenReturn(ctx);
        when(ctx.getAttribute(PrelevaAuleServlet.EDIFICIO_DAO_PARAM)).thenReturn(edificioDAO);
        when(ctx.getAttribute(PrelevaAuleServlet.AULA_DAO_PARAM)).thenReturn(aulaDAO);

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

    @Test
    void testNoEdificio() throws Exception {
        Edificio f3 = edificioDAO.retriveByName("F3");
        Edificio f2 = edificioDAO.retriveByName("F2");
        Aula p3 = new Aula("P3", 100, "", f3);
        p3.setId(1);
        Aula p4 = new Aula("P4", 120, "", f3);
        p4.setId(2);
        Aula f8 = new Aula("F8", 120, "", f2);
        f8.setId(3);
        Aula laboratorio = new Aula("Laboratorio Hopper", 80, "", f2);
        laboratorio.setId(4);
        f3.getAule().add(p3);
        f3.getAule().add(p4);
        f2.getAule().add(f8);
        f2.getAule().add(laboratorio);
        Set<Aula> aule = new HashSet<>();
        aule.addAll(f3.getAule());
        aule.addAll(f2.getAule());
        aulaDAO.insert(p3);
        aulaDAO.insert(p4);
        aulaDAO.insert(f8);
        aulaDAO.insert(laboratorio);

        when(req.getParameter("edificio")).thenReturn(null);

        Gson gson = new Gson();
        servlet.doPost(req, resp);
        assertEquals(gson.toJson(aule), stringWriter.toString());
    }

    @Test
    void testWithEdificio() throws Exception {
        Edificio f3 = edificioDAO.retriveByName("F3");
        Edificio f2 = edificioDAO.retriveByName("F2");
        Aula p3 = new Aula("P3", 100, "", f3);
        p3.setId(1);
        Aula p4 = new Aula("P4", 120, "", f3);
        p4.setId(2);
        Aula f8 = new Aula("F8", 120, "", f2);
        f8.setId(3);
        Aula laboratorio = new Aula("Laboratorio Hopper", 80, "", f2);
        laboratorio.setId(4);
        f3.getAule().add(p3);
        f3.getAule().add(p4);
        f2.getAule().add(f8);
        f2.getAule().add(laboratorio);
        Set<Aula> aule = new HashSet<>(f3.getAule());
        aulaDAO.insert(p3);
        aulaDAO.insert(p4);
        aulaDAO.insert(f8);
        aulaDAO.insert(laboratorio);

        when(req.getParameter("edificio")).thenReturn("F3");

        Gson gson = new Gson();
        servlet.doPost(req, resp);
        assertEquals(gson.toJson(aule), stringWriter.toString());
    }
}