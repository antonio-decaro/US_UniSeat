package control.comuni;

import com.google.gson.Gson;
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
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;
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
        // TODO rendere compatibile con AJAX
        HttpSession ssn = request.getSession();
        Utente user = SessionManager.getUtente(ssn);

        if (user == null) {
            SessionManager.setError(ssn, "LogIn non effettuato");
            response.sendRedirect(request.getServletContext().getContextPath() + "/comuni/login.jsp");
            return;
        }
        try {
            PrenotazioneDAO prenotazioneDAO = (PrenotazioneDAO) request.getServletContext().getAttribute(PRENOTAZIONE_DAO_PARAM);
            Date date = Date.valueOf(LocalDate.now());
            String uem = user.getEmail();
            List<Prenotazione> prenotazioni;
            prenotazioni = prenotazioneDAO.retriveByData(date);
            for (Iterator<Prenotazione> i = prenotazioni.iterator(); i.hasNext(); ) {
                Prenotazione p = i.next();
                String e = p.getUtente().getEmail();
                if (!e.equals(uem))
                    i.remove();
            }
            if (prenotazioni.isEmpty())
                request.setAttribute("prenotazione", null);
            else {
                Gson gson = new Gson();
                PrintWriter pw = response.getWriter();
                if (user.getTipoUtente().toString().equals(TipoUtente.STUDENTE.toString())) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    pw.print(gson.toJson(prenotazioni.get(0)));
                    pw.close();
                }

                if (user.getTipoUtente().toString().equals(TipoUtente.DOCENTE.toString())) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    pw.print(gson.toJson(prenotazioni));
                    pw.close();
                }
                /*if(user.getTipoUtente().toString().equals(TipoUtente.ADMIN)){

                }*/
            }

        } catch (IllegalArgumentException e) {
            SessionManager.setError(ssn, e.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    static final String PRENOTAZIONE_DAO_PARAM = "PrelevaPrenotazioneServlet.PrenotazioneDAO";
}
