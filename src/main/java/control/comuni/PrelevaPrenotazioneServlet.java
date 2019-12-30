package control.comuni;

import control.utili.SessionManager;
import model.dao.PrenotazioneDAO;
import model.database.DBPrenotazioneDAO;
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
import java.util.Calendar;
import java.util.Iterator;
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
        getServletContext().setAttribute(PRENOTAZIONE_DAO_PARAM, DBPrenotazioneDAO.getInstance());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession ssn = request.getSession();
        Utente user = SessionManager.getUtente(ssn);
        String addres = "/comuni/prenotazioni.jsp";

        if (user == null ) {
            SessionManager.setError(ssn, "LogIn non effettuato");
            response.sendRedirect(request.getServletContext().getContextPath() + "/comuni/login.jsp");
            return;
        }
        try {
            PrenotazioneDAO prenotazioneDAO = (PrenotazioneDAO) request.getServletContext().getAttribute(PRENOTAZIONE_DAO_PARAM);
            Date date = new Date(Calendar.getInstance().getTime().getTime());
            String uem = user.getEmail();
            List<Prenotazione> prenotazioni;
            prenotazioni = prenotazioneDAO.retriveByData(date);
            for (Iterator<Prenotazione> i = prenotazioni.iterator(); i.hasNext();) {
                Prenotazione p = i.next();
                String e = p.getUtente().getEmail();
                if (e.equals(uem))
                    i.remove();
            }
            if (prenotazioni.isEmpty())
                request.setAttribute("prenotazione", null);
            else {
                if (user.getTipoUtente().toString().equals(TipoUtente.STUDENTE.toString())) {
                    request.setAttribute("prenotazione", prenotazioni.get(0));
                }

                if (user.getTipoUtente().toString().equals(TipoUtente.DOCENTE.toString())) {
                    request.setAttribute("prenotazioni", prenotazioni);
                }
                /*if(user.getTipoUtente().toString().equals(TipoUtente.ADMIN)){

                }*/
            }


        } catch (IllegalArgumentException e) {
            SessionManager.setError(ssn, e.getMessage());
        }
        response.sendRedirect(request.getServletContext().getContextPath() + addres);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    static final String PRENOTAZIONE_DAO_PARAM = "PrelevaPrenotazioneServlet.PrenotazioneDAO";
}
