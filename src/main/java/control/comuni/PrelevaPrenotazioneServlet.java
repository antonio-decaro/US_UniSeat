package control.comuni;

import control.utili.SessionManager;
import model.dao.AulaDAO;
import model.dao.PrenotazioneDAO;
import model.dao.UtenteDAO;
import model.database.DBAulaDAO;
import model.database.DBPrenotazioneDAO;
import model.database.DBUtenteDAO;
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
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Questa servlet permette di prelevare una prenotazione presente nel database
 *
 * @author De Santis Marco
 * @version 0.1
 * @see model.pojo.Utente
 * @see model.dao.UtenteDAO
 * @see model.pojo.Aula
 * @see model.dao.AulaDAO
 */

@WebServlet("/prelevaPrenotazione")
public class PrelevaPrenotazioneServlet extends HttpServlet {

    public void init() throws ServletException {
        super.init();
        getServletContext().setAttribute(UTENTE_DAO_PARAM, DBUtenteDAO.getInstance());
        getServletContext().setAttribute(PRENOTAZIONE_DAO_PARAM, DBPrenotazioneDAO.getInstance());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession ssn = request.getSession();
        Utente user = SessionManager.getUtente(ssn);
        String addres = "/comuni/prenotazioni.jsp";

        if (user == null || SessionManager.isAlradyAuthenticated(ssn)) {
            SessionManager.setError(ssn, "LogIn non effettuato");
            response.sendRedirect(request.getServletContext().getContextPath() + "/comuni/login.jsp");
            return;
        }

        PrenotazioneDAO prenotazioneDAO = (PrenotazioneDAO) getServletContext().getAttribute(PRENOTAZIONE_DAO_PARAM);
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        List<Prenotazione> prenotazioni ;
        prenotazioni= prenotazioneDAO.retriveByData(date);
        try {
            if (user.getTipoUtente().toString().equals(TipoUtente.STUDENTE)) {
                if(prenotazioni != null)
                    request.setAttribute("prenotazione",prenotazioni.get(0));
                else
                    request.setAttribute("prenotazione",null);
            }

            if (user.getTipoUtente().toString().equals(TipoUtente.DOCENTE)) {
                if (prenotazioni != null)
                    request.setAttribute("prenotazioni",prenotazioni);
                else
                    request.setAttribute("prenotazione",null);
            }

        /*if(user.getTipoUtente().toString().equals(TipoUtente.ADMIN)){

        }*/
        } catch (IllegalArgumentException e) {
            SessionManager.setError(ssn, e.getMessage());
        }
        response.sendRedirect(request.getServletContext().getContextPath() + addres);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public static final String UTENTE_DAO_PARAM = "EliminaPrenotazioneServlet.UtenteDAO";
    public static final String PRENOTAZIONE_DAO_PARAM = "EliminaPrenotazioneServlet.PrenotazioneDAO";
}
