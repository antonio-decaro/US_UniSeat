package control.comuni;

import control.utili.SessionManager;
import model.dao.AulaDAO;
import model.dao.PrenotazioneDAO;
import model.database.DBAulaDAO;
import model.database.DBPrenotazioneDAO;
import model.pojo.Aula;
import model.pojo.Prenotazione;
import model.pojo.TipoUtente;
import model.pojo.Utente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Questa servlet permette ad un utente di eliminare la sua prenotazione all''interno del database
 *
 * @author De Santis Marco
 * @version 0.1
 * @see model.pojo.Utente
 * @see model.dao.UtenteDAO
 * @see model.pojo.Aula
 * @see model.dao.AulaDAO
 */

@WebServlet("/eliminaPrenotazione")
public class EliminaPrenotazioneServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        getServletContext().setAttribute(PRENOTAZIONE_DAO_PARAM, DBPrenotazioneDAO.getInstance());
        getServletContext().setAttribute(AULA_DAO_PARAM, DBAulaDAO.getInstance());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession ssn = request.getSession();
        Utente user = SessionManager.getUtente(ssn);
        String addres = "/comuni/prenotazioni.jsp";

        if (user == null || ! SessionManager.isAlradyAuthenticated(ssn)) {
            SessionManager.setError(ssn, "LogIn non effettuato");
            response.sendRedirect(request.getServletContext().getContextPath() + "/comuni/login.jsp");
            return;
        }

        int id = Integer.parseInt(request.getParameter("id_prenotazione"));
        Prenotazione p;
        PrenotazioneDAO prenotazioneDAO = (PrenotazioneDAO) getServletContext().getAttribute(PRENOTAZIONE_DAO_PARAM);
        try {
            p = prenotazioneDAO.retriveById(id);
            if (p == null) {
                SessionManager.setError(ssn, "Prenotazione non presente nel DB");
                response.sendRedirect(request.getServletContext().getContextPath() + "/comuni/login.jsp");
                return;
            }
            prenotazioneDAO.delete(p);
            AulaDAO aulaDAO = (AulaDAO) getServletContext().getAttribute(AULA_DAO_PARAM);
            Aula a = aulaDAO.retriveById(p.getAula().getId());


            if (user.getTipoUtente().toString().equals(TipoUtente.STUDENTE.toString())) {
                a.setPostiOccupati(a.getPostiOccupati() - 1);
                aulaDAO.update(a);
            }

            if (user.getTipoUtente().toString().equals(TipoUtente.DOCENTE.toString())) {
                a.setPostiOccupati(0);
                aulaDAO.update(a);
            }

        /*if(user.getTipoUtente().toString().equals(TipoUtente.ADMIN)){

        }*/
        } catch (IllegalArgumentException e) {
            SessionManager.setError(ssn, e.getMessage());
        }
        response.sendRedirect(request.getServletContext().getContextPath() + addres);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    public static final String PRENOTAZIONE_DAO_PARAM = "EliminaPrenotazioneServlet.PrenotazioneDAO";
    public static final String AULA_DAO_PARAM = "EliminaPrenotazioneServlet.AulaDAO";

}
