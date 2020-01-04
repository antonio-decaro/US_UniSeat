package control.comuni;

import com.google.gson.Gson;
import model.dao.AulaDAO;
import model.dao.EdificioDAO;
import model.database.DBAulaDAO;
import model.database.DBEdificioDAO;
import model.pojo.Aula;
import model.pojo.Edificio;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

/**
 * Questa servlet permette di prelevare tutte le aule di un determinato edificio dal sistema
 * @author Capozzoli Lorenzo
 * @version 0.1
 * @see model.pojo.Aula
 * @see model.dao.AulaDAO
 */
@WebServlet("/PrelevaAuleServlet")
public class PrelevaAuleServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        getServletContext().setAttribute(AULA_DAO_PARAM, DBAulaDAO.getInstance());
        getServletContext().setAttribute(EDIFICIO_DAO_PARAM, DBEdificioDAO.getInstance());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        AulaDAO aulaDAO = (AulaDAO) req.getServletContext().getAttribute(AULA_DAO_PARAM);
        EdificioDAO edificioDAO = (EdificioDAO) req.getServletContext().getAttribute(EDIFICIO_DAO_PARAM);

        Edificio edificio = parseEdificio(req.getParameter("edificio"), edificioDAO);
        Set<Aula> aule;
        if (edificio == null) {
            aule = aulaDAO.retriveAll();
        } else {
            aule = aulaDAO.retriveByEdificio(edificio);
        }

        for (Aula a : aule) {
            a.setEdificio(null);
        }

        Gson gson = new Gson();
        try (PrintWriter pw = resp.getWriter()) {
            resp.setStatus(HttpServletResponse.SC_OK);
            pw.print(gson.toJson(aule));
        }
    }

    private Edificio parseEdificio(String param, EdificioDAO edificioDAO) {
        if (param == null || param.strip().equals(""))
            return null;
        return edificioDAO.retriveByName(param);
    }

    static final String AULA_DAO_PARAM = "PrelevaAuleServlet.AulaDAO";
    static final String EDIFICIO_DAO_PARAM = "PrelevaAuleServlet.EdificioDAO";
}
