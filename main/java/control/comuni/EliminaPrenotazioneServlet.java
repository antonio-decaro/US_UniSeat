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
import java.sql.Date;
import java.sql.Time;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;

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
        getServletContext().setAttribute(CLOCK, Clock.systemDefaultZone());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Utente user = SessionManager.getUtente(session);
        String addres = "/_comuni/prenotazione.jsp";
        int id;
        if (user == null || !SessionManager.isAlradyAuthenticated(session)) {
            SessionManager.setError(session, "LogIn non effettuato");
            response.sendRedirect(request.getServletContext().getContextPath() + "/_comuni/login.jsp");
            return;
        }

        try {
            id = Integer.parseInt(request.getParameter("id_prenotazione"));
        }catch (Exception e){
            SessionManager.setError(session, "Prenotazione non presente");
            response.sendRedirect(request.getServletContext().getContextPath() + "/_comuni/login.jsp");
            return;
        }
        Prenotazione p;
        PrenotazioneDAO prenotazioneDAO = (PrenotazioneDAO) request.getServletContext().getAttribute(PRENOTAZIONE_DAO_PARAM);
        try {
            p = prenotazioneDAO.retriveById(id);
            if (p == null) {
                SessionManager.setError(session, "Prenotazione non presente nel DB");
                response.sendRedirect(request.getServletContext().getContextPath() + "/index.jsp");
                return;
            }
            prenotazioneDAO.delete(p);
            AulaDAO aulaDAO = (AulaDAO) request.getServletContext().getAttribute(AULA_DAO_PARAM);
            Aula a = aulaDAO.retriveById(p.getAula().getId());

            Clock clock = (Clock) request.getServletContext().getAttribute(CLOCK);
            Date oggi = Date.valueOf(LocalDate.now(clock));
            Time ora = Time.valueOf(LocalTime.now(clock));
            if (p.getData().equals(oggi) && (p.getOraFine().after(ora) && p.getOraInizio().before(ora))) {
                if (user.getTipoUtente().toString().equals(TipoUtente.STUDENTE.toString())) {
                    a.setPostiOccupati(a.getPostiOccupati() - 1);
                    aulaDAO.update(a);
                }

                if (user.getTipoUtente().toString().equals(TipoUtente.DOCENTE.toString())) {
                    a.setPostiOccupati(0);
                    aulaDAO.update(a);
                }
            }
        } catch (IllegalArgumentException e) {
            SessionManager.setError(session, e.getMessage());
//            addres"error.jsp";
        }
        response.sendRedirect(request.getServletContext().getContextPath() + addres);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    static final String PRENOTAZIONE_DAO_PARAM = "EliminaPrenotazioneServlet.PrenotazioneDAO";
    static final String AULA_DAO_PARAM = "EliminaPrenotazioneServlet.AulaDAO";
    static final String CLOCK = "EliminaPrenotazioneServlet.Clock";

}
