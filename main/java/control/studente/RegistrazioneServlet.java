package control.studente;

import control.utili.EmailManager;
import control.utili.PasswordEncrypter;
import control.utili.SessionManager;
import model.dao.UtenteDAO;
import model.dao.ViolazioneEntityException;
import model.database.DBUtenteDAO;
import model.pojo.TipoUtente;
import model.pojo.Utente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Random;

/**
 * Questa classe registra nuovi utenti al sistema
 *
 * @author De Caro Antonio
 * @version 0.1
 * */
@WebServlet("/signin")
public class RegistrazioneServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        this.getServletContext().setAttribute(UTENTE_DAO_PARAM, DBUtenteDAO.getInstance());
        String hostname = this.getServletContext().getInitParameter("hostname");
        this.getServletContext().setAttribute(EMAIL_PARAM, new EmailManager(hostname));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        if (SessionManager.isAlradyAuthenticated(session)) {
            resp.sendRedirect(req.getServletContext().getContextPath() + "/index.jsp");
            return;
        }

        String email, nome, cognome, password;

        // controllo validità campi
        try {
            email = parseEmail(req.getParameter("email"));
            nome = parseNomeAndCognome(req.getParameter("nome"), "Nome");
            cognome = parseNomeAndCognome(req.getParameter("cognome"), "Cognome");
            password = parsePassword(req.getParameter("password"), req.getParameter("confPassword"));
        } catch(IllegalArgumentException e) {
            SessionManager.setError(session, e.getMessage());
            resp.sendRedirect(req.getServletContext().getContextPath() + "/_studente/registrazione.jsp");
            return;
        }
        // fine controllo validità campi

        UtenteDAO utenteDAO = (UtenteDAO) req.getServletContext().getAttribute(UTENTE_DAO_PARAM);

        Utente utente = new Utente(email, nome, cognome, PasswordEncrypter.criptaPassword(password), TipoUtente.STUDENTE);
        Random rand = new Random();
        utente.setCodiceVerifica(rand.nextInt());

        try {
            utenteDAO.insert(utente);
            EmailManager emailManager = (EmailManager) req.getServletContext().getAttribute(EMAIL_PARAM);
            new Thread(()->emailManager.inviaEmailConferma(utente)).start();
            SessionManager.setMessage(session, "E-Mail di conferma inviata al tuo indirizzo di posta");
        } catch (ViolazioneEntityException e) {
            SessionManager.setError(session, e.getMessage());
        }
        resp.sendRedirect(req.getServletContext().getContextPath() + "/_studente/registrazione.jsp");
    }

    private String parsePassword(String password, String confPassword) {
        if (password.length() > 32 || password.length() < 8) {
            throw new IllegalArgumentException("Il campo Password non rispetta la lunghezza");
        }
        if (!password.matches("^((?=.*[\\d])(?=.*[a-z])(?=.*[A-Z])).+$")) {
            throw new IllegalArgumentException("Il campo Password non rispetta il formato");
        }
        if (!password.equals(confPassword)){
            throw new IllegalArgumentException("Le password non corrispondono");
        }
        return password;
    }

    private String parseEmail(String param) {
        if (param == null || param.strip().equals("")) {
            throw new IllegalArgumentException("Il campo E-mail non rispetta la lunghezza");
        }
        if (!param.endsWith("@unisa.it") && !param.endsWith("@studenti.unisa.it")) {
            throw new IllegalArgumentException("Il campo E-mail non rispetta il formato");
        }
        int length = param.substring(0, param.indexOf("@")).length();
        if (length > 30 || length < 6) {
            throw new IllegalArgumentException("Il campo E-mail non rispetta la lunghezza");
        }
        return param;
    }

    private String parseNomeAndCognome(String param, String paramName) {
        if (param == null || param.strip().length() < 1 || param.strip().length() > 20) {
            throw new IllegalArgumentException("Il campo " + paramName + " non rispetta la lunghezza");
        }
        if (!param.matches("^[a-z A-Z]+$")) {
            throw new IllegalArgumentException("Il campo " + paramName + " non rispetta il formato");
        }
        return param;
    }

    static final String UTENTE_DAO_PARAM = "RegistrazioneServlet.user";
    static final String EMAIL_PARAM = "RegistrazioneServlet.emailManager";
}
