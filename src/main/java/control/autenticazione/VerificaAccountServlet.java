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

@WebServlet("verify")
public class VerificaAccountServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        getServletContext().setAttribute(UTENTE_DAO, DBUtenteDAO.getInstance());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utente utente = SessionManager.getUtente(session);
        if (utente == null || utente.getCodiceVerifica() == 0) {
            resp.sendRedirect(req.getContextPath() + "/comuni/index.jsp");
            return;
        }

        String email = req.getParameter("email");
        String code = req.getParameter("code");

        if (email == null || code == null) {
            EmailManager emailManager = new EmailManager(req.getServletContext().getInitParameter("hostname"));
            emailManager.inviaEmailConferma(utente);
        }

        else {
            long verification = utente.getCodiceVerifica();
            if (Long.parseLong(code) == verification) {
                utente.setCodiceVerifica(0);
                UtenteDAO utenteDAO = (UtenteDAO) req.getServletContext().getAttribute(UTENTE_DAO);
                utenteDAO.update(utente);
            }
        }

        resp.sendRedirect(req.getContextPath() + "/comuni/index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private static final String UTENTE_DAO = "VerificaAccountServlet.utenteDao";
}
