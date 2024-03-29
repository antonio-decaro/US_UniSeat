package control.admin;

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

/**
 * Questa servlet permette all'admin di eliminare un utente dal sistema
 * @author Spinelli Gianluca
 * @version 0.1
 * @see model.pojo.Utente
 * @see model.dao.UtenteDAO
 */

@WebServlet("/eliminaUtente")
public class EliminaUtenteServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        getServletContext().setAttribute(UTENTE_DAO_PARAM, DBUtenteDAO.getInstance());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente u = SessionManager.getUtente(session);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        if (u == null || !u.getTipoUtente().equals(TipoUtente.ADMIN)) { // se non è admin o non è loggato
            SessionManager.setError(session, "Utente non abilitato");
            response.sendError(HttpServletResponse.SC_FORBIDDEN,"Utente non abilitato");
            return;
        }

        String email = request.getParameter("email_utente");
        if (email == null) {
            response.getWriter().print("Utente non selezionato");
            SessionManager.setError(session, "Utente non selezionato");
            return;
        }

        UtenteDAO utenteDAO = (UtenteDAO) request.getServletContext().getAttribute(UTENTE_DAO_PARAM);
        Utente utente_el = utenteDAO.retriveByEmail(email);
        utenteDAO.delete(utente_el);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print("Utente eliminato con successo");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);

    }

    public static final String UTENTE_DAO_PARAM = "EliminaUtenteServlet.UtenteDAO";

}
