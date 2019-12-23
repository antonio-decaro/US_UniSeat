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

@WebServlet("login")
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
        if (!session.isNew()){ // se già autenticato reindirizzo alla home
            resp.sendRedirect(req.getServletContext().getContextPath() + "/home");
            return;
        }

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        // controllo validità campi
        if (email == null || password == null) {
            SessionManager.setError(session, "Uno dei campi email o password non rispetta la lunghezza");
            resp.sendRedirect(req.getServletContext().getContextPath() + "/comuni/login.jsp");
            return;
        }
        if (!email.endsWith("@unisa.it") && !email.endsWith("@studenti.unisa.it")) {
            SessionManager.setError(session, "Il campo E-mail non rispetta il formato");
            resp.sendRedirect(req.getServletContext().getContextPath() + "/comuni/login.jsp");
            return;
        }
        int length = email.substring(0, email.indexOf("@")).length();
        if (length > 30 || length < 6) {
            SessionManager.setError(session, "Il campo E-mail non rispetta la lunghezza");
            resp.sendRedirect(req.getServletContext().getContextPath() + "/comuni/login.jsp");
            return;
        }
        if (password.length() > 32 || password.length() < 8) {
            SessionManager.setError(session, "Il campo Password non rispetta la lunghezza");
            resp.sendRedirect(req.getServletContext().getContextPath() + "/comuni/login.jsp");
            return;
        }
        if (!password.matches("^((?=.*[\\d])(?=.*[a-z])(?=.*[A-Z])).+$")) {
            SessionManager.setError(session, "Il campo Password non rispetta il formato");
            resp.sendRedirect(req.getServletContext().getContextPath() + "/comuni/login.jsp");
            return;
        }
        // fine controllo validità campi
        UtenteDAO utenteDAO = (UtenteDAO) req.getServletContext().getAttribute(UTENTE_DAO_PARAM);

        Utente u = utenteDAO.retriveByEmail(email);
        if (u == null || !u.getPassword().equals(PassowrdEncrypter.criptaPassword(password))) {
            SessionManager.setError(session, "Credenziali non corrette");
            resp.sendRedirect(req.getServletContext().getContextPath() + "/comuni/login.jsp");
            return;
        }

        SessionManager.autentica(session, u);
        resp.sendRedirect(req.getServletContext().getContextPath() + "/comuni/index.jsp");
    }

    static final String UTENTE_DAO_PARAM = "LoginServlet.UtenteDAO";
}
