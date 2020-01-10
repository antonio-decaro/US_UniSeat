package control.comuni;


import com.google.gson.Gson;
import model.dao.EdificioDAO;
import model.database.DBEdificioDAO;
import model.pojo.Edificio;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Questa servlet permette di prelevare tutti gli edifici dal sistema
 * @author Capozzoli Lorenzo
 * @version 0.1
 * @see model.pojo.Edificio
 * @see model.dao.EdificioDAO
 */
@WebServlet("/PrelevaEdificiServlet")
public class PrelevaEdificiServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        getServletContext().setAttribute(EDIFICI_DAO_PARAM, DBEdificioDAO.getInstance());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        EdificioDAO edificioDAO = (EdificioDAO) req.getServletContext().getAttribute(EDIFICI_DAO_PARAM);

        List<Edificio> edifici = edificioDAO.retriveAll();

        Gson gson = new Gson();
        try (PrintWriter pw = resp.getWriter()) {
            resp.setStatus(HttpServletResponse.SC_OK);
            pw.print(gson.toJson(edifici));
        }
    }

    static final String EDIFICI_DAO_PARAM = "PrelevaEdificiServlet.EdificioDAO";
}
