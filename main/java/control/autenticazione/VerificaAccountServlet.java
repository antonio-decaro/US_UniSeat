package control.autenticazione;

import control.utili.EmailManager;
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

@WebServlet("/verifica")
public class VerificaAccountServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        getServletContext().setAttribute(UTENTE_DAO, DBUtenteDAO.getInstance());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        String email = req.getParameter("email");
        String code = req.getParameter("code");
        if (email == null || SessionManager.isAlradyAuthenticated(session)) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        UtenteDAO utenteDAO = (UtenteDAO) req.getServletContext().getAttribute(UTENTE_DAO);
        Utente utente = utenteDAO.retriveByEmail(email);
        if (utente == null) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        if (code == null) {
            EmailManager emailManager = new EmailManager(req.getServletContext().getInitParameter("hostname"));
            new Thread(() -> emailManager.inviaEmailConferma(utente)).start();
        }

        else {
            int verification = utente.getCodiceVerifica();
            if (Integer.parseInt(code) == verification) {
                utente.setCodiceVerifica(0);
                utenteDAO.update(utente);
            }
        }

        session.invalidate();
        resp.sendRedirect(req.getContextPath() + "/_comuni/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }

    private static final String UTENTE_DAO = "VerificaAccountServlet.utenteDao";
}
