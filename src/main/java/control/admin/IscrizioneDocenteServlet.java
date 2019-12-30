package control.admin;

import control.utili.EmailManager;
import control.utili.PassowrdEncrypter;
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

@WebServlet("/iscrizioneDocente")
public class IscrizioneDocenteServlet extends HttpServlet {
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
            resp.sendRedirect(req.getServletContext().getContextPath() + "/comuni/index.jsp");
            return;
        }

        String email = req.getParameter("email");
        String nome = req.getParameter("nome");
        String cognome = req.getParameter("cognome");
        String password = req.getParameter("password");
        String confPassword = req.getParameter("confPassword");

        // controllo validità campi
        if (nome == null || nome.length() < 1 || nome.length() > 20) {
            SessionManager.setError(session, "Il campo Nome non rispetta la lunghezza");
            resp.sendRedirect(req.getServletContext().getContextPath() + "/docente/registrazione.jsp");
            return;
        }
        if (!nome.matches("^[a-z A-Z]+$")) {
            SessionManager.setError(session, "Il campo Nome non rispetta il formato");
            resp.sendRedirect(req.getServletContext().getContextPath() + "/docente/registrazione.jsp");
            return;
        }
        if (cognome == null || cognome.length() < 1 || cognome.length() > 20) {
            SessionManager.setError(session, "Il campo Cognome non rispetta la lunghezza");
            resp.sendRedirect(req.getServletContext().getContextPath() + "/docente/registrazione.jsp");
            return;
        }
        if (!cognome.matches("^[a-z A-Z]+$")) {
            SessionManager.setError(session, "Il campo Cognome non rispetta il formato");
            resp.sendRedirect(req.getServletContext().getContextPath() + "/docente/registrazione.jsp");
            return;
        }
        int length = email.substring(0, email.indexOf("@")).length();
        if (length > 30 || length < 6) {
            SessionManager.setError(session, "Il campo E-mail non rispetta la lunghezza");
            resp.sendRedirect(req.getServletContext().getContextPath() + "/docente/registrazione.jsp");
            return;
        }
        if (!email.endsWith("@unisa.it") && !email.endsWith("@studenti.unisa.it")) {
            SessionManager.setError(session, "Il campo E-mail non rispetta il formato");
            resp.sendRedirect(req.getServletContext().getContextPath() + "/docente/registrazione.jsp");
            return;
        }
        if (password.length() > 32 || password.length() < 8) {
            SessionManager.setError(session, "Il campo Password non rispetta la lunghezza");
            resp.sendRedirect(req.getServletContext().getContextPath() + "/docente/registrazione.jsp");
            return;
        }
        if (!password.matches("^((?=.*[\\d])(?=.*[a-z])(?=.*[A-Z])).+$")) {
            SessionManager.setError(session, "Il campo Password non rispetta il formato");
            resp.sendRedirect(req.getServletContext().getContextPath() + "/docente/registrazione.jsp");
            return;
        }
        if (!confPassword.equals(password)){
            SessionManager.setError(session, "Le password non corrispondono");
            resp.sendRedirect(req.getServletContext().getContextPath() + "/docente/registrazione.jsp");
            return;
        }
        // fine controllo validità campi
        UtenteDAO utenteDAO = (UtenteDAO) req.getServletContext().getAttribute(UTENTE_DAO_PARAM);

        Random rand = new Random();
        Utente utente = new Utente(email, nome, cognome, PassowrdEncrypter.criptaPassword(password), TipoUtente.DOCENTE);
        utente.setCodiceVerifica(rand.nextLong());

        try {
            utenteDAO.insert(utente);
            EmailManager emailManager = (EmailManager) req.getServletContext().getAttribute(EMAIL_PARAM);
            emailManager.inviaEmailConferma(utente);
        } catch (ViolazioneEntityException e) {
            SessionManager.setError(session, e.getMessage());
        }
        resp.sendRedirect(req.getServletContext().getContextPath() + "/docente/registrazione.jsp");
    }

    static final String UTENTE_DAO_PARAM = "IscrizioneDocenteServlet.user";
    static final String EMAIL_PARAM = "IscrizioneDocenteServlet.emailManager";
}
