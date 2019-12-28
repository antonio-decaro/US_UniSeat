package control.studente;

import control.utili.DisponibilitaManager;
import control.utili.SessionManager;
import model.dao.AulaDAO;
import model.dao.EdificioDAO;
import model.dao.PrenotazioneDAO;
import model.database.DBAulaDAO;
import model.database.DBEdificioDAO;
import model.database.DBPrenotazioneDAO;
import model.pojo.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Questa classe si occupa di gestire le prenotazione dei posti effettuate dagli studenti.
 *
 * @author De Caro Antonio
 * @version 0.1
 * */
public class PrenotaPostoServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        getServletContext().setAttribute(PRENOTAZIONE_DAO, DBPrenotazioneDAO.getInstance());
        getServletContext().setAttribute(AULA_DAO, DBAulaDAO.getInstance());
        getServletContext().setAttribute(EDIFICIO_DAO, DBEdificioDAO.getInstance());
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
       doPost(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        HttpSession session = req.getSession();
        Utente utente = SessionManager.getUtente(session);
        if (session.isNew() || utente == null) {
            resp.sendRedirect(req.getContextPath() + "/autenticazione/log-in.jsp");
            return;
        }

        if (!utente.getTipoUtente().equals(TipoUtente.STUDENTE)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Non hai i permessi per accedere a questa funzionalità");
            resp.sendRedirect(req.getContextPath() + "/comuni/index.jsp");
            return;
        }

        EdificioDAO edificioDAO = (EdificioDAO) req.getServletContext().getAttribute(EDIFICIO_DAO);
        AulaDAO aulaDAO = (AulaDAO) req.getServletContext().getAttribute(AULA_DAO);
        PrenotazioneDAO prenotazioneDAO = (PrenotazioneDAO) req.getServletContext().getAttribute(PRENOTAZIONE_DAO);

        // controllo campi
        String nomeEdificio = req.getParameter("edificio");
        Edificio edificio = edificioDAO.retriveByName(nomeEdificio);
        if (edificio == null) {
            SessionManager.setError(session, "Edificio non valido");
            resp.sendRedirect(req.getContextPath() + "/studente/prenotazionePosto.jsp");
            return;
        }

        String idAula = req.getParameter("aula");
        if (idAula == null || idAula.equals("")) {
            SessionManager.setError(session, "Aula non valida");
            resp.sendRedirect(req.getContextPath() + "/studente/prenotazionePosto.jsp");
            return;
        }
        Aula aula = aulaDAO.retriveById(Integer.parseInt(idAula));
        if (aula == null) {
            SessionManager.setError(session, "Aula non valida");
            resp.sendRedirect(req.getContextPath() + "/studente/prenotazionePosto.jsp");
            return;
        }

        String strDurata = req.getParameter("durata").strip();
        if (strDurata == null || strDurata.equals("")) {
            SessionManager.setError(session, "Durata non valida");
            resp.sendRedirect(req.getContextPath() + "/studente/prenotazionePosto.jsp");
            return;
        }
        int durata = Integer.parseInt(strDurata);

        Date data = Date.valueOf(LocalDate.now());
        Time oraInizio = Time.valueOf(LocalTime.now());
        Time oraFine = Time.valueOf(oraInizio.toLocalTime().plusMinutes(durata));
        // fine controllo campi

        DisponibilitaManager disponibilita = new DisponibilitaManager(aula, prenotazioneDAO);
        boolean changed = false;
        while (durata > 0 && !disponibilita.isPostoDisponibile(data, oraInizio, oraFine)) {
            changed = true;
            durata -= 30; // sottraggo 30 minuti fin quando non riesco ad ottenere un orario disponibile.
            oraFine = Time.valueOf(oraInizio.toLocalTime().plusMinutes(durata));
        }

        if (durata == 0) {
            SessionManager.setError(session, "Aula non disponibile");
            resp.sendRedirect(req.getContextPath() + "/studente/prenotazionePosto.jsp");
            return;
        }

        if (changed) {
            SessionManager.setMessage(session, "L'orario di fine prenotazione è stato modificato");
        }

        Prenotazione p = new Prenotazione();
        p.setData(data);
        p.setOraInizio(oraInizio);
        p.setOraFine(oraFine);
        p.setAula(aula);
        p.setUtente(utente);
        p.setTipoPrenotazione(TipoPrenotazione.POSTO);
        prenotazioneDAO.insert(p);
        aula.setPostiOccupati(aula.getPostiOccupati() + 1); // aggiorno aula
        aulaDAO.update(aula);

        resp.sendRedirect(req.getContextPath() + "/comuni/prenotazioni.jsp");
    }

    static final String PRENOTAZIONE_DAO = "PrenotaPostoServlet.PrenotazioneDAO";
    static final String AULA_DAO = "PrenotaPostoServlet.AulaDAO";
    static final String EDIFICIO_DAO = "PrenotaPostoServlet.EdificioDAO";
}
