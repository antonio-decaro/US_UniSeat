package control.studente;

import control.utili.PassowrdEncrypter;
import control.utili.SessionManager;
import model.dao.UtenteDAO;
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

@WebServlet("/signin")
public class RegistrazioneServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        this.getServletContext().setAttribute(UTENTE_DAO_PARAM, DBUtenteDAO.getInstance());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (SessionManager.isAlradyAuthenticated(session)) {
            resp.sendRedirect(req.getServletContext().getContextPath() + "/home");
            return;
        }

        String email = req.getParameter("email");
        String nome = req.getParameter("nome");
        String cognome = req.getParameter("cognome");
        String password = req.getParameter("passowrd");
        String confPassword = req.getParameter("confPassword");

        // controllo validità campi
        if (email == null || password == null) {
            SessionManager.setError(session, "Uno dei campi email o password non rispetta la lunghezza");
            resp.sendRedirect(req.getServletContext().getContextPath() + "/login");
            return;
        }
        if (!email.endsWith("@unisa.it") && !email.endsWith("@studenti.unisa.it")) {
            SessionManager.setError(session, "Il campo E-mail non rispetta il formato");
            resp.sendRedirect(req.getServletContext().getContextPath() + "/login");
            return;
        }
        int length = email.substring(0, email.indexOf("@")).length();
        if (length > 30 || length < 6) {
            SessionManager.setError(session, "Il campo E-mail non rispetta la lunghezza");
            resp.sendRedirect(req.getServletContext().getContextPath() + "/login");
            return;
        }
        if (nome == null || nome.length() < 1 || nome.length() > 20) {
            SessionManager.setError(session, "Il campo Nome non rispetta la lunghezza");
            resp.sendRedirect(req.getServletContext().getContextPath() + "/login");
            return;
        }
        if (!nome.matches("^[a-z A-Z]+$")) {
            SessionManager.setError(session, "Il campo Nome non rispetta il formato");
            resp.sendRedirect(req.getServletContext().getContextPath() + "/login");
            return;
        }
        if (cognome == null || cognome.length() < 1 || cognome.length() > 20) {
            SessionManager.setError(session, "Il campo Cognome non rispetta la lunghezza");
            resp.sendRedirect(req.getServletContext().getContextPath() + "/login");
            return;
        }
        if (!cognome.matches("^[a-z A-Z]+$")) {
            SessionManager.setError(session, "Il campo Cognome non rispetta il formato");
            resp.sendRedirect(req.getServletContext().getContextPath() + "/login");
            return;
        }
        if (password.length() > 32 || password.length() < 8) {
            SessionManager.setError(session, "Il campo Password non rispetta la lunghezza");
            resp.sendRedirect(req.getServletContext().getContextPath() + "/login");
            return;
        }
        if (!password.matches("^((?=.*[\\d])(?=.*[a-z])(?=.*[A-Z])).+$")) {
            SessionManager.setError(session, "Il campo Password non rispetta il formato");
            resp.sendRedirect(req.getServletContext().getContextPath() + "/login");
            return;
        }
        if (!confPassword.equals(password)){
            SessionManager.setError(session, "Le password non corrispondono");
            resp.sendRedirect(req.getServletContext().getContextPath() + "/login");
            return;
        }
        // fine controllo validità campi
        UtenteDAO utenteDAO = (UtenteDAO) req.getServletContext().getAttribute(UTENTE_DAO_PARAM);

        if (utenteDAO.retriveByEmail(email) != null) { // controllo se non esiste già nel db
            SessionManager.setError(session, "E-mail già in uso");
            resp.sendRedirect(req.getServletContext().getContextPath() + "/login");
            return;
        }

        Utente utente = new Utente(email, nome, cognome, PassowrdEncrypter.criptaPassword(password), TipoUtente.STUDENTE);

    }

    public static final String UTENTE_DAO_PARAM = "RegistrazioneServlet.user";
}
