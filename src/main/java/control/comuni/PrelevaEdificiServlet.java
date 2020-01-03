package control.comuni;


import java.io.IOException;
import java.util.List;

import model.dao.EdificioDAO;
import model.database.DBEdificioDAO;
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

    public PrelevaEdificiServlet() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // TODO Auto-generated method stub
        doPost(request,response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {


        String address = "/comuni/edifici.jsp";

        EdificioDAO edificioDAO = (EdificioDAO) getServletContext().getAttribute(EDIFICI_DAO_PARAM);
        List<Edificio> edifici ;
        edifici= edificioDAO.retriveAll();

        response.sendRedirect(request.getServletContext().getContextPath() + address);


    }

    public static final String EDIFICI_DAO_PARAM = "PrelevaEdificiServlet.EdificioDAO";

}
