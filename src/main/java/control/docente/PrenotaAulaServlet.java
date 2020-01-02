package control.docente;

import control.utili.DisponibilitaManager;
import control.utili.EmailManager;
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
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

/**
 * Questa classe si occupa di gestire le prenotazione delle aule effettuate dai docenti.
 *
 * @author De Caro Antonio
 * @version 0.1
 * */
public class PrenotaAulaServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        getServletContext().setAttribute(PRENOTAZIONE_DAO, DBPrenotazioneDAO.getInstance());
        getServletContext().setAttribute(AULA_DAO, DBAulaDAO.getInstance());
        getServletContext().setAttribute(EDIFICIO_DAO, DBEdificioDAO.getInstance());
        String hostname = getServletContext().getInitParameter("hostname");
        getServletContext().setAttribute(EMAIL_MANAGER, new EmailManager(hostname));
        getServletContext().setAttribute(CLOCK,  Clock.systemDefaultZone());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        Utente utente = SessionManager.getUtente(session);
        if (utente == null) {
            resp.sendRedirect(req.getContextPath() + "/autenticazione/log-in.jsp");
            SessionManager.setError(session, "Utente non loggato");
            return;
        }

        if (!utente.getTipoUtente().equals(TipoUtente.DOCENTE)) {
            final String ERROR = "Non hai i permessi per accedere a questa funzionalità";
            SessionManager.setError(session, ERROR);
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, ERROR);
            resp.sendRedirect(req.getContextPath() + "/comuni/index.jsp");
            return;
        }

        EdificioDAO edificioDAO = (EdificioDAO) req.getServletContext().getAttribute(EDIFICIO_DAO);
        AulaDAO aulaDAO = (AulaDAO) req.getServletContext().getAttribute(AULA_DAO);
        PrenotazioneDAO prenotazioneDAO = (PrenotazioneDAO) req.getServletContext().getAttribute(PRENOTAZIONE_DAO);

        // controllo campi
        Edificio edificio;
        Aula aula;
        int durata;
        Date data;
        Time oraInizio;
        try {
            edificio = parseEdificio(req.getParameter("edificio"), edificioDAO);
            aula = parseAula(req.getParameter("aula"), edificio, aulaDAO);
            data = parseData(req.getParameter("data"));
            oraInizio = parseOra(req.getParameter("oraInizio"));
            durata = parseDurata(req.getParameter("durata"));
        } catch (IllegalArgumentException e) {
            SessionManager.setError(session, e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/docente/prenotazioneAula.jsp");
            return;
        }
        Time oraFine = Time.valueOf(oraInizio.toLocalTime().plusHours(durata));
        // fine controllo campi

        DisponibilitaManager disponibilita = new DisponibilitaManager(aula, prenotazioneDAO);
        if (!disponibilita.isAulaDisponibile(data, oraInizio, oraFine)) {
            SessionManager.setError(session, "Aula non disponibile");
            resp.sendRedirect(req.getContextPath() + "/docente/prenotazioneAula.jsp");
            return;
        }

        Prenotazione prenotazione = new Prenotazione(0, data, oraInizio, oraFine, TipoPrenotazione.AULA,
                aula, utente);
        prenotazioneDAO.insert(prenotazione);

        // ora gestisco le prenotazioni degli studenti che si sovrappongono con quella del docente
        Clock clock = (Clock) req.getServletContext().getAttribute(CLOCK);
        if (Date.valueOf(LocalDate.now(clock)).equals(data)) {
            List<Prenotazione> prenotazioniDaEliminare = prenotazioneDAO.retriveByData(data);
            prenotazioniDaEliminare.removeIf(p -> p.getTipoPrenotazione().equals(TipoPrenotazione.AULA));
            prenotazioniDaEliminare.removeIf(p -> p.getAula().getId() != aula.getId());
            prenotazioniDaEliminare.removeIf(p -> !overlaps(oraInizio, oraFine, p.getOraInizio(), p.getOraFine()));

            EmailManager emailManager = (EmailManager) req.getServletContext().getAttribute(EMAIL_MANAGER);
            for (Prenotazione p : prenotazioniDaEliminare) {
                // associo l'operazione ad un thread per evitare overhead dovuto al servizio email.
                new Thread(() -> emailManager.comunicaPrenotazione(p.getUtente(), prenotazione)).start();

                if (p.getOraInizio().before(prenotazione.getOraInizio())) {
                    p.setOraFine(prenotazione.getOraInizio());
                    prenotazioneDAO.update(p);
                }
                else {
                    prenotazioneDAO.delete(p);
                }
            }
        }

        resp.sendRedirect(req.getContextPath() + "/comuni/prenotazioni.jsp");
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

    private Date parseData(String param) {
        if (param == null || param.strip().equals(""))
            throw new IllegalArgumentException("Data non valida");
        DayOfWeek day;
        switch(param) {
            case "LUNEDI":
                day = DayOfWeek.of(1);
                break;
            case "MARTEDI":
                day = DayOfWeek.of(2);
                break;
            case "MERCOLEDI":
                day = DayOfWeek.of(3);
                break;
            case "GIOVEDI":
                day = DayOfWeek.of(4);
                break;
            case "VENERDI":
                day = DayOfWeek.of(5);
                break;
            default:
                throw new IllegalArgumentException("Data non valida");
        }
        LocalDate date = LocalDate.now();
        while (!date.getDayOfWeek().equals(day)) {
            date = date.plusDays(1);
        }

        return Date.valueOf(date);
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

    private Aula parseAula(String parameter, Edificio edificio, AulaDAO aulaDAO) throws IllegalArgumentException {
        if (parameter == null || parameter.equals("")) {
            throw new IllegalArgumentException("Aula non valida");
        }
        Aula aula = aulaDAO.retriveById(Integer.parseInt(parameter));
        if (aula == null || !aula.getEdificio().getNome().equals(edificio.getNome())) {
            throw new IllegalArgumentException("Aula non valida");
        }
        return aula;
    }

    private Edificio parseEdificio(String param, EdificioDAO edificioDAO) throws IllegalArgumentException {
        Edificio edificio = edificioDAO.retriveByName(param);
        if (edificio == null) {
            throw new IllegalArgumentException("Edificio non valido");
        }
        return edificio;
    }

    /**
     * Questo metodo controlla se due intervalli di tempo si sovrappongono
     *
     * @param oraInizio1 vertice sinistro del primo intervallo
     * @param oraFine1 vertice destro del secondo intervallo
     * @param oraInizio2 vertice sinistro del secondo intervallo
     * @param oraFine2 vertice destro del secondo intervallo
     * @return true se i due intervalli si sovrappongono, false altrimenti
     * @throws IllegalArgumentException se oraFine1 o oraFine2 sono minori rispettivamente di oraInizio1 o oraInizio2
     * */
    private boolean overlaps(Time oraInizio1, Time oraFine1, Time oraInizio2, Time oraFine2) {
        if (oraFine1.before(oraInizio1) || oraFine2.before(oraInizio2))
            throw new IllegalArgumentException("L'ora di fine non può precedere nel tempo l'ora di inizio");

        int valOraInizio1 = oraInizio1.toLocalTime().toSecondOfDay();
        int valOraFine1 = oraFine1.toLocalTime().toSecondOfDay();
        int valOraInizio2 = oraInizio2.toLocalTime().toSecondOfDay();
        int valOraFine2 = oraFine2.toLocalTime().toSecondOfDay();

        return (valOraInizio1 <= valOraInizio2 && valOraInizio2 <= valOraFine1) ||
                (valOraInizio1 <= valOraFine2 && valOraFine2 <= valOraFine1) ||
                (valOraInizio2 < valOraInizio1 && valOraFine2 > valOraFine1);
    }

    static final String PRENOTAZIONE_DAO = "PrenotaAulaServlet.PrenotazioneDAO";
    static final String AULA_DAO = "PrenotaAulaServlet.AulaDAO";
    static final String EDIFICIO_DAO = "PrenotaAulaServlet.EdificioDAO";
    static final String EMAIL_MANAGER = "PrenotaAulaServlet.EmailManager";
    static final String CLOCK = "PrenotaAulaServlet.Clock";
}
