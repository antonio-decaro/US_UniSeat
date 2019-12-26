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
        SessionManager sessionManager = new SessionManager();
        Utente user = sessionManager.getUtente(ssn);
        String addres = "VisulizzaProfilo.jsp";

        try {
            if (user == null) {
                SessionManager.setError(ssn, "Utente non registrato");
                response.sendRedirect(request.getServletContext().getContextPath() + "/login");
                return;
            }

            Utente u = new Utente();
            int count = 0;
            String Rgx1 = "^[a-z A-Z]+$";
            String Rgx2 = "^((?=.*[\\d])(?=.*[a-z])(?=.*[A-Z])).+$";

            String nome = request.getParameter("nome").trim();
            String cognome = request.getParameter("cognome").trim();
            String password = request.getParameter("password");
            String cPassword = request.getParameter("confPassword");

            if (nome == null || nome.length() < 1 || nome.length() > 20) {
                SessionManager.setError(ssn, "Il campo Nome non rispetta la lunghezza");
                response.sendRedirect(request.getServletContext().getContextPath() + "/VisualizzaProfilo");
//                return;
            }
            if (nome.matches(Rgx1)) {
                u.setNome(nome);
                count++;
            } else {
                SessionManager.setError(ssn, "Il campo Nome non rispetta il formato");
                response.sendRedirect(request.getServletContext().getContextPath() + "/VisualizzaProfilo");
                return;
            }

            if (cognome == null || nome.length() < 1 || nome.length() > 20) {
                SessionManager.setError(ssn, "Il campo Cognome non rispetta il lunghezza");
                response.sendRedirect(request.getServletContext().getContextPath() + "/VisualizzaProfilo");
                return;
            }
            if (nome.matches(Rgx1)) {
                u.setCognome(cognome);
                count++;
            } else {
                SessionManager.setError(ssn, "Il campo Cognome non rispetta il formato");
                response.sendRedirect(request.getServletContext().getContextPath() + "/VisualizzaProfilo");
                return;
            }

            if (password == null || password.length() > 32 || password.length() < 8) {
                SessionManager.setError(ssn, "Il campo Password non rispetta il lunghezza");
                response.sendRedirect(request.getServletContext().getContextPath() + "/VisualizzaProfilo");
                return;
            }
            if (password.matches(Rgx2)) {
                u.setPassword(password);
                count++;
            } else {
                SessionManager.setError(ssn, "Il campo Cognome non rispetta il formato");
                response.sendRedirect(request.getServletContext().getContextPath() + "/VisualizzaProfilo");
                return;
            }
            if (!password.equals(cPassword)) {
                SessionManager.setError(ssn, "Il campo Password non corrisponde con la Password di conferma ");
                response.sendRedirect(request.getServletContext().getContextPath() + "/VisualizzaProfilo");
                return;
            } else
                count++;

            UtenteDAO utenteDAO = (UtenteDAO) request.getServletContext().getAttribute(UTENTE_DAO_PARAM);
            if (count == 4) {
                u.setEmail(user.getEmail());
                utenteDAO.update(u);
            }
            else {
                SessionManager.setError(ssn, "Impossibilie effettuare la modifica");
                response.sendRedirect(request.getServletContext().getContextPath() + "/VisualizzaProfilo");
                return;
            }
        } catch (ViolazioneEntityException e) {
            addres = "error.jsp";
            e.printStackTrace();
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(addres);
        requestDispatcher.forward(request, response);
    }


    private static final long serialVersionUID = 1L;
    public static final String UTENTE_DAO_PARAM = "LogoutServlet.UtenteDAO";
}