package control.studente;

import control.utili.SessionManager;
import model.dao.UtenteDAO;
import model.dao.ViolazioneEntityException;
import model.database.DBUtenteDAO;
import model.pojo.Utente;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Questa servlet permette all'admin di modificare i dati di un utente all'interno del database
 *
 * @author De Santis Marco
 * @version 0.1
 * @see model.pojo.Utente
 * @see model.dao.UtenteDAO
 */

@WebServlet("/modificaProfilo")
public class ModificaDatiProfiloServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        getServletContext().setAttribute(UTENTE_DAO_PARAM, DBUtenteDAO.getInstance());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession ssn = request.getSession();
        Utente user = SessionManager.getUtente(ssn);
        String addres = "VisulizzaProfilo.jsp";


        if (user == null) {
            SessionManager.setError(ssn, "LogIn non effettuato");
            response.sendRedirect(request.getServletContext().getContextPath() + "/login");
            return;
        }

        Utente u = new Utente();
        int count = 0;
        String Rgx1 = "^[a-z A-Z]+$";
        String Rgx2 = "^((?=.*[\\d])(?=.*[a-z])(?=.*[A-Z])).+$";

        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String password = request.getParameter("password");
        String cPassword = request.getParameter("confPassword");

        if (nome == null || nome.length() < 1 || nome.length() > 20) {
            SessionManager.setError(ssn, "Il campo Nome non rispetta la lunghezza");
            response.sendRedirect(request.getServletContext().getContextPath() + "/VisualizzaProfilo");
            return;
        }
        if (nome.matches(Rgx1)) {
            u.setNome(nome);
            count++;
        } else {
            SessionManager.setError(ssn, "Il campo Nome non rispetta il formato");
            response.sendRedirect(request.getServletContext().getContextPath() + "/VisualizzaProfilo");
            return;
        }

        if (cognome == null || cognome.length() < 1 || cognome.length() > 20) {
            SessionManager.setError(ssn, "Il campo Cognome non rispetta la lunghezza");
            response.sendRedirect(request.getServletContext().getContextPath() + "/VisualizzaProfilo");
            return;
        }
        if (cognome.matches(Rgx1)) {
            u.setCognome(cognome);
            count++;
        } else {
            SessionManager.setError(ssn, "Il campo Cognome non rispetta il formato");
            response.sendRedirect(request.getServletContext().getContextPath() + "/VisualizzaProfilo");
            return;
        }

        if (password == null || password.length() > 32 || password.length() < 8) {
            SessionManager.setError(ssn, "Il campo Password non rispetta la lunghezza");
            response.sendRedirect(request.getServletContext().getContextPath() + "/VisualizzaProfilo");
            return;
        }
        if (password.matches(Rgx2)) {
            if (password.equals(cPassword)) {
                u.setPassword(password);
                count++;
            } else {
                SessionManager.setError(ssn, "Le Password non corrispondono");
                response.sendRedirect(request.getServletContext().getContextPath() + "/VisualizzaProfilo");
                return;
            }
        } else {
            SessionManager.setError(ssn, "Il campo Password non rispetta il formato");
            response.sendRedirect(request.getServletContext().getContextPath() + "/VisualizzaProfilo");
            return;
        }

//        try {
            UtenteDAO utenteDAO = (UtenteDAO) request.getServletContext().getAttribute(UTENTE_DAO_PARAM);
            if (count == 3) {
                u.setEmail(user.getEmail());
                u.setTipoUtente(user.getTipoUtente());
                utenteDAO.update(u);
            }


//        } catch (ViolazioneEntityException e) {
//            SessionManager.setError(ssn, e.getMessage());
//            addres = "error.jsp";
//        }

        response.sendRedirect(request.getServletContext().getContextPath() + addres);
    }


    public static final String UTENTE_DAO_PARAM = "LogoutServlet.UtenteDAO";
}