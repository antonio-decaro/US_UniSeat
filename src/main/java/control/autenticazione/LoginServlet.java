package control.autenticazione;

import control.utili.PassowrdEncrypter;
import control.utili.SessionManager;
import model.dao.UtenteDAO;
import model.database.DBUtenteDAO;
import model.pojo.Utente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Questa classe effettua l'autenticazione di tutti gli utenti registrati al sistema
 *
 * @author De Caro Antonio
 * @version 0.1
 * */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        getServletContext().setAttribute(UTENTE_DAO_PARAM, DBUtenteDAO.getInstance());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        Utente utente = SessionManager.getUtente(session);
        if (utente != null){ // se già autenticato reindirizzo alla home
            resp.sendRedirect(req.getServletContext().getContextPath() + "/jsp/index.jsp");
            return;
        }

        // controllo validità campi
        String email, password;
        try {
            email = parseEmail(req.getParameter("email"));
            password = parsePassword(req.getParameter("password"));
        } catch (IllegalArgumentException e) {
            SessionManager.setError(session, e.getMessage());
            resp.sendRedirect(req.getServletContext().getContextPath() + "/jsp/login.jsp");
            return;
        }
        // fine controllo validità campi

        UtenteDAO utenteDAO = (UtenteDAO) req.getServletContext().getAttribute(UTENTE_DAO_PARAM);

        Utente u = utenteDAO.retriveByEmail(email);
        if (u == null || !u.getPassword().equals(PassowrdEncrypter.criptaPassword(password))) {
            SessionManager.setError(session, "Credenziali non corrette");
            resp.sendRedirect(req.getServletContext().getContextPath() + "/jsp/login.jsp");
            return;
        }

        if (u.getCodiceVerifica() != 0) {
            SessionManager.setError(session, "Devi confermare l'email prima di poter accedere");
            resp.sendRedirect(req.getServletContext().getContextPath() + "/jsp/login.jsp");
            return;
        }

        SessionManager.autentica(session, u);
        resp.sendRedirect(req.getServletContext().getContextPath() + "/jsp/index.jsp");
    }

    private String parsePassword(String param) {
        if (param.length() > 32 || param.length() < 8) {
            throw new IllegalArgumentException("Il campo Password non rispetta la lunghezza");
        }
        if (!param.matches("^((?=.*[\\d])(?=.*[a-z])(?=.*[A-Z])).+$")) {
            throw new IllegalArgumentException("Il campo Password non rispetta il formato");
        }
        return param;
    }

    private String parseEmail(String param) {
        if (param == null || param.strip().equals("")) {
            throw new IllegalArgumentException("Il campo email non rispetta la lunghezza");
        }
        if (!param.endsWith("@unisa.it") && !param.endsWith("@studenti.unisa.it")) {
            throw new IllegalArgumentException("Il campo E-mail non rispetta il formato");
        }
        int length = param.substring(0, param.indexOf("@")).length();
        if (length > 30 || length < 6) {
            throw new IllegalArgumentException("Il campo email non rispetta la lunghezza");
        }
        return param;
    }


    static final String UTENTE_DAO_PARAM = "LoginServlet.UtenteDAO";
}
