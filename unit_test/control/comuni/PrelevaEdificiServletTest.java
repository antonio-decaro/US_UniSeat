package control.comuni;

import com.google.gson.Gson;
import model.dao.EdificioDAO;
import model.daostub.StubEdificioDAO;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

class PrelevaEdificiServletTest {

    @Mock
    private HttpServletRequest req;
    @Mock private HttpServletResponse resp;
    @Mock private ServletContext ctx;
    @Mock private HttpSession session;
    private EdificioDAO edificioDAO = new StubEdificioDAO();
    private HashMap<String, Object> attributes = new HashMap<>();
    private control.comuni.PrelevaEdificiServlet servlet = new control.comuni.PrelevaEdificiServlet();
    private StringWriter stringWriter;

    @BeforeEach
    void setUp() throws IOException {
        stringWriter = new StringWriter();
        MockitoAnnotations.initMocks(this);
        when(req.getSession()).thenReturn(session);
        when(resp.getWriter()).thenReturn(new PrintWriter(stringWriter));
        when(req.getServletContext()).thenReturn(ctx);
        when(ctx.getAttribute(control.comuni.PrelevaEdificiServlet.EDIFICI_DAO_PARAM)).thenReturn(edificioDAO);

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
    void testDoPost() throws Exception {
        List<Edificio> edifici = edificioDAO.retriveAll();

        Gson gson = new Gson();
        servlet.doPost(req, resp);
        assertEquals(gson.toJson(edifici), stringWriter.toString());
    }
}