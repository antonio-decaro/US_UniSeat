package control.docente;

import control.utili.DisponibilitaManager;
import control.utili.SessionManager;
import model.dao.AulaDAO;
import model.dao.PrenotazioneDAO;
import model.database.DBAulaDAO;
import model.pojo.Aula;
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
import java.sql.Time;

/**
 * Questa servlet controlla se una data aula è diponibile ad una determinata ora di un determinato giorno.
 * Scrive la risposta all'interno del ServletOutputStream. Non effettua il redirect a nessun'altra risorsa del server.
 *
 * @author De Caro Antonio
 * @version 0.1
 * */
@WebServlet("/controllaDisponibilita")
public class ControllaDisponibilitaServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        getServletContext().setAttribute(AULA_DAO, DBAulaDAO.getInstance());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        AulaDAO aulaDAO = (AulaDAO) req.getServletContext().getAttribute(AULA_DAO);
        PrenotazioneDAO prenotazioneDAO = (PrenotazioneDAO) req.getServletContext().getAttribute(PRENOTAZIONE_DAO);

        try (PrintWriter pw = resp.getWriter()) {
            Utente utente = SessionManager.getUtente(session);
            if (utente == null) {
                final String ERROR = "Utente non loggato";
                SessionManager.setError(session, ERROR);
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                pw.print(ERROR);
                return;
            }
            else if (!utente.getTipoUtente().equals(TipoUtente.DOCENTE)) {
                final String ERROR = "Non hai i permessi per accedere a questa risorsa";
                SessionManager.setError(session, ERROR);
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                pw.print(ERROR);
                return;
            }

            Aula aula;
            Date data;
            Time oraInizio, oraFine;
            int durata;
            try {
                aula = parseAula(req.getParameter("aula"), aulaDAO);
                data = parseDate(req.getParameter("data"));
                oraInizio = parseOra(req.getParameter("oraInizio"));
                durata = parseDurata(req.getParameter("durata"));
                oraFine = Time.valueOf(oraInizio.toLocalTime().plusHours(durata));
            } catch (IllegalArgumentException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                pw.print(e.getMessage());
                return;
            }

            DisponibilitaManager disponibilitaManager = new DisponibilitaManager(aula, prenotazioneDAO);
            if (disponibilitaManager.isAulaDisponibile(data, oraInizio, oraFine)) {
                pw.print(true);
            } else {
                pw.print(false);
            }

            resp.setStatus(HttpServletResponse.SC_OK);
            pw.flush();
        }
    }

    private int parseDurata(String param) {
        if (param == null || param.strip().equals("") || !param.matches("^[+\\-]?[0-9]+$")) {
            throw new IllegalArgumentException("Durata non valida");
        }

        int durata = Integer.parseInt(param);
        if (durata <= 0) {
            throw new IllegalArgumentException("Durata non valida");
        }
        return durata;
    }

    private Time parseOra(String param) {
        if (param == null || param.strip().equals("")) {
            throw new IllegalArgumentException("Ora non valida");
        }
        try {
            return Time.valueOf(param);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Formato ora non valido");
        }
    }

    private Date parseDate(String param) {
        if (param == null || param.strip().equals(""))
            throw new IllegalArgumentException("Data non valida");

        try {
            return Date.valueOf(param);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Formato data non valido");
        }
    }

    private Aula parseAula(String parameter, AulaDAO aulaDAO) {
        if (parameter == null || parameter.equals("")) {
            throw new IllegalArgumentException("Aula non valida");
        }
        Aula aula = aulaDAO.retriveById(Integer.parseInt(parameter));
        if (aula == null) {
            throw new IllegalArgumentException("Aula non valida");
        }

        return aula;
    }


    static final String AULA_DAO = "ControllaDisponibilitaServlet.AulaDAO";
    static final String PRENOTAZIONE_DAO = "ControllaDisponibilitaServlet.PrenotazioneDAO";
}
