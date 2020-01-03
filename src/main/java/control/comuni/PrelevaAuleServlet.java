package control.comuni;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import model.dao.AulaDAO;
import model.database.DBAulaDAO;
import model.pojo.Aula;
import model.pojo.Edificio;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String edif= request.getParameter("nome_edificio"); //richiesta parametro in base al quale effettuare la ricerca delle aule
        Edificio edificio = new Edificio (edif);
        String address = "/comuni/aule.jsp";

        AulaDAO aulaDAO = (AulaDAO) getServletContext().getAttribute(AULA_DAO_PARAM);
        Set<Aula> aule ;
        aule= aulaDAO.retriveByEdificio(edificio);

        response.sendRedirect(request.getServletContext().getContextPath() + address);


    }

    public static final String AULA_DAO_PARAM = "PrelevaAuleServlet.AulaDAO";
}
